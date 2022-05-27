package com.zhengqing.common.core.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.google.common.collect.Lists;
import com.zhengqing.common.core.constant.AppConstant;
import com.zhengqing.common.core.enums.ExcelExportFileTypeEnum;
import com.zhengqing.common.core.enums.ExcelImportFileTypeEnum;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.util.MyFileUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jxls.common.Context;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 导入导出Excel报表工具类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/7 14:00
 */
@Slf4j
@Component
public class ExcelReportUtil {

    @Autowired
    private QiniuFileUtil qiniuFileUtil;

    /**
     * 读取上传文件数据
     *
     * @param dataList:                数据
     * @param excelImportFileTypeEnum: 导入报表模板类型
     * @param file:                    上传文件数据
     * @param isThrowException:        遇到错误是否抛出异常信息 true:抛出 false：不抛，继续处理数据
     * @return 装满数据的dataList
     * @author zhengqingya
     * @date 2020/9/7 13:59
     */
    public <E, T> List<T> read(List<T> dataList, ExcelImportFileTypeEnum excelImportFileTypeEnum, MultipartFile file,
                               boolean isThrowException) {
        String fileName = file.getName();
        InputStream inputXLS = null;
        InputStream inputXML = null;
        try {
            Resource resource = new ClassPathResource(
                    AppConstant.DEFAULT_REPORT_IMPORT_FOLDER + excelImportFileTypeEnum.getMappingXml());
            // 上传文件流
            inputXLS = file.getInputStream();
            // xml配置文件流
            inputXML = resource.getInputStream();
            // 执行解析
            XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML);
            Map<String, Object> beans = new HashMap<>(1);
            beans.put("dataList", dataList);
            XLSReadStatus readStatus = mainReader.read(inputXLS, beans);
            if (readStatus.isStatusOK()) {
                log.debug("读取excel文件成功: 【{}】", fileName);
            }
        } catch (Exception e) {
            // ① 记录错误位置
            String errorCell = e.getMessage().split(" ")[3];
            // ② 记录错误原因
            String errorMsg = e.getCause().toString();
            String[] causeMsgArray = errorMsg.split(":");
            errorMsg = errorMsg.substring(causeMsgArray[0].length() + 2).split(":")[0];
            switch (errorMsg) {
                case "For input string":
                    errorMsg = "时间格式不正确";
                    break;
                case "Error converting from 'String' to 'Integer' For input string":
                    errorMsg = "请填写数字类型";
                    break;
                default:
                    break;
            }
            errorMsg = "读取" + fileName + "文件异常: " + errorCell + errorMsg;
            if (isThrowException) {
                throw new MyException(errorMsg);
            } else {
                log.error(errorMsg);
            }
        } finally {
            try {
                if (inputXLS != null) {
                    inputXLS.close();
                }
                if (inputXML != null) {
                    inputXML.close();
                }
            } catch (IOException e) {
                log.error("parse excel error : 【{}】", e.getMessage());
            }
        }
        return dataList;
    }

    /**
     * 导出EXCEL到指定路径
     *
     * @param dataList:                数据
     * @param excelExportFileTypeEnum: 导出报表模板类型
     * @param exportPath:              导出路径
     * @return 文件下载地址信息
     * @author zhengqingya
     * @date 2020/9/7 13:59
     */
    @SneakyThrows(Exception.class)
    public String export(List<Map<String, Object>> dataList, ExcelExportFileTypeEnum excelExportFileTypeEnum,
                         String exportPath) {
        // 处理导出
        File exportFile = this.handleExport(dataList, excelExportFileTypeEnum, exportPath);
        // 返回文件下载地址
        // String filePathCsdnBlogExportExcel = Constants.FILE_PATH_CSDN_BLOG_EXPORT_EXCEL;
        // String[] filePathCsdnBlogExportExcelArray = filePathCsdnBlogExportExcel.split(Constants.SYSTEM_SEPARATOR);
        // String fileName = filePathCsdnBlogExportExcelArray[filePathCsdnBlogExportExcelArray.length - 1];
        String fileName = excelExportFileTypeEnum.getSheetName() + ".xls";
        return this.qiniuFileUtil.uploadFile(exportFile, fileName);
    }

    /**
     * 导出EXCEL给前端直接下载
     *
     * @param dataList:                数据
     * @param excelExportFileTypeEnum: 导出报表模板类型
     * @param exportPath:              导出路径
     * @param response:
     * @return void
     * @author zhengqingya
     * @date 2020/9/8 14:59
     */
    @SneakyThrows(Exception.class)
    public void export(List<Map<String, Object>> dataList, ExcelExportFileTypeEnum excelExportFileTypeEnum,
                       String exportPath, HttpServletResponse response) {
        // 处理导出
        this.handleExport(dataList, excelExportFileTypeEnum, exportPath);

        // ======================= ↓↓↓↓↓↓ 响应给前端 ↓↓↓↓↓↓ =======================
        // 文件名 - 解决中文乱码问题
        String filename = URLEncoder.encode(excelExportFileTypeEnum.getTemplateFile().substring(1), "UTF-8");
        // 设置响应编码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        OutputStream outputStream = response.getOutputStream();
        InputStream inputStream = new FileInputStream(exportPath);
        byte[] buffer = new byte[1024];
        int i = -1;
        while ((i = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, i);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    /**
     * Hutool Java工具类库导出Excel测试
     *
     * @param response:
     * @return void
     * @author zhengqingya
     * @date 2020/9/9 10:24
     */
    @SneakyThrows(Exception.class)
    public void exportTest(HttpServletResponse response) {
        List<User> list = Lists.newArrayList();
        list.add(new User("zhangsan", "1231", new Date()));
        list.add(new User("zhangsan1", "1232", new Date()));
        list.add(new User("zhangsan2", "1233", new Date()));
        list.add(new User("zhangsan3", "1234", new Date()));
        list.add(new User("zhangsan4", "1235", new Date()));
        list.add(new User("zhangsan5", "1236", DateUtil.date(new Date())));

        /* 通过工具类创建writer，默认创建xls格式 */
        ExcelWriter writer = ExcelUtil.getWriter();
        /* 自定义标题别名 */
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("age", "年龄");
        writer.addHeaderAlias("birthDay", "生日");
        /* 合并单元格后的标题行，使用默认标题样式 */
        writer.merge(2, "申请人员信息");
        /* 一次性写出内容，使用默认样式，强制输出标题 */
        writer.write(list, true);
        /* out为OutputStream，需要写出到的目标流 */
        /* response为HttpServletResponse对象 */
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        /* test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码 */
        String name = URLEncoder.encode("申请学院", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + name + ".xls");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            /* 关闭writer，释放内存 */
            writer.close();
        }
        /* 此处记得关闭输出Servlet流 */
        IoUtil.close(out);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String name;
        private String age;
        private Date birthDay;
    }

    /**
     * 处理导出数据逻辑
     *
     * @param dataList:                数据
     * @param excelExportFileTypeEnum: 导出报表模板类型
     * @param exportPath:              导出路径
     * @return 导出数据文件
     * @author zhengqingya
     * @date 2020/9/8 15:49
     */
    @SneakyThrows(Exception.class)
    public File handleExport(List<Map<String, Object>> dataList, ExcelExportFileTypeEnum excelExportFileTypeEnum,
                             String exportPath) {
        Resource resource =
                new ClassPathResource(AppConstant.DEFAULT_REPORT_EXPORT_FOLDER + excelExportFileTypeEnum.getTemplateFile());
        InputStream templateInputStream = resource.getInputStream();

        log.debug("导出文件地址为:{}", exportPath);

        // 删除本地上次保存的数据 -> 避免数据污染
        // MyFileUtil.deleteFileOrFolder(exportPath);
        // 创建文件
        File exportFile = MyFileUtil.touch(exportPath);

        // 列表数据将存储到指定的excel文件路径
        OutputStream out = new FileOutputStream(exportPath);
        // 这里的context是jxls框架上的context内容
        Context context = PoiTransformer.createInitialContext();
        // 将列表参数放入context中
        context.putVar("dataList", dataList);
        Workbook workbook = WorkbookFactory.create(templateInputStream);
        // Changing name of the first sheet
        workbook.setSheetName(0, excelExportFileTypeEnum.getSheetName());
        PoiTransformer transformer = PoiTransformer.createTransformer(workbook);
        transformer.setOutputStream(out);
        // 将列表数据按照模板文件中的格式生成
        JxlsHelper.getInstance().processTemplate(context, transformer);
        templateInputStream.close();
        out.close();
        return exportFile;
    }

}
