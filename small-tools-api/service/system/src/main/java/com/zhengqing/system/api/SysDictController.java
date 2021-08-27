package com.zhengqing.system.api;

import com.google.common.collect.Lists;
import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.validator.fieldrepeat.UpdateGroup;
import com.zhengqing.system.model.dto.SysDictSaveBatchDTO;
import com.zhengqing.system.model.dto.SysDictSaveDTO;
import com.zhengqing.system.model.vo.SysDictVO;
import com.zhengqing.system.service.ISysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基础模块 - 数据字典接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:50
 */
@Slf4j
@RestController
@RequestMapping("/web/api/dict")
@Api(tags = "基础模块 - 数据字典接口")
public class SysDictController extends BaseController {

    @Autowired
    private ISysDictService sysDictService;

    @PostMapping("initCache")
    @ApiOperation("初始化缓存数据")
    public void initCache() {
        this.sysDictService.initCache();
    }

    @GetMapping("listByCode")
    @ApiOperation("通过编码获取数据字典列表信息（启用+禁用数据）")
    public List<SysDictVO> listByCode(@RequestParam String code) {
        return this.sysDictService.listByCode(code);
    }

    @GetMapping("listByOpenCode")
    @ApiOperation("通过编码获取数据字典列表（只含启用数据）")
    public Map<String, List<SysDictVO>> listByOpenCode(@RequestParam List<String> codeList) {
        return this.sysDictService.listByOpenCode(codeList);
    }

    @GetMapping("listFromDbByCode")
    @ApiOperation("通过编码获取数据字典列表信息 - 数据库方式（只含启用数据）")
    public List<SysDictVO> listFromDbByCode(@RequestParam String code) {
        return this.sysDictService.listFromDbByOpenCode(Lists.newArrayList(code)).get(code);
    }

    @GetMapping("listFromCacheByCode")
    @ApiOperation("通过编码获取数据字典列表信息 - 缓存方式（只含启用数据）")
    public List<SysDictVO> listFromCacheByCode(@RequestParam String code) {
        return this.sysDictService.listFromCacheByCode(Lists.newArrayList(code)).get(code);
    }

    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody SysDictSaveDTO params) {
        params.setId(null);
        return this.sysDictService.addOrUpdateData(params);
    }

    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody SysDictSaveDTO params) {
        return this.sysDictService.addOrUpdateData(params);
    }

    @PutMapping("updateBatch")
    @ApiOperation("批量更新")
    public void updateBatch(@Validated @RequestBody SysDictSaveBatchDTO params) {
        this.sysDictService.updateBatch(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.sysDictService.deleteDictById(id);
    }

}
