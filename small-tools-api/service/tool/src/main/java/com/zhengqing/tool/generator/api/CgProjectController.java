package com.zhengqing.tool.generator.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.validator.fieldrepeat.UpdateGroup;
import com.zhengqing.common.validator.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.tool.generator.model.dto.CgGenerateCodeDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectListDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectSaveDTO;
import com.zhengqing.tool.generator.model.vo.CgProjectListVO;
import com.zhengqing.tool.generator.service.ICgGeneratorCodeService;
import com.zhengqing.tool.generator.service.ICgProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 代码生成器 - 项目管理 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019-09-09
 */
@RestController
@RequestMapping("/web/api/generator/project")
@Api(tags = "代码生成器 - 项目管理接口")
public class CgProjectController extends BaseController {

    @Autowired
    private ICgProjectService projectService;

    @Autowired
    private ICgGeneratorCodeService cgGeneratorCodeService;

    // @RequiresPermissions("code:project:list")
    @GetMapping("listPage")
    @ApiOperation("列表分页")
    public IPage<CgProjectListVO> listPage(@ModelAttribute CgProjectListDTO filter) {
        return projectService.listPage(filter);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<CgProjectListVO> list(@ModelAttribute CgProjectListDTO filter) {
        return projectService.list(filter);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody CgProjectSaveDTO params) {
        return projectService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody CgProjectSaveDTO params) {
        return projectService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        projectService.deleteData(id);
    }

    @PostMapping("generateCode")
    @ApiOperation("生成代码")
    public String generateCode(@Validated @RequestBody CgGenerateCodeDTO params) {
        return cgGeneratorCodeService.generateCode(params);
    }

}
