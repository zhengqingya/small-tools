package com.zhengqing.tool.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.tool.generator.entity.CgTableConfig;
import com.zhengqing.tool.generator.enums.CgGenerateTemplateDataTypeEnum;
import com.zhengqing.tool.generator.mapper.CgTableConfigMapper;
import com.zhengqing.tool.generator.model.dto.CgTableConfigListDTO;
import com.zhengqing.tool.generator.model.dto.CgTableConfigSaveDTO;
import com.zhengqing.tool.generator.service.ICgTableConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.StringJoiner;

/**
 * <p>
 * 代码生成器 - 项目数据表配置信息表 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/14 23:11
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CgTableConfigServiceImpl extends ServiceImpl<CgTableConfigMapper, CgTableConfig>
        implements ICgTableConfigService {

    @Resource
    private CgTableConfigMapper cgTableConfigMapper;

    @Override
    public List<CgTableConfig> list(CgTableConfigListDTO params) {
        return this.cgTableConfigMapper.selectDataList(params);
    }

    @Override
    public CgTableConfig addOrUpdateData(CgTableConfigSaveDTO params) {
        Integer projectId = params.getProjectId();
        String tableName = params.getTableName();
        List<String> queryColumnList = params.getQueryColumnList();
        Integer projectReDbDataSourceId = params.getProjectReDbDataSourceId();
        String packageName = params.getPackageName();
        String moduleName = params.getModuleName();
        Integer currentUserId = params.getCurrentUserId();
        Integer dataType = params.getDataType();

        StringJoiner queryColumnSj = new StringJoiner(",");
        if (!CollectionUtils.isEmpty(queryColumnList)) {
            queryColumnList.forEach(queryColumnSj::add);
        }

        // 1、先删除旧数据
        this.cgTableConfigMapper.delete(new LambdaQueryWrapper<CgTableConfig>()
                .eq(CgTableConfig::getProjectId, projectId)
                .eq(CgTableConfig::getTableName, tableName));

        // 将所有数据都先设置为默认数据
        if (CgGenerateTemplateDataTypeEnum.测试模板生成配置数据.getType().equals(dataType)) {
            CgTableConfigListDTO tableConfigListDTO = new CgTableConfigListDTO();
            tableConfigListDTO.setProjectId(projectId);
            tableConfigListDTO.setCurrentUserId(currentUserId);
            List<CgTableConfig> cgTableConfigList = this.list(tableConfigListDTO);
            cgTableConfigList.forEach(e -> e.setDataType(CgGenerateTemplateDataTypeEnum.默认数据.getType()));
            this.saveOrUpdateBatch(cgTableConfigList);
        }

        // 2、保存数据
        CgTableConfig cgTableConfig = new CgTableConfig();
        cgTableConfig.setProjectId(projectId);
        cgTableConfig.setTableName(tableName);
        cgTableConfig.setQueryColumns(queryColumnSj.toString());
        cgTableConfig.setProjectReDbDataSourceId(projectReDbDataSourceId);
        cgTableConfig.setPackageName(packageName);
        cgTableConfig.setModuleName(moduleName);
        cgTableConfig.setDataType(dataType);
        cgTableConfig.setDataReUserId(currentUserId);
        cgTableConfig.insert();
        return cgTableConfig;
    }

}
