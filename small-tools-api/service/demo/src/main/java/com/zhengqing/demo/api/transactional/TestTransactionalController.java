package com.zhengqing.demo.api.transactional;

import com.zhengqing.common.api.BaseController;
import com.zhengqing.demo.service.ITransactionalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 测试api
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/3/15 9:53
 */
@Slf4j
@RestController
@RequestMapping("/web/api/demo/test/transactional")
@Api(tags = {"测试事务"})
public class TestTransactionalController extends BaseController {

    @Autowired
    private ITransactionalService transactionalService;

    @GetMapping("01")
    @ApiOperation("事务场景01")
    public void testTransactional01() {
        this.transactionalService.testTransactional01();
    }

    @GetMapping("02")
    @ApiOperation("事务场景02")
    public void testTransactional02() {
        this.transactionalService.testTransactional02();
    }

    @GetMapping("03")
    @ApiOperation("事务场景03")
    public void testTransactional03() {
        this.transactionalService.testTransactional03();
    }

    @GetMapping("04")
    @ApiOperation("事务场景04")
    public void testTransactional04() {
        this.transactionalService.testTransactional04();
    }

}
