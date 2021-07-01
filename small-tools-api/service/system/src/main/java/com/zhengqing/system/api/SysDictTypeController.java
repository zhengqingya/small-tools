package com.zhengqing.system.api;

import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.validator.fieldrepeat.UpdateGroup;
import com.zhengqing.system.entity.SysDictType;
import com.zhengqing.system.model.dto.SysDictTypeSaveDTO;
import com.zhengqing.system.service.ISysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 基础模块 - 数据字典类型接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:50
 */
@Slf4j
@RestController
@RequestMapping("/web/api/dict/type")
@Api(tags = "基础模块 - 数据字典类型接口")
public class SysDictTypeController extends BaseController {

    @Autowired
    private ISysDictTypeService dictTypeService;

    @GetMapping("/list")
    @ApiOperation("列表")
    public List<SysDictType> list() {
        return dictTypeService.list();
    }

    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody SysDictTypeSaveDTO params) {
        return dictTypeService.addOrUpdateData(params);
    }

    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody SysDictTypeSaveDTO params) {
        return dictTypeService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        dictTypeService.deleteType(id);
    }

}
