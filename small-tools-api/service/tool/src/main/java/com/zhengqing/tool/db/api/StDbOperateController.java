package com.zhengqing.tool.db.api;

import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.tool.db.model.dto.StDbTableColumnSaveDTO;
import com.zhengqing.tool.db.model.vo.StDbDatabaseListVO;
import com.zhengqing.tool.db.model.vo.StDbTableColumnListVO;
import com.zhengqing.tool.db.model.vo.StDbTableListVO;
import com.zhengqing.tool.db.service.IStDbJdbcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 小工具 - 数据库 - 数据库操作处理 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-09-02 15:04:12
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
        this.stDbJdbcService.connectTest(dataSourceId, dbName);
    }

    @GetMapping("getAllDatabasesByDataSourceId")
    @ApiOperation("数据库列表")
    public List<StDbDatabaseListVO> getAllDatabasesByDataSourceId(@RequestParam Integer dataSourceId) {
        return this.stDbJdbcService.getAllDatabasesByDataSourceId(dataSourceId);
    }

    @GetMapping("getAllTablesByDataSourceIdAndDbName")
    @ApiOperation("表列表")
    public List<StDbTableListVO> getAllTablesByDataSourceIdAndDbName(@RequestParam Integer dataSourceId,
                                                                     @RequestParam String dbName, @RequestParam(required = false) String tableName) {
        return this.stDbJdbcService.getAllTablesByDataSourceIdAndDbName(dataSourceId, dbName, tableName);
    }

    @GetMapping("getAllColumnsByDataSourceIdAndDbNameAndTableName")
    @ApiOperation("表字段列表")
    public StDbTableColumnListVO getAllColumnsByDataSourceIdAndDbNameAndTableName(@RequestParam Integer dataSourceId,
                                                                                  @RequestParam String dbName, @RequestParam String tableName) {
        return this.stDbJdbcService.getAllColumnsByDataSourceIdAndDbNameAndTableName(dataSourceId, dbName, tableName);
    }

    @NoRepeatSubmit
    @PutMapping("updateColumnInfo")
    @ApiOperation("更新表字段信息")
    public void updateColumnInfo(@Valid @RequestBody StDbTableColumnSaveDTO params) {
        this.stDbJdbcService.updateColumnInfo(params);
    }

    @GetMapping("tableInfoToWordByDataSourceIdAndDbName")
    @ApiOperation("导出数据库表信息生成Word文档")
    public String tableInfoToWordByDataSourceIdAndDbName(@RequestParam Integer dataSourceId,
                                                         @RequestParam String dbName) {
        return this.stDbJdbcService.tableInfoToWordByDataSourceIdAndDbName(dataSourceId, dbName);
    }

}
