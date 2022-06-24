package com.zhengqing.tool.crawler.service.impl;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.file.FileWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.youbenzi.md2.export.FileFactory;
import com.youbenzi.md2.util.MDUtil;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.base.util.MyFileUtil;
import com.zhengqing.common.excel.enums.ExcelExportFileTypeEnum;
import com.zhengqing.common.excel.enums.ExcelImportFileTypeEnum;
import com.zhengqing.common.excel.util.ExcelReportUtil;
import com.zhengqing.common.file.util.QiniuFileUtil;
import com.zhengqing.tool.crawler.entity.StCrawlerArticleInfo;
import com.zhengqing.tool.crawler.enums.StCrawlerExportDataTypeEnum;
import com.zhengqing.tool.crawler.mapper.StCrawlerArticleInfoMapper;
import com.zhengqing.tool.crawler.model.bo.StCrawlerArticleInfoBO;
import com.zhengqing.tool.crawler.model.dto.StCrawlerArticleInfoExportDataDTO;
import com.zhengqing.tool.crawler.model.dto.StCrawlerArticleInfoListDTO;
import com.zhengqing.tool.crawler.model.dto.StCrawlerArticleInfoQueryDTO;
import com.zhengqing.tool.crawler.model.vo.StCrawlerArticleInfoListVO;
import com.zhengqing.tool.crawler.service.IStCrawlerArticleInfoService;
import com.zhengqing.tool.util.htmlToMd.HtmlToMd;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 小工具 - 爬虫 - 文章信息 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @date 2020-08-21 22:35:34
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class StCrawlerArticleInfoServiceImpl extends ServiceImpl<StCrawlerArticleInfoMapper, StCrawlerArticleInfo>
        implements IStCrawlerArticleInfoService {

    @Resource
    private StCrawlerArticleInfoMapper stCrawlerArticleInfoMapper;

    @Resource
    private ExcelReportUtil excelReportUtil;

    @Resource
    private QiniuFileUtil qiniuFileUtil;

    @Override
    public IPage<StCrawlerArticleInfoListVO> listPage(StCrawlerArticleInfoListDTO params) {
        IPage<StCrawlerArticleInfoListVO> result =
                this.stCrawlerArticleInfoMapper.selectStCrawlerArticleInfos(new Page<>(), params);
        return result;
    }

    @Override
    public Integer getArticleInfoId(Integer articleId) {
        return this.stCrawlerArticleInfoMapper.selectArticleInfoId(articleId);
    }

    @Override
    public StCrawlerArticleInfoListVO getArticleDetail(StCrawlerArticleInfoQueryDTO params) {
        return this.stCrawlerArticleInfoMapper.selectArticleDetail(params);
    }

    @Override
    public Integer getArticleSumByWebsiteId(Integer websiteId) {
        return this.stCrawlerArticleInfoMapper.selectArticleSumByWebsiteId(websiteId);
    }

    @Override
    public List<StCrawlerArticleInfoListVO> getArticlesByWebsiteId(Integer websiteId) {
        return this.stCrawlerArticleInfoMapper.selectArticlesByWebsiteId(websiteId);
    }

    @Override
    @SneakyThrows(Exception.class)
    public String exportData(StCrawlerArticleInfoExportDataDTO params) {
        StCrawlerArticleInfo articleInfo = this.stCrawlerArticleInfoMapper.selectById(params.getArticleInfoId());
        if (articleInfo == null) {
            throw new MyException("未找到此文章信息！");
        }

        String fileExtension = StCrawlerExportDataTypeEnum.getEnum(params.getType()).getFileExtension();
        String filePath = AppConstant.FILE_PATH_CSDN_BLOG_EXPORT_SRC + "/" + articleInfo.getTitle() + fileExtension;

        File file = MyFileUtil.touch(filePath);
        // 文件写入
        FileWriter fileWriter = new FileWriter(filePath);

        String htmlContent = articleInfo.getContent();
        switch (StCrawlerExportDataTypeEnum.getEnum(params.getType())) {
            case HTML:
                fileWriter.write(htmlContent);
                break;
            case MARKDOWN:
                String markdownContent = HtmlToMd.convert(htmlContent, filePath);
                String markdown2Html = MDUtil.markdown2Html(markdownContent);
                fileWriter.write(markdownContent);
                break;
            case WORD:
            case PDF:
                String markdownContentNew = HtmlToMd.converthtml(htmlContent, "utf-8");
                FileFactory.produce(markdownContentNew, filePath);
                break;
            default:
                break;
        }

        // 采用七牛云上传并返回地址下载文件
        // if (StCrawlerExportDataDTOEnum.HTML == StCrawlerExportDataDTOEnum.getEnum(params.getType())) {
        // // .html类型，前端会直接打开，而不是下载，所以这里手动处理一下，转换成zip格式
        // File zipFile =
        // MyFileUtil.zip(Constants.FILE_PATH_CSDN_BLOG_EXPORT_SRC, Constants.FILE_PATH_CSDN_BLOG_EXPORT_ZIP, true,
        // true);
        // String fileUrl = qiniuFileUtil.uploadFile(zipFile, "blog.zip");
        // // 删除本地数据
        // MyFileUtil.deleteFileOrFolder(Constants.FILE_PATH_CSDN_BLOG_EXPORT_ZIP);
        // return fileUrl;
        // }
        String fileUrl = this.qiniuFileUtil.uploadFile(file, articleInfo.getTitle() + fileExtension);
        // 删除本地数据
        MyFileUtil.deleteFileOrFolder(AppConstant.FILE_PATH_CSDN_BLOG_EXPORT_SRC);
        return fileUrl;
    }

    @Override
    public String exportAllDataByWebsiteId(Integer websiteId) {
        if (websiteId == null) {
            throw new MyException("请先选择要导出的网站数据源！");
        }
        List<StCrawlerArticleInfoListVO> articleList = this.getArticlesByWebsiteId(websiteId);
        List<String> filePathList = Lists.newArrayList();
        articleList.forEach(e -> {
            e.setPublishTimeStr(MyDateUtil.dateToStr(e.getPublishTime(), MyDateUtil.DATE_TIME_FORMAT));
            String content = e.getContent();
            String filePath =
                    AppConstant.FILE_PATH_CSDN_BLOG_EXPORT_SRC + "/" + e.getCategory() + "/" + e.getTitle() + ".html";
            try {
                // 文件写入
                FileWriter writer = new FileWriter(filePath);
                writer.write(content);
                filePathList.add(filePath);
            } catch (IORuntimeException error) {
                log.error(String.format("《根据网站id导出数据》 导出文件名：【%s】    失败原因：【%s】", e.getTitle(), error.getMessage()));
            }
        });
        if (!CollectionUtils.isEmpty(filePathList)) {
            // 这里先导出excel报表数据
            List<Map<String, Object>> dataList =
                    JSON.parseObject(JSON.toJSONString(articleList), new TypeReference<List<Map<String, Object>>>() {
                    });
            String exportUploadUrl = this.excelReportUtil.export(dataList, ExcelExportFileTypeEnum.CSDN文章信息数据,
                    AppConstant.FILE_PATH_CSDN_BLOG_EXPORT_EXCEL);

            File zipFile = MyFileUtil.zip(AppConstant.FILE_PATH_CSDN_BLOG_EXPORT_SRC,
                    AppConstant.FILE_PATH_CSDN_BLOG_EXPORT_ZIP, true, true);
            String bloggerName = articleList.get(0).getWebsiteName();
            // 采用七牛云上传并返回地址下载文件
            String downloadUrl = this.qiniuFileUtil.uploadFile(zipFile,
                    "BLOG_(" + bloggerName + ")_" + MyDateUtil.dateToStr(new Date(), "yyyy-MM-dd HH-mm-ss") + ".zip");
            // 删除本地数据
            MyFileUtil.deleteFileOrFolder(AppConstant.FILE_PATH_CSDN_BLOG_EXPORT_ZIP);
            return downloadUrl;
        }
        throw new MyException("暂无数据！");
    }

    @Override
    public String exportExcelByWebsiteId(Integer websiteId) {
        if (websiteId == null) {
            throw new MyException("请先选择要导出的网站数据源！");
        }
        List<StCrawlerArticleInfoListVO> articleList = this.getArticlesByWebsiteId(websiteId);
        articleList
                .forEach(e -> e.setPublishTimeStr(MyDateUtil.dateToStr(e.getPublishTime(), MyDateUtil.DATE_TIME_FORMAT)));
        if (!CollectionUtils.isEmpty(articleList)) {
            // 这里先导出excel报表数据
            List<Map<String, Object>> dataList =
                    JSON.parseObject(JSON.toJSONString(articleList), new TypeReference<List<Map<String, Object>>>() {
                    });
            String downloadUrl = this.excelReportUtil.export(dataList, ExcelExportFileTypeEnum.CSDN文章信息数据,
                    AppConstant.FILE_PATH_CSDN_BLOG_EXPORT_EXCEL);
            return downloadUrl;
        }
        return null;
    }

    @Override
    @SneakyThrows(Exception.class)
    public String importData(MultipartFile file, Integer websiteId) {
        if (websiteId == null) {
            throw new MyException("请先选择数据要导入的网站！");
        }
        if (file == null || file.isEmpty()) {
            throw new MyException("上传文件不能为空！");
        } else {
            // 文件名
            final String fileName = file.getOriginalFilename();
            // 文件类型
            final String contentType = file.getContentType();
            // TODO【暂时只支持解压zip文件】
            // rar有版权，有些东西没有公开，对解压有一些限制，即使其他解压包也可能有问题，但是建议尝试!
            // 针对rar文件解压时报如此异常的可能原因是因为rar压缩算法是不开源的，导致这块资料相对较少，对高版本没有兼容性；为避免不必要的如此文件解压异常，实际项目中若需要上传文件执行导入等相关功能时，可以强制限定文件格式为非rar格式，如zip开源算法，使用比较成熟就不会有这种尴尬的问题；
            if (!contentType.equals("application/x-zip-compressed")) {
                throw new MyException("暂时只直接导入.zip文件格式!");
            }

            // 得到文件流
            final InputStream is = file.getInputStream();

            // 这里先删除之前解压的久数据，避免数据重复
            MyFileUtil.deleteFileOrFolder(AppConstant.FILE_PATH_CSDN_BLOG_IMPORT_SRC);

            // 解压文件
            File outFile = MyFileUtil.unzip(is, AppConstant.FILE_PATH_CSDN_BLOG_IMPORT_ZIP,
                    AppConstant.FILE_PATH_CSDN_BLOG_IMPORT_SRC, true);
            String fileFullPath = outFile.getAbsolutePath();

            // 存储读取的文件信息
            List<StCrawlerArticleInfo> articleInfoList = Lists.newArrayList();
            // 解析系统导出时定义的excel文件名`excel.xls`
            String[] ExcelPathArray = AppConstant.FILE_PATH_CSDN_BLOG_EXPORT_EXCEL.split(AppConstant.SEPARATOR_SPRIT);
            String csdnBlogExportExcelFilePath = ExcelPathArray[ExcelPathArray.length - 1];
            this.recursionReadFile(articleInfoList, fileFullPath, csdnBlogExportExcelFilePath);
            articleInfoList.forEach(e -> {
                e.setWebsiteId(websiteId);
                // 保存数据
                StCrawlerArticleInfoListVO articleInfo =
                        this.stCrawlerArticleInfoMapper.selectArticleDetail(StCrawlerArticleInfoQueryDTO.builder()
                                .websiteId(e.getWebsiteId()).title(e.getTitle()).category(e.getCategory()).build());
                if (articleInfo == null) {
                    // 情况① : 新增数据
                    this.stCrawlerArticleInfoMapper.insert(e);
                } else {
                    // 情况② : 更新数据
                    e.setArticleInfoId(articleInfo.getArticleInfoId());
                    this.stCrawlerArticleInfoMapper.updateById(e);
                }
            });

            // 关闭输入流
            is.close();

            // 删除解压的文件数据
            MyFileUtil.deleteFileOrFolder(AppConstant.FILE_PATH_CSDN_BLOG_IMPORT_SRC);
            return "上传成功";
        }
    }

    /**
     * 递归读取文件
     *
     * @param articleInfoList:
     * @param fileFullPath:
     * @param csdnBlogExportExcelFilePath:
     * @return void
     * @author zhengqingya
     * @date 2020/9/5 21:30
     */
    @SneakyThrows(Exception.class)
    public void recursionReadFile(List<StCrawlerArticleInfo> articleInfoList, String fileFullPath,
                                  String csdnBlogExportExcelFilePath) {
        File file = new File(fileFullPath);
        // 如果该目录存在
        if (file.exists()) {
            String filePath = file.getPath();
            filePath = filePath.replaceAll("\\\\", AppConstant.SEPARATOR_SPRIT);
            if (file.isDirectory()) {
                // 文件夹操作 -> 循环
                log.debug("文件夹：【{}】", filePath);
                // 返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中的文件和目录。
                String[] fileList = file.list();
                if (fileList != null && fileList.length > 0) {
                    for (int i = 0; i < fileList.length; i++) {
                        // 重新设置路径
                        String readFileNewPath = fileFullPath + "/" + fileList[i];
                        // 继续递归读取
                        this.recursionReadFile(articleInfoList, readFileNewPath, csdnBlogExportExcelFilePath);
                    }
                } else {
                    log.debug("该目录下面为空！");
                }
            } else {
                // 文件操作
                log.debug("文件：【{}】" + filePath);
                if (file.getName().equals(csdnBlogExportExcelFilePath)) {
                    // 情况①：这里拿到zip压缩包中的`excel.xls`文件做处理...
                    // File转MultipartFile
                    InputStream inputStream = new FileInputStream(file);
                    MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
                    List<StCrawlerArticleInfoBO> excelArticleInfoList = Lists.newArrayList();
                    this.excelReportUtil.read(excelArticleInfoList, ExcelImportFileTypeEnum.CSDN文章信息数据, multipartFile, true);
                    System.out.println(1);
                    // TODO 这里读取到数据后待处理...
                } else {
                    // 情况②：csdn文章数据
                    // 数据整理处理 【文件所在的最后一级为分类】
                    String[] fileInfoArray = filePath.split(AppConstant.SEPARATOR_SPRIT);
                    // 文章所属分类
                    String category = fileInfoArray[fileInfoArray.length - 2];
                    // 文章名
                    String fileName = file.getName().split("\\.")[0];
                    // 文件相对路径作为文章地址
                    String fileRelativePathUrl =
                            filePath.substring(AppConstant.FILE_PATH_CSDN_BLOG_IMPORT_SRC.length());
                    // 文件内容
                    String fileContent = MyFileUtil.readFileContent(file);

                    // 封装文章信息
                    StCrawlerArticleInfo articleInfo = new StCrawlerArticleInfo();
                    articleInfo.setTitle(fileName);
                    articleInfo.setCategory(category);
                    articleInfo.setContent(fileContent);
                    articleInfo.setUrl(fileRelativePathUrl);
                    articleInfo.setPublishTime(new Date());
                    articleInfoList.add(articleInfo);
                }
            }
        } else {
            throw new MyException("目录或文件不存在:" + file.getName());
        }
    }

}
