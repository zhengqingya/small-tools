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
import com.zhengqing.common.validator.fieldrepeat.UpdateGroup;
import com.zhengqing.system.model.dto.SysDictSaveDTO;
import com.zhengqing.system.model.vo.SysDictVO;
import com.zhengqing.system.service.ISysDictService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 基础模块 - 数据字典接口
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/4/15 20:50
 */
@Slf4j
@RestController
@RequestMapping("/web/api/dict")
@Api(tags = "基础模块 - 数据字典接口")
public class SysDictController extends BaseController {

    @Autowired
    private ISysDictService dictService;

    @GetMapping("/listByCode")
    @ApiOperation("通过编码获取数据字典列表信息（启用+禁用数据）")
    public List<SysDictVO> listByCode(@RequestParam String code) {
        return dictService.getAllDictListByCode(code);
    }

    @GetMapping("/listFromDbByCode")
    @ApiOperation("通过编码获取数据字典列表信息 - 数据库方式（只含启用数据）")
    public List<SysDictVO> listFromDbByCode(@RequestParam String code) {
        return dictService.getUpDictListFromDbByCode(code);
    }

    @GetMapping("/listFromCacheByCode")
    @ApiOperation("通过编码获取数据字典列表信息 - 缓存方式（只含启用数据）")
    public List<SysDictVO> listFromCacheByCode(@RequestParam String code) {
        return dictService.getUpDictListFromCacheByCode(code);
    }

    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody SysDictSaveDTO params) {
        return dictService.addOrUpdateData(params);
    }

    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody SysDictSaveDTO params) {
        return dictService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        dictService.deleteDictById(id);
    }

}
