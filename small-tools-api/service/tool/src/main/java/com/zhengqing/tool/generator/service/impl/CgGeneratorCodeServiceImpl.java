package com.zhengqing.tool.generator.service.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.exception.MyException;
import com.zhengqing.common.util.FreeMarkerUtil;
import com.zhengqing.common.util.MyDateUtil;
import com.zhengqing.common.util.MyFileUtil;
import com.zhengqing.common.util.MyStringUtil;
import com.zhengqing.common.util.QiniuFileUtil;
import com.zhengqing.tool.db.model.vo.StDbTableColumnListVO;
import com.zhengqing.tool.db.model.vo.StDbTableColumnListVO.ColumnInfo;
import com.zhengqing.tool.db.service.IStDbJdbcService;
import com.zhengqing.tool.generator.entity.CgProjectReDb;
import com.zhengqing.tool.generator.entity.CgProjectVelocityContext;
import com.zhengqing.tool.generator.enums.CgColumnJavaTypeEnum;
import com.zhengqing.tool.generator.enums.CgProjectTemplateDataTypeEnum;
import com.zhengqing.tool.generator.model.bo.CgGeneratorCodeColumnInfoBO;
import com.zhengqing.tool.generator.model.bo.CgGeneratorCodeTemplateFileBO;
import com.zhengqing.tool.generator.model.dto.CgFreeMarkerTemplateListDTO;
import com.zhengqing.tool.generator.model.dto.CgGenerateCodeDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectTemplateListDTO;
import com.zhengqing.tool.generator.model.dto.CgTableConfigSaveDTO;
import com.zhengqing.tool.generator.model.vo.CgFreeMarkerTemplateListVO;
import com.zhengqing.tool.generator.model.vo.CgProjectTemplateListVO;
import com.zhengqing.tool.generator.service.ICgFreeMarkerTemplateService;
import com.zhengqing.tool.generator.service.ICgGeneratorCodeService;
import com.zhengqing.tool.generator.service.ICgProjectPackageService;
import com.zhengqing.tool.generator.service.ICgProjectReDbService;
import com.zhengqing.tool.generator.service.ICgProjectTemplateService;
import com.zhengqing.tool.generator.service.ICgTableConfigService;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 代码生成器 - 生成代码 服务实现类
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/11/14 23:59
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CgGeneratorCodeServiceImpl implements ICgGeneratorCodeService {

    @Autowired
    private ICgProjectReDbService cgProjectReDbService;

    @Autowired
    private ICgProjectTemplateService cgProjectTemplateService;

    @Autowired
    private IStDbJdbcService stDbJdbcService;

    @Autowired
    private ICgProjectPackageService cgProjectPackageService;

    @Autowired
    private ICgFreeMarkerTemplateService cgFreeMarkerTemplateService;

    @Autowired
    private QiniuFileUtil qiniuFileUtil;

    @Autowired
    private ICgTableConfigService cgTableConfigService;

    @Override
    public String generateTemplateData(Map<String, Object> templateDataMap, String templateContent) {
        return FreeMarkerUtil.generateTemplateData(templateDataMap, templateContent);
    }

    @Override
    public String generateCode(CgGenerateCodeDTO params) {
        Integer projectReDbDataSourceId = params.getProjectReDbDataSourceId();
        String tableName = params.getTableName();
        String packageName = params.getPackageName();
        packageName = packageName.trim();
        String moduleName = params.getModuleName();
        moduleName = moduleName.trim();
        List<String> queryColumnList = params.getQueryColumnList();
        Integer dataType = params.getDataType();
        Boolean ifTestTemplateData = params.getIfTestTemplateData();
        Integer projectTemplateId = params.getProjectTemplateId();
        if (ifTestTemplateData == null) {
            ifTestTemplateData = false;
        }

        // 数据模型(这里使用map类型) -- [数据模型可以是List、Map对象 注意:Map类型的key必须是String类型]
        Map<String, Object> templateDataMap = Maps.newHashMap();
        // 模板文件名列表
        List<CgGeneratorCodeTemplateFileBO> templateFileInfoList = Lists.newArrayList();
        // 表名驼峰式处理
        String entityName = tableName.substring("t_".length());
        String entityNameLower = MyStringUtil.dbStrToHumpLower(entityName);
        String entityNameUpper = MyStringUtil.dbStrToHumpUpper(entityName);
        templateDataMap.put("tableName", tableName);
        templateDataMap.put("tableNameAbbr", MyStringUtil.tableNameToAbbr(tableName));
        templateDataMap.put("vueApiName", tableName.substring("t_".length()));
        templateDataMap.put("entityNameLower", entityNameLower);
        templateDataMap.put("entityNameUpper", entityNameUpper);
        templateDataMap.put("entity", entityNameUpper);
        templateDataMap.put("moduleName", moduleName);

        CgProjectReDb cgProjectReDb = cgProjectReDbService.getById(projectReDbDataSourceId);
        Integer projectId = cgProjectReDb.getProjectId();
        Integer dbDataSourceId = cgProjectReDb.getDbDataSourceId();
        String dbName = cgProjectReDb.getDbName();

        // 保存表配置信息
        CgTableConfigSaveDTO cgTableConfigSaveDTO = new CgTableConfigSaveDTO();
        cgTableConfigSaveDTO.setProjectId(projectId);
        cgTableConfigSaveDTO.setTableName(tableName);
        cgTableConfigSaveDTO.setQueryColumnList(queryColumnList);
        cgTableConfigSaveDTO.setProjectReDbDataSourceId(projectReDbDataSourceId);
        cgTableConfigSaveDTO.setPackageName(packageName);
        cgTableConfigSaveDTO.setModuleName(moduleName);
        cgTableConfigSaveDTO.setDataType(dataType);
        cgTableConfigService.addOrUpdateData(cgTableConfigSaveDTO);

        // 1、获取包路径信息
        Map<Integer, String> packageNameInfoMap = cgProjectPackageService.packageNameInfoMap(projectId);
        // 查询项目父包名，然后取前端设置的父包名+模块名进行替换处理
        String parentPackageName = cgProjectPackageService.getParentPackageName(projectId);
        String parentPackageNameFinal = packageName + (StringUtils.isBlank(moduleName) ? "" : "." + moduleName);
        packageNameInfoMap.forEach((key, value) -> {
            value = value.replace(parentPackageName, parentPackageNameFinal);
            packageNameInfoMap.put(key, value);
        });

        // 2、获取该项目关联的模板数据，并在本地创建模板文件 -> 封装 （模板文件名 -> 模板文件包路径）
        CgProjectTemplateListDTO cgProjectTemplateListDTO = new CgProjectTemplateListDTO();
        cgProjectTemplateListDTO.setProjectId(projectId);
        cgProjectTemplateListDTO.setIsBasic(CgProjectTemplateDataTypeEnum.项目模板.getDataType());
        List<CgProjectTemplateListVO> projectTemplateList = cgProjectTemplateService.list(cgProjectTemplateListDTO);
        for (CgProjectTemplateListVO projectTemplate : projectTemplateList) {
            if (ifTestTemplateData && !projectTemplate.getProjectTemplateId().equals(projectTemplateId)) {
                continue;
            }
            String fileName = projectTemplate.getFileName();
            String fileSuffix = projectTemplate.getFileSuffix();
            String content = projectTemplate.getContent();
            String projectTemplateRePackageName = packageNameInfoMap.get(projectTemplate.getProjectPackageId());

            CgGeneratorCodeTemplateFileBO templateFileBO = new CgGeneratorCodeTemplateFileBO();
            templateFileBO.setFileName(fileName);
            templateFileBO.setGenerateFileSuffix(fileSuffix);
            templateFileBO.setTemplateContent(content);
            templateFileBO.setTemplateRePackage(projectTemplateRePackageName);
            templateFileInfoList.add(templateFileBO);
        }

        if (templateFileInfoList.size() == 0) {
            throw new MyException("无数据！");
        }

        // 3、获取表字段信息
        StDbTableColumnListVO columnInfo =
            stDbJdbcService.getAllColumnsByDataSourceIdAndDbNameAndTableName(dbDataSourceId, dbName, tableName);

        // 4、模板数据处理
        handleTemplateData(templateDataMap, columnInfo, packageNameInfoMap, parentPackageNameFinal, queryColumnList);

        // 5、准备生成（模板+数据模型）
        String templateData =
            this.generateTemplateData(templateFileInfoList, templateDataMap, projectId, ifTestTemplateData);

        // 如果为测试模板数据生成，则返回模板内容
        if (ifTestTemplateData) {
            return templateData;
        }

        // 6、打包生成的代码
        String codeSrcPath = AppConstant.FILE_PATH_CODE_GENERATOR_SRC_CODE + AppConstant.SEPARATOR_SPRIT
            + parentPackageNameFinal.replace(".", AppConstant.SEPARATOR_SPRIT);
        File zipFile = MyFileUtil.zip(codeSrcPath, AppConstant.FILE_PATH_CODE_GENERATOR_ZIP, true, false);
        // 采用七牛云上传并返回地址下载文件
        return qiniuFileUtil.uploadFile(zipFile, "CODE_" + DateUtil.format(new Date(), "yyyy-MM-dd HH-mm-ss") + ".zip");
    }

    /**
     * 处理模板数据
     *
     * @param templateDataMap:
     *            模板数据
     * @param columnInfo:
     *            表字段信息
     * @param packageNameInfoMap:
     *            包信息
     * @param parentPackageName:
     *            父包名+模块名
     * @param queryColumnList:
     *            可检索字段信息
     * @return: 模板数据
     * @author : zhengqing
     * @date : 2020/11/15 21:42
     */
    private Map<String, Object> handleTemplateData(Map<String, Object> templateDataMap,
        StDbTableColumnListVO columnInfo, Map<Integer, String> packageNameInfoMap, String parentPackageName,
        List<String> queryColumnList) {
        // 获取用户自己配置的模板数据
        List<CgFreeMarkerTemplateListVO> freeMarkerTemplateDataList =
            cgFreeMarkerTemplateService.list(CgFreeMarkerTemplateListDTO.builder().build());
        if (!CollectionUtils.isEmpty(freeMarkerTemplateDataList)) {
            freeMarkerTemplateDataList.forEach(e -> templateDataMap.put(e.getTemplateKey(), e.getTemplateValue()));
        }

        // 获取表字段信息
        if (columnInfo != null) {
            // 表注释
            String tableComment = columnInfo.getTableComment();
            templateDataMap.put("tableComment", tableComment);
            List<ColumnInfo> columnInfoList = columnInfo.getColumnInfoList();
            if (!CollectionUtils.isEmpty(columnInfoList)) {
                List<CgGeneratorCodeColumnInfoBO> columnInfoBOList = Lists.newArrayList();
                List<CgGeneratorCodeColumnInfoBO> queryColumnInfoBOList = Lists.newArrayList();
                // 判断是否处理可检索字段
                boolean ifHandleQueryColumn = false;
                if (!CollectionUtils.isEmpty(queryColumnList)) {
                    ifHandleQueryColumn = true;
                }
                for (ColumnInfo e : columnInfoList) {
                    String columnNameDb = e.getColumnName();
                    String columnComment = e.getColumnComment();
                    String columnType = e.getColumnType();
                    boolean ifAutoIncrement = e.isIfAutoIncrement();
                    boolean ifPrimaryKey = e.isIfPrimaryKey();
                    boolean ifNullAble = e.isIfNullAble();
                    String columnNameJavaLower = MyStringUtil.dbStrToHumpLower(columnNameDb);
                    String columnNameJavaUpper = MyStringUtil.dbStrToHumpUpper(columnNameDb);

                    // 封装数据库字段信息
                    CgGeneratorCodeColumnInfoBO cgGeneratorCodeColumnInfoBO = new CgGeneratorCodeColumnInfoBO();
                    cgGeneratorCodeColumnInfoBO.setColumnNameDb(columnNameDb);
                    cgGeneratorCodeColumnInfoBO.setColumnNameJavaLower(columnNameJavaLower);
                    cgGeneratorCodeColumnInfoBO.setColumnNameJavaUpper(columnNameJavaUpper);
                    cgGeneratorCodeColumnInfoBO.setColumnComment(columnComment);
                    cgGeneratorCodeColumnInfoBO.setColumnType(columnType);
                    cgGeneratorCodeColumnInfoBO
                        .setColumnTypeJava(CgColumnJavaTypeEnum.getEnum(columnType).getColumnTypeJava());
                    cgGeneratorCodeColumnInfoBO.setIfNullAble(ifNullAble);
                    cgGeneratorCodeColumnInfoBO.setIfPrimaryKey(ifPrimaryKey);
                    cgGeneratorCodeColumnInfoBO.setIfAutoIncrement(ifAutoIncrement);
                    columnInfoBOList.add(cgGeneratorCodeColumnInfoBO);

                    // 封装可检索字段信息
                    if (ifHandleQueryColumn && queryColumnList.contains(columnNameDb)) {
                        queryColumnInfoBOList.add(cgGeneratorCodeColumnInfoBO);
                    }
                }
                templateDataMap.put("columnInfoList", columnInfoBOList);
                templateDataMap.put("queryColumnInfoList", queryColumnInfoBOList);
            }
        }

        // 包信息处理
        HashMap<String, Object> packageMap = Maps.newHashMap();
        packageNameInfoMap.forEach((key, value) -> {
            // String packageName = value.replace(parentPackageName + ".", "");
            String[] packageNameArray = value.split("\\.");
            String packageName = packageNameArray[packageNameArray.length - 1];
            packageMap.put(packageName, value);
        });
        templateDataMap.put("package", packageMap);

        String dateStr = MyDateUtil.dateToStr(new Date(), MyDateUtil.MINUTE_JAVA_AUTHOR_FORMAT);
        templateDataMap.put("date", dateStr);
        return templateDataMap;
    }

    /**
     * 模板数据生成 TODO 注：freemaker模板数据模型必须存在，否则会报错！！！ 之后在前端加个模板测试数据是否正确校验处理
     *
     * @param templateFileInfoList:
     *            模板文件信息
     * @param templateDataMap:
     *            模板数据
     * @param projectId:
     *            项目id
     * @param ifTestTemplateData:
     *            是否为测试模板生成数据
     * @return: 如果为测试模板生成数据则返回模板内容，不再生成文件
     * @author : zhengqing
     * @date : 2020/11/15 21:05
     */
    private String generateTemplateData(List<CgGeneratorCodeTemplateFileBO> templateFileInfoList,
        Map<String, Object> templateDataMap, Integer projectId, boolean ifTestTemplateData) {
        if (!ifTestTemplateData) {
            // 先删除旧数据
            MyFileUtil.deleteFileOrFolder(AppConstant.FILE_PATH_CODE_GENERATOR_DATA_PATH);

            // 将模板配置存入数据库提供给前端页面展示使用
            new CgProjectVelocityContext().delete(new LambdaQueryWrapper<CgProjectVelocityContext>()
                .eq(CgProjectVelocityContext::getProjectId, projectId));
            templateDataMap.forEach((key, value) -> {
                CgProjectVelocityContext velocityContext = new CgProjectVelocityContext();
                velocityContext.setProjectId(projectId);
                velocityContext.setVelocity(key);
                String valueStr = JSONObject.toJSONString(value);
                velocityContext.setContext(valueStr);
                velocityContext.insert();
            });
        }

        // 循环生成数据文件
        for (CgGeneratorCodeTemplateFileBO templateFileInfo : templateFileInfoList) {
            String templateContent = templateFileInfo.getTemplateContent();
            if (ifTestTemplateData) {
                return this.generateTemplateData(templateDataMap, templateContent);
            }
            String fileName = templateFileInfo.getFileName();
            String generateFileSuffix = templateFileInfo.getGenerateFileSuffix();
            String templateRePackage = templateFileInfo.getTemplateRePackage();

            // 待生成文件名 ex: `test.java`
            String generateFileName = String.format("%s%s", fileName, generateFileSuffix);
            String generateFileNameFinal = this.generateTemplateData(templateDataMap, generateFileName);

            // 待生成文件内容
            String fileContentFinal = this.generateTemplateData(templateDataMap, templateContent);

            // 包路径
            String templateRePackagePath =
                templateRePackage.replace(".", AppConstant.SEPARATOR_SPRIT) + AppConstant.SEPARATOR_SPRIT;

            // 最终生成文件路径
            String fileFinalPath = AppConstant.FILE_PATH_CODE_GENERATOR_SRC_CODE + AppConstant.SEPARATOR_SPRIT
                + templateRePackagePath + generateFileNameFinal;
            // 创建文件并写入生成模板数据
            MyFileUtil.writeFileContent(fileContentFinal, fileFinalPath);
        }
        return null;
    }

}
