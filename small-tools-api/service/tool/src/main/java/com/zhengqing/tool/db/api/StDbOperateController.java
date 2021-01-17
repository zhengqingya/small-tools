package com.zhengqing.tool.db.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.validator.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.tool.db.model.dto.StDbTableColumnSaveDTO;
import com.zhengqing.tool.db.model.vo.StDbDatabaseListVO;
import com.zhengqing.tool.db.model.vo.StDbTableColumnListVO;
import com.zhengqing.tool.db.model.vo.StDbTableListVO;
import com.zhengqing.tool.db.service.IStDbJdbcService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 小工具 - 数据库 - 数据库操作处理 接口
 * </p>
 *
 * @author: zhengqing
 * @description:
 * @date: 2020-09-02 15:04:12
 *
 */
@RestController
@RequestMapping("/web/api/db/operate")
@Api(tags = {"小工具 - 数据库 - 数据库操作处理 接口"})
public class StDbOperateController extends BaseController {

    @Autowired
    private IStDbJdbcService stDbJdbcService;

    @GetMapping("connectTest")
    @ApiOperation("连接测试")
    public void connectTest(@RequestParam Integer dataSourceId, @RequestParam(required = false) String dbName) {
        stDbJdbcService.connectTest(dataSourceId, dbName);
    }

    @GetMapping("getAllDatabasesByDataSourceId")
    @ApiOperation("数据库列表")
    public List<StDbDatabaseListVO> getAllDatabasesByDataSourceId(@RequestParam Integer dataSourceId) {
        return stDbJdbcService.getAllDatabasesByDataSourceId(dataSourceId);
    }

    @GetMapping("getAllTablesByDataSourceIdAndDbName")
    @ApiOperation("表列表")
    public List<StDbTableListVO> getAllTablesByDataSourceIdAndDbName(@RequestParam Integer dataSourceId,
        @RequestParam String dbName, @RequestParam(required = false) String tableName) {
        return stDbJdbcService.getAllTablesByDataSourceIdAndDbName(dataSourceId, dbName, tableName);
    }

    @GetMapping("getAllColumnsByDataSourceIdAndDbNameAndTableName")
    @ApiOperation("表字段列表")
    public StDbTableColumnListVO getAllColumnsByDataSourceIdAndDbNameAndTableName(@RequestParam Integer dataSourceId,
        @RequestParam String dbName, @RequestParam String tableName) {
        return stDbJdbcService.getAllColumnsByDataSourceIdAndDbNameAndTableName(dataSourceId, dbName, tableName);
    }

    @NoRepeatSubmit
    @PutMapping("updateColumnInfo")
    @ApiOperation("更新表字段信息")
    public void updateColumnInfo(@Valid @RequestBody StDbTableColumnSaveDTO params) {
        stDbJdbcService.updateColumnInfo(params);
    }

    @GetMapping("tableInfoToWordByDataSourceIdAndDbName")
    @ApiOperation("导出数据库表信息生成Word文档")
    public String tableInfoToWordByDataSourceIdAndDbName(@RequestParam Integer dataSourceId,
        @RequestParam String dbName) {
        return stDbJdbcService.tableInfoToWordByDataSourceIdAndDbName(dataSourceId, dbName);
    }

}
