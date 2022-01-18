package com.zhengqing.tool.db.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.custom.validator.common.UpdateGroup;
import com.zhengqing.tool.db.entity.StDbDataSource;
import com.zhengqing.tool.db.model.dto.StDbDataSourceListDTO;
import com.zhengqing.tool.db.model.dto.StDbDataSourceSaveDTO;
import com.zhengqing.tool.db.model.vo.StDbDataSourceListVO;
import com.zhengqing.tool.db.service.IStDbDataSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 小工具 - 数据库 - 数据源配置信息表 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-09-02 15:04:12
 */
@RestController
@RequestMapping("/web/api/db/dataSource")
@Api(tags = {"小工具 - 数据库 - 数据源配置信息表接口"})
public class StDbDataSourceController extends BaseController {

    @Autowired
    private IStDbDataSourceService stDbDataSourceService;

    @GetMapping("listPage")
    @ApiOperation("列表分页")
    public IPage<StDbDataSourceListVO> listPage(@ModelAttribute StDbDataSourceListDTO params) {
        return this.stDbDataSourceService.listPage(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<StDbDataSourceListVO> list(@ModelAttribute StDbDataSourceListDTO params) {
        return this.stDbDataSourceService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody StDbDataSourceSaveDTO params) {
        return this.stDbDataSourceService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody StDbDataSourceSaveDTO params) {
        return this.stDbDataSourceService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.stDbDataSourceService.removeById(id);
    }

    @GetMapping("detail")
    @ApiOperation("详情")
    public StDbDataSource detail(@RequestParam Integer id) {
        return this.stDbDataSourceService.getById(id);
    }

}
