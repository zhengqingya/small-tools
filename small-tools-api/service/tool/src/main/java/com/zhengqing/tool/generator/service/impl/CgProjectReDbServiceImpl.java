package com.zhengqing.tool.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.core.constant.AppConstant;
import com.zhengqing.tool.db.model.vo.StDbTableColumnListVO;
import com.zhengqing.tool.db.model.vo.StDbTableListVO;
import com.zhengqing.tool.db.service.IStDbJdbcService;
import com.zhengqing.tool.generator.entity.CgProjectPackage;
import com.zhengqing.tool.generator.entity.CgProjectReDb;
import com.zhengqing.tool.generator.entity.CgTableConfig;
import com.zhengqing.tool.generator.mapper.CgProjectPackageMapper;
import com.zhengqing.tool.generator.mapper.CgProjectReDbMapper;
import com.zhengqing.tool.generator.model.dto.*;
import com.zhengqing.tool.generator.model.vo.CgProjectReDbListVO;
import com.zhengqing.tool.generator.model.vo.CgTableInfoVO;
import com.zhengqing.tool.generator.model.vo.CgTableListVO;
import com.zhengqing.tool.generator.service.ICgProjectReDbService;
import com.zhengqing.tool.generator.service.ICgTableConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 代码生成器 - 项目关联数据库表 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-11-14 13:55:47
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CgProjectReDbServiceImpl extends ServiceImpl<CgProjectReDbMapper, CgProjectReDb>
        implements ICgProjectReDbService {

    @Autowired
    private CgProjectReDbMapper cgProjectReDbMapper;

    @Autowired
    private CgProjectPackageMapper cgProjectPackageMapper;

    @Autowired
    private IStDbJdbcService stDbJdbcService;

    @Autowired
    private ICgTableConfigService cgTableConfigService;

    @Override
    public IPage<CgProjectReDbListVO> listPage(CgProjectReDbListDTO params) {
        IPage<CgProjectReDbListVO> result = this.cgProjectReDbMapper.selectCodeProjectReDbs(new Page<>(), params);
        List<CgProjectReDbListVO> list = result.getRecords();
        this.handleResultData(list);
        return result;
    }

    @Override
    public List<CgProjectReDbListVO> list(CgProjectReDbListDTO params) {
        List<CgProjectReDbListVO> list = this.cgProjectReDbMapper.selectCodeProjectReDbs(params);
        this.handleResultData(list);
        return list;
    }

    @Override
    public List<CgTableListVO> tableList(CgProjectReDbTableListDTO params) {
        Integer projectReDbDataSourceId = params.getProjectReDbDataSourceId();
        String tableName = params.getTableName();

        CgProjectReDb cgProjectReDb = this.cgProjectReDbMapper.selectById(projectReDbDataSourceId);
        Integer dbDataSourceId = cgProjectReDb.getDbDataSourceId();
        String dbName = cgProjectReDb.getDbName();

        List<CgTableListVO> myTableList = Lists.newArrayList();

        List<StDbTableListVO> tableList =
                this.stDbJdbcService.getAllTablesByDataSourceIdAndDbName(dbDataSourceId, dbName, tableName);
        if (!CollectionUtils.isEmpty(tableList)) {
            tableList.forEach(e -> myTableList.add(new CgTableListVO(e.getTableName(), e.getTableComment())));
        }
        return myTableList;
    }

    @Override
    public CgTableInfoVO tableInfo(CgProjectReDbTableInfoDTO params) {
        Integer projectReDbDataSourceId = params.getProjectReDbDataSourceId();
        String tableName = params.getTableName();

        CgProjectReDb cgProjectReDb = this.cgProjectReDbMapper.selectById(projectReDbDataSourceId);
        Integer projectId = cgProjectReDb.getProjectId();
        Integer dbDataSourceId = cgProjectReDb.getDbDataSourceId();
        String dbName = cgProjectReDb.getDbName();

        CgTableInfoVO myTableInfo = new CgTableInfoVO();

        // 1、获取表信息
        StDbTableColumnListVO columnInfo =
                this.stDbJdbcService.getAllColumnsByDataSourceIdAndDbNameAndTableName(dbDataSourceId, dbName, tableName);
        if (columnInfo != null) {
            myTableInfo.setTableName(tableName);
            myTableInfo.setTableComment(columnInfo.getTableComment());
            myTableInfo.setColumnInfoList(columnInfo.getColumnInfoList());
        }

        // 2、查询是否存在之前生成代码时的检索字段
        List<CgTableConfig> cgTableConfigList =
                this.cgTableConfigService.list(CgTableConfigListDTO.builder().projectId(projectId).tableName(tableName).build());
        if (!CollectionUtils.isEmpty(cgTableConfigList)) {
            String queryColumns = cgTableConfigList.get(0).getQueryColumns();
            if (StringUtils.isNotBlank(queryColumns)) {
                myTableInfo.setQueryColumnList(Arrays.asList(queryColumns.split(",")));
            }
        }

        /// 3、获取库的关联项目包信息
        List<CgProjectPackage> cgProjectPackageList =
                this.cgProjectPackageMapper.selectList(new LambdaQueryWrapper<CgProjectPackage>()
                        .eq(CgProjectPackage::getParentId, AppConstant.PROJECT_RE_PACKAGE_PARENT_ID)
                        .eq(CgProjectPackage::getProjectId, projectId));
        myTableInfo.setPackageName(cgProjectPackageList.get(0).getName());
        return myTableInfo;
    }

    /**
     * 处理数据
     *
     * @param list: 数据
     * @return void
     * @author zhengqingya
     * @date 2020-11-14 13:55:47
     */
    private void handleResultData(List<CgProjectReDbListVO> list) {
        if (!CollectionUtils.isEmpty(list)) {

        }
    }

    @Override
    public Integer addOrUpdateData(CgProjectReDbSaveDTO params) {
        Integer projectReDbDataSourceId = params.getProjectReDbDataSourceId();
        Integer projectId = params.getProjectId();
        Integer dbDataSourceId = params.getDbDataSourceId();
        String remark = params.getRemark();
        Integer currentUserId = params.getCurrentUserId();
        String dbName = params.getDbName();

        CgProjectReDb projectReDb = new CgProjectReDb();
        projectReDb.setProjectReDbDataSourceId(projectReDbDataSourceId);
        projectReDb.setProjectId(projectId);
        projectReDb.setDbDataSourceId(dbDataSourceId);
        projectReDb.setRemark(remark);
        projectReDb.setDataReUserId(currentUserId);
        projectReDb.setDbName(dbName);

        if (projectReDbDataSourceId == null) {
            projectReDb.insert();
        } else {
            projectReDb.updateById();
        }
        return projectReDb.getProjectReDbDataSourceId();
    }

}
