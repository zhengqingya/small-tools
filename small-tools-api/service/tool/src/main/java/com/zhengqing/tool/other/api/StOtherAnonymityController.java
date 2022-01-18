package com.zhengqing.tool.other.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.custom.validator.common.UpdateGroup;
import com.zhengqing.tool.other.model.dto.StOtherAnonymityHandleDTO;
import com.zhengqing.tool.other.model.dto.StOtherAnonymityListDTO;
import com.zhengqing.tool.other.model.dto.StOtherAnonymitySaveDTO;
import com.zhengqing.tool.other.model.vo.StOtherAnonymityListVO;
import com.zhengqing.tool.other.service.IStOtherAnonymityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 小工具 - 其它 - 匿名事件表 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-10-25 13:27:16
 */
@RestController
@RequestMapping("/web/api/other/anonymity")
@Api(tags = {"小工具 - 其它 - 匿名事件表接口"})
public class StOtherAnonymityController extends BaseController {

    @Autowired
    private IStOtherAnonymityService stOtherAnonymityService;

    @GetMapping("listPage")
    @ApiOperation("列表分页")
    public IPage<StOtherAnonymityListVO> listPage(@ModelAttribute StOtherAnonymityListDTO params) {
        return this.stOtherAnonymityService.listPage(params);
    }

    // @GetMapping("/list")
    // @ApiOperation("列表")
    // public List<StOtherAnonymityVO> list(@ModelAttribute StOtherAnonymityListDTO params) {
    // return stOtherAnonymityService.list(params);
    // }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody StOtherAnonymitySaveDTO params) {
        return this.stOtherAnonymityService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody StOtherAnonymitySaveDTO params) {
        return this.stOtherAnonymityService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("handle")
    @ApiOperation("处理")
    public void handle(@Validated @RequestBody StOtherAnonymityHandleDTO params) {
        this.stOtherAnonymityService.handle(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.stOtherAnonymityService.removeById(id);
    }

    // @GetMapping("/detail")
    // @ApiOperation("详情")
    // public StOtherAnonymity detail(@RequestParam Integer stOtherAnonymityId) {
    // return stOtherAnonymityService.getById(stOtherAnonymityId);
    // }

}
