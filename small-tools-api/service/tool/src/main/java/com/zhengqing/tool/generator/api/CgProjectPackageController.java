package com.zhengqing.tool.generator.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.validator.fieldrepeat.Update;
import com.zhengqing.common.validator.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.tool.generator.model.dto.CgProjectPackageListDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectPackageSaveDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectPackageTreeDTO;
import com.zhengqing.tool.generator.model.vo.CgProjectPackageListVO;
import com.zhengqing.tool.generator.model.vo.CgProjectPackageTreeVO;
import com.zhengqing.tool.generator.service.ICgProjectPackageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 代码生成器 - 项目关联包管理接口 接口
 * </p>
 *
 * @author: zhengqing
 * @description:
 * @date: 2019-09-09
 */
@RestController
@RequestMapping("/web/api/generator/projectPackage")
@Api(tags = "代码生成器 - 项目关联包管理接口")
public class CgProjectPackageController extends BaseController {

    @Autowired
    private ICgProjectPackageService cgProjectPackageService;

    @GetMapping("list")
    @ApiOperation("列表")
    public List<CgProjectPackageListVO> list(@ModelAttribute CgProjectPackageListDTO params) {
        return cgProjectPackageService.list(params);
    }

    @GetMapping("tree")
    @ApiOperation("项目包架构树")
    public List<CgProjectPackageTreeVO> tree(@ModelAttribute CgProjectPackageTreeDTO params) {
        return cgProjectPackageService.tree(params);
    }

    @NoRepeatSubmit
    @PostMapping()
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody CgProjectPackageSaveDTO params) {
        return cgProjectPackageService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping()
    @ApiOperation("更新")
    public Integer update(@Validated(Update.class) @RequestBody CgProjectPackageSaveDTO params) {
        return cgProjectPackageService.addOrUpdateData(params);
    }

    @DeleteMapping()
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        cgProjectPackageService.deleteData(id);
    }

}
