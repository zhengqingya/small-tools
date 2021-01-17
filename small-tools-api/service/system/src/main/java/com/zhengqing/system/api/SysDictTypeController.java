package com.zhengqing.system.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.validator.fieldrepeat.Update;
import com.zhengqing.system.entity.SysDictType;
import com.zhengqing.system.model.dto.SysDictTypeSaveDTO;
import com.zhengqing.system.service.ISysDictTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 基础模块 - 数据字典类型接口
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/4/15 20:50
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
    public Integer update(@Validated(Update.class) @RequestBody SysDictTypeSaveDTO params) {
        return dictTypeService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        dictTypeService.deleteType(id);
    }

}
