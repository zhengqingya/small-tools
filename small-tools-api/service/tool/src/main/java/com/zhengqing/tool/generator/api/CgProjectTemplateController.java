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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.validator.fieldrepeat.UpdateGroup;
import com.zhengqing.common.validator.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.tool.generator.entity.CgProjectVelocityContext;
import com.zhengqing.tool.generator.model.dto.CgProjectTemplateListDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectTemplateSaveDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectTemplateTestDataDTO;
import com.zhengqing.tool.generator.model.vo.CgProjectTemplateListVO;
import com.zhengqing.tool.generator.service.ICgProjectTemplateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 代码生成器 - 项目代码模板接口
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2019/8/20 15:22
 */
@RestController
@RequestMapping("/web/api/generator/projectTemplate")
@Api(tags = "代码生成器 - 项目代码模板接口")
public class CgProjectTemplateController extends BaseController {

    @Autowired
    private ICgProjectTemplateService templateService;

    @GetMapping("listPage")
    @ApiOperation("列表分页")
    public IPage<CgProjectTemplateListVO> listPage(@ModelAttribute CgProjectTemplateListDTO params) {
        return templateService.listPage(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<CgProjectTemplateListVO> list(@ModelAttribute CgProjectTemplateListDTO params) {
        return templateService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody CgProjectTemplateSaveDTO params) {
        return templateService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody CgProjectTemplateSaveDTO params) {
        return templateService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PostMapping("testTemplateData")
    @ApiOperation("测试模板数据")
    public String testTemplateData(@Validated @RequestBody CgProjectTemplateTestDataDTO params) {
        return templateService.testTemplateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer projectTemplateId) {
        templateService.removeById(projectTemplateId);
    }

    @GetMapping("")
    @ApiOperation("校验模板数据是否正确")
    public void checkTemplateData(@RequestParam Integer projectTemplateId) {
        templateService.checkTemplateData(projectTemplateId);
    }

    @PostMapping("generateTemplate")
    @ApiOperation("生成项目代码模板")
    public void generateTemplate(@RequestParam Integer projectId) {
        templateService.generateTemplate(projectId);
    }

    @GetMapping("listPageCodeProjectVelocityContext")
    @ApiOperation("获取项目代码模板对应数据源模板列表")
    public IPage<CgProjectVelocityContext>
        listPageCodeProjectVelocityContext(@ModelAttribute CgProjectTemplateListDTO params) {
        return templateService.listPageCodeProjectVelocityContext(params);
    }

}
