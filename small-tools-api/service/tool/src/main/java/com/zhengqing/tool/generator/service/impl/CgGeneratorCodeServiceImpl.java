package com.zhengqing.tool.generator.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.util.MyFileUtil;
import com.zhengqing.common.file.util.QiniuFileUtil;
import com.zhengqing.common.web.util.FreeMarkerUtil;
import com.zhengqing.tool.db.model.vo.StDbTableColumnListVO;
import com.zhengqing.tool.db.service.IStDbJdbcService;
import com.zhengqing.tool.generator.entity.CgProjectReDb;
import com.zhengqing.tool.generator.entity.CgProjectVelocityContext;
import com.zhengqing.tool.generator.enums.CgProjectTemplateDataTypeEnum;
import com.zhengqing.tool.generator.model.bo.CgGeneratorCodeTemplateFileBO;
import com.zhengqing.tool.generator.model.dto.CgFreeMarkerTemplateListDTO;
import com.zhengqing.tool.generator.model.dto.CgGenerateCodeDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectTemplateListDTO;
import com.zhengqing.tool.generator.model.dto.CgTableConfigSaveDTO;
import com.zhengqing.tool.generator.model.vo.CgFreeMarkerTemplateListVO;
import com.zhengqing.tool.generator.model.vo.CgProjectTemplateListVO;
import com.zhengqing.tool.generator.service.*;
import com.zhengqing.tool.util.GenerateCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代码生成器 - 生成代码 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/14 23:59
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CgGeneratorCodeServiceImpl implements ICgGeneratorCodeService {

    @Resource
    private ICgProjectReDbService cgProjectReDbService;

    @Lazy
    @Resource
    private ICgProjectTemplateService cgProjectTemplateService;

    @Resource
    private IStDbJdbcService stDbJdbcService;

    @Resource
    private ICgProjectPackageService cgProjectPackageService;

    @Resource
    private ICgFreeMarkerTemplateService cgFreeMarkerTemplateService;

    @Resource
    private QiniuFileUtil qiniuFileUtil;

    @Resource
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

        // 查询项目关联数据库信息
        CgProjectReDb cgProjectReDb = this.cgProjectReDbService.getById(projectReDbDataSourceId);
        Integer projectId = cgProjectReDb.getProjectId();
        Integer dbDataSourceId = cgProjectReDb.getDbDataSourceId();
        String dbName = cgProjectReDb.getDbName();

        if (!ifTestTemplateData) {
            // 保存表配置信息
            CgTableConfigSaveDTO cgTableConfigSaveDTO = new CgTableConfigSaveDTO();
            cgTableConfigSaveDTO.setProjectId(projectId);
            cgTableConfigSaveDTO.setTableName(tableName);
            cgTableConfigSaveDTO.setQueryColumnList(queryColumnList);
            cgTableConfigSaveDTO.setProjectReDbDataSourceId(projectReDbDataSourceId);
            cgTableConfigSaveDTO.setPackageName(packageName);
            cgTableConfigSaveDTO.setModuleName(moduleName);
            cgTableConfigSaveDTO.setDataType(dataType);
            this.cgTableConfigService.addOrUpdateData(cgTableConfigSaveDTO);
        }

        // 1、获取包路径信息
        Map<String, String> packageNameInfoMap = this.cgProjectPackageService.packageNameInfoMap(projectId);
        // 查询项目父包名，然后取前端设置的父包名+模块名进行替换处理
        String parentPackageName = this.cgProjectPackageService.getParentPackageName(projectId);
        String parentPackageNameFinal = packageName + (StringUtils.isBlank(moduleName) ? "" : "." + moduleName);
        packageNameInfoMap.forEach((key, value) -> {
            value = value.replace(parentPackageName, parentPackageNameFinal);
            packageNameInfoMap.put(key, value);
        });


        // 2、获取该项目关联的模板数据，并在本地创建模板文件 -> 封装 （模板文件名 -> 模板文件包路径）
        List<CgProjectTemplateListVO> projectTemplateList = this.cgProjectTemplateService.list(CgProjectTemplateListDTO.builder()
                .projectId(projectId)
                .isBasic(CgProjectTemplateDataTypeEnum.项目模板.getDataType())
                .build());
        // 模板文件名列表
        List<CgGeneratorCodeTemplateFileBO> tplFileInfoList = Lists.newArrayList();
        for (CgProjectTemplateListVO projectTemplate : projectTemplateList) {
            if (ifTestTemplateData && !projectTemplate.getProjectTemplateId().equals(projectTemplateId)) {
                continue;
            }
            tplFileInfoList.add(CgGeneratorCodeTemplateFileBO.builder()
                    .fileName(projectTemplate.getFileName())
                    .generateFileSuffix(projectTemplate.getFileSuffix())
                    .templateContent(projectTemplate.getContent())
                    .templateRePackage(packageNameInfoMap.get(projectTemplate.getProjectPackageId()))
                    .build());
        }
        Assert.notNull(tplFileInfoList, "无数据！");

        // 3、获取表字段信息
        StDbTableColumnListVO columnInfo = this.stDbJdbcService.getAllColumnsByDataSourceIdAndDbNameAndTableName(dbDataSourceId, dbName, tableName);

        // 4、模板数据处理
        Map<String, Object> tplDataMap = GenerateCodeUtil.handleTplData(moduleName, columnInfo, packageNameInfoMap, queryColumnList);
        // 获取用户自己配置的模板数据
        List<CgFreeMarkerTemplateListVO> freeMarkerTemplateDataList = this.cgFreeMarkerTemplateService.list(CgFreeMarkerTemplateListDTO.builder().build());
        if (!CollectionUtils.isEmpty(freeMarkerTemplateDataList)) {
            freeMarkerTemplateDataList.forEach(e -> tplDataMap.put(e.getTemplateKey(), e.getTemplateValue()));
        }


        // 5、如果为测试模板数据生成，则返回模板内容
        if (ifTestTemplateData) {
            // 生成数据（模板+数据模型）
            return this.generateTemplateData(tplDataMap, tplFileInfoList.get(0).getTemplateContent());
        } else {
            // 将模板配置存入数据库提供给前端页面展示使用
            new CgProjectVelocityContext().delete(new LambdaQueryWrapper<CgProjectVelocityContext>().eq(CgProjectVelocityContext::getProjectId, projectId));
            tplDataMap.forEach((key, value) -> CgProjectVelocityContext.builder()
                    .projectId(projectId)
                    .velocity(key)
                    .context(JSONObject.toJSONString(value))
                    .build()
                    .insert());
            // 先删除旧数据
            MyFileUtil.deleteFileOrFolder(AppConstant.FILE_PATH_CODE_GENERATOR_DATA_PATH);
            // TODO 注：freemaker模板数据模型必须存在，否则会报错！！！ 之后在前端加个模板测试数据是否正确校验处理
            GenerateCodeUtil.generateTplFileData(tplFileInfoList, tplDataMap);
        }

        // 6、打包生成的代码
        String codeSrcPath = AppConstant.FILE_PATH_CODE_GENERATOR_SRC_CODE + AppConstant.SEPARATOR_SPRIT
                + parentPackageNameFinal.replace(".", AppConstant.SEPARATOR_SPRIT);
        File zipFile = MyFileUtil.zip(codeSrcPath, AppConstant.FILE_PATH_CODE_GENERATOR_ZIP, true, false);
        // 采用七牛云上传并返回地址下载文件
        return this.qiniuFileUtil.uploadFile(zipFile, "CODE_" + DateUtil.format(new Date(), "yyyy-MM-dd HH-mm-ss") + ".zip");
    }


}
