package com.zhengqing.tool.generator.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.tool.generator.entity.CgFreeMarkerTemplate;
import com.zhengqing.tool.generator.model.dto.CgFreeMarkerTemplateListDTO;
import com.zhengqing.tool.generator.model.dto.CgFreeMarkerTemplateSaveDTO;
import com.zhengqing.tool.generator.model.dto.CgFreeMarkerTemplateTestDataDTO;
import com.zhengqing.tool.generator.model.vo.CgFreeMarkerTemplateListVO;
import com.zhengqing.tool.generator.service.ICgFreeMarkerTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 代码生成器 - FreeMarker模板数据配置表 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-11-02 19:23:15
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
        return this.cgFreeMarkerTemplateService.listPage(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<CgFreeMarkerTemplateListVO> list(@ModelAttribute CgFreeMarkerTemplateListDTO params) {
        return this.cgFreeMarkerTemplateService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody CgFreeMarkerTemplateSaveDTO params) {
        return this.cgFreeMarkerTemplateService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody CgFreeMarkerTemplateSaveDTO params) {
        return this.cgFreeMarkerTemplateService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer freeMarkerTemplateId) {
        this.cgFreeMarkerTemplateService.deleteData(freeMarkerTemplateId);
    }

    @GetMapping("detail")
    @ApiOperation("详情")
    public CgFreeMarkerTemplate detail(@RequestParam Integer freeMarkerTemplateId) {
        return this.cgFreeMarkerTemplateService.getById(freeMarkerTemplateId);
    }

    @NoRepeatSubmit
    @PostMapping("testTemplateData")
    @ApiOperation("测试模板数据")
    public String testTemplateData(@Validated @RequestBody CgFreeMarkerTemplateTestDataDTO params) {
        return this.cgFreeMarkerTemplateService.testTemplateData(params);
    }

}
