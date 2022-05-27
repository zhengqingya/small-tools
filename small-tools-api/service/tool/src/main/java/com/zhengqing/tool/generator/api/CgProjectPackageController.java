package com.zhengqing.tool.generator.api;

import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.tool.generator.model.dto.CgProjectPackageListDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectPackageSaveDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectPackageTreeDTO;
import com.zhengqing.tool.generator.model.vo.CgProjectPackageListVO;
import com.zhengqing.tool.generator.model.vo.CgProjectPackageTreeVO;
import com.zhengqing.tool.generator.service.ICgProjectPackageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 代码生成器 - 项目关联包管理接口 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019-09-09
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
        return this.cgProjectPackageService.list(params);
    }

    @GetMapping("tree")
    @ApiOperation("项目包架构树")
    public List<CgProjectPackageTreeVO> tree(@ModelAttribute CgProjectPackageTreeDTO params) {
        return this.cgProjectPackageService.tree(params);
    }

    @NoRepeatSubmit
    @PostMapping()
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody CgProjectPackageSaveDTO params) {
        return this.cgProjectPackageService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping()
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody CgProjectPackageSaveDTO params) {
        return this.cgProjectPackageService.addOrUpdateData(params);
    }

    @DeleteMapping()
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.cgProjectPackageService.deleteData(id);
    }

}
