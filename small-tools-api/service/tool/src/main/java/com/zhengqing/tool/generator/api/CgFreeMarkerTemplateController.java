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
import com.zhengqing.common.validator.fieldrepeat.Update;
import com.zhengqing.common.validator.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.tool.generator.entity.CgFreeMarkerTemplate;
import com.zhengqing.tool.generator.model.dto.CgFreeMarkerTemplateListDTO;
import com.zhengqing.tool.generator.model.dto.CgFreeMarkerTemplateSaveDTO;
import com.zhengqing.tool.generator.model.dto.CgFreeMarkerTemplateTestDataDTO;
import com.zhengqing.tool.generator.model.vo.CgFreeMarkerTemplateListVO;
import com.zhengqing.tool.generator.service.ICgFreeMarkerTemplateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 代码生成器 - FreeMarker模板数据配置表 接口
 * </p>
 *
 * @author: zhengqing
 * @description:
 * @date: 2020-11-02 19:23:15
 *
 */
@RestController
@RequestMapping("/web/api/generator/freeMarkerTemplate")
@Api(tags = {"代码生成器 - FreeMarker模板数据配置表接口"})
public class CgFreeMarkerTemplateController extends BaseController {

    @Autowired
    private ICgFreeMarkerTemplateService cgFreeMarkerTemplateService;

    @GetMapping("listPage")
    @ApiOperation("列表分页")
    public IPage<CgFreeMarkerTemplateListVO> listPage(@ModelAttribute CgFreeMarkerTemplateListDTO params) {
        return cgFreeMarkerTemplateService.listPage(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<CgFreeMarkerTemplateListVO> list(@ModelAttribute CgFreeMarkerTemplateListDTO params) {
        return cgFreeMarkerTemplateService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody CgFreeMarkerTemplateSaveDTO params) {
        return cgFreeMarkerTemplateService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(Update.class) @RequestBody CgFreeMarkerTemplateSaveDTO params) {
        return cgFreeMarkerTemplateService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer freeMarkerTemplateId) {
        cgFreeMarkerTemplateService.deleteData(freeMarkerTemplateId);
    }

    @GetMapping("detail")
    @ApiOperation("详情")
    public CgFreeMarkerTemplate detail(@RequestParam Integer freeMarkerTemplateId) {
        return cgFreeMarkerTemplateService.getById(freeMarkerTemplateId);
    }

    @NoRepeatSubmit
    @PostMapping("testTemplateData")
    @ApiOperation("测试模板数据")
    public String testTemplateData(@Validated @RequestBody CgFreeMarkerTemplateTestDataDTO params) {
        return cgFreeMarkerTemplateService.testTemplateData(params);
    }

}
