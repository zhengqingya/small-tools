package com.zhengqing.demo.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.context.SysUserContext;
import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.post.RequestPostSingleParam;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.model.dto.DemoListDTO;
import com.zhengqing.demo.model.dto.DemoSaveDTO;
import com.zhengqing.demo.model.vo.DemoListVO;
import com.zhengqing.demo.service.IDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 测试demo 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/01/13 10:11
 */
@Slf4j
@RestController
@RequestMapping("/web/api/demo/demo")
@Api(tags = {"测试demo接口"})
// @Transactional(rollbackFor = Exception.class)
public class DemoController extends BaseController {

    @Resource
    private IDemoService demoService;

    @GetMapping("test/tenantId")
    @ApiOperation("测试-租户id")
    public Demo testTenantId() {
//        TenantIdContext.removeFlag();
        return this.demoService.getById(1L);
    }

    @GetMapping("test/dataPermission")
    @ApiOperation("测试数据权限")
    public IPage<DemoListVO> testDataPermission(@Validated @ModelAttribute DemoListDTO params) {
        return this.demoService.testDataPermission(params);
    }

    @GetMapping("test/dataScope")
    @ApiOperation("测试数据范围（数据权限）")
    public void testDataScope() {
        this.demoService.testDataScope();
    }

    @GetMapping("test/transactional")
    @ApiOperation("测试事务")
    public void testTransactional() {
        this.demoService.testTransactional();
    }

    @PostMapping("add/batch/data")
    @ApiOperation("测试插入数据用时")
    public String addBatchData(@ApiParam(value = "插入数据量", required = true, example = "10000")
                               @RequestParam int addSum) {
        return this.demoService.addBatchData(addSum);
    }

    @PostMapping("test/post/param")
    @ApiOperation("测试post请求接收单个参数")
    public void testPostParam(@ApiParam("id值") @RequestPostSingleParam Integer id) {
        System.out.println(id);
    }

    @GetMapping("page")
    @ApiOperation("列表分页")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "params", value = "提交参数")
//    })
    public IPage<DemoListVO> page(@Validated @ModelAttribute DemoListDTO params) {
        return this.demoService.page(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<DemoListVO> list(@ModelAttribute DemoListDTO params) {
        return this.demoService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Long add(@Validated @RequestBody DemoSaveDTO params) {
        params.setId(null);
        return this.demoService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Long update(@Validated(UpdateGroup.class) @RequestBody DemoSaveDTO params) {
        return this.demoService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer demoId) {
        this.demoService.removeById(demoId);
    }

    @GetMapping("{id}")
    @ApiOperation("详情")
    public Demo detail(@ApiParam("主键ID") @PathVariable Long id) {
        log.info("系统用户：{}", SysUserContext.getUsername());
        return this.demoService.getById(id);
    }

    /**
     * `@Valid`: 可嵌套校验，不支持分组校验
     * `@Validated`: 不支持嵌套校验，但支持分组校验
     */
    @PostMapping("test/list/")
    @ApiOperation("list校验测试")
    public Integer addListValid(@Valid @RequestBody ValidList<TestSaveDTO> params) {
        TestSaveDTO testSaveDTO = TestSaveDTO.builder().id(1).username("xx").password("xxx").build();
        return 1;
    }

    @Transactional(readOnly = true)
    @GetMapping("test/update")
    @ApiOperation("测试更新")
    public Demo testUpdate() {

        Demo a = this.demoService.getById(2);
        System.out.println(a);
        a.setUsername("xx");

        Demo b = this.demoService.getById(2);
        System.out.println("xx::::::" + b);

        return new Demo();
    }

    @PostMapping("test/builder/")
    @ApiOperation("testBuilder")
    public void testBuilder() {

    }


//    @NoRepeatSubmit
//    @PostMapping("importData")
//    @ApiOperation("导入数据")
//    public String importData(@RequestParam(value = "file", required = false) MultipartFile file,
//                             @RequestParam(value = "userId", required = false) Integer userId,
////                             @ModelAttribute List<DemoSaveDTO> list,
//                             HttpServletRequest request) {
//        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
//        MultipartFile fileNew = multipartRequest.getFile("file");
//        String websiteIdStr = multipartRequest.getParameter("userId");
//        return "OK" + websiteIdStr;
//    }

}

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
class TestSaveDTO extends BaseDTO {

    @ApiModelProperty("主键ID")
    @NotNull(message = "主键ID不能为空!")
    private Integer id;


    @NotBlank(message = "用户名不能为空!")
    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    //    @NotEmpty(message = "list不能为空！")
    @ApiModelProperty("list")
    private List<TestSaveDTO> list;

}
