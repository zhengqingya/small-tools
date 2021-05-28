package com.zhengqing.demo.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.custom.RequestPostSingleParam;
import com.zhengqing.common.validator.fieldrepeat.UpdateGroup;
import com.zhengqing.common.validator.fieldrepeat.ValidList;
import com.zhengqing.common.validator.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.mapper.DemoMapper;
import com.zhengqing.demo.model.dto.DemoListDTO;
import com.zhengqing.demo.model.dto.DemoSaveDTO;
import com.zhengqing.demo.model.vo.DemoListVO;
import com.zhengqing.demo.service.IDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

/**
 * <p>
 * 测试demo 接口
 * </p>
 *
 * @author: zhengqing
 * @description:
 * @date: 2021/01/13 10:11
 */
@RestController
@RequestMapping("/web/api/demo/demo")
@Api(tags = {"测试demo接口"})
// @Transactional(rollbackFor = Exception.class)
public class DemoController extends BaseController {

    @Autowired
    private IDemoService demoService;

    @Autowired
    private DemoMapper demoMapper;

    @GetMapping("test/transactional")
    @ApiOperation("测试事务")
    public void testTransactional() {
        demoService.testTransactional();
    }

    @PostMapping("add/batch/data")
    @ApiOperation("测试插入100w数据用时")
    public void addBatchData() {
        demoService.addBatchData();
    }

    @PostMapping("test/post/param")
    @ApiOperation("测试post请求接收单个参数")
    public void testPostParam(@ApiParam("id值") @RequestPostSingleParam Integer id) {
        System.out.println(id);
    }

    @GetMapping("list/page")
    @ApiOperation("列表分页")
    public IPage<DemoListVO> listPage(@ModelAttribute DemoListDTO params) {
        return demoService.listPage(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<DemoListVO> list(@ModelAttribute DemoListDTO params) {
        return demoService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody DemoSaveDTO params) {
        return demoService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody DemoSaveDTO params) {
        return demoService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer demoId) {
        demoService.removeById(demoId);
    }

    @GetMapping("detail")
    @ApiOperation("详情")
    public Demo detail(@RequestParam Integer demoId) {
        return demoService.getById(demoId);
    }

    @PostMapping("test/list/")
    @ApiOperation("list校验测试")
    public Integer addListValid(@Valid @RequestBody ValidList<TestSaveDTO> params) {
        return 1;
    }

    @Transactional(readOnly = true)
    @GetMapping("test/update")
    @ApiOperation("测试更新")
    public Demo testUpdate() {

        Demo a = demoMapper.selectById(2);
        System.out.println(a);
        a.setUsername("xx");

        Demo b = demoMapper.selectById(2);
        System.out.println("xx::::::" + b);

        return new Demo();
    }

}

@Data
class TestSaveDTO {

    @ApiModelProperty("主键ID")
    @NotNull(message = "主键ID不能为空!")
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

}
