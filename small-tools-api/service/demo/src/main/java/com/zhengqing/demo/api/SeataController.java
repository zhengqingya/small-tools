package com.zhengqing.demo.api;

import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.service.ISeataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 测试 - seata
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/01/13 10:11
 */
@Slf4j
@RestController
@RequestMapping("/test/seata")
@Api(tags = {"test-seata"})
public class SeataController {

    @Resource
    private ISeataService seataService;

    @PostMapping("saveOrUpdate")
    @ApiOperation("saveOrUpdate")
//    @GlobalTransactional(timeoutMills = 300000, name = "spring-cloud-demo-tx")
    public void saveOrUpdate(@Validated @RequestBody Demo demo) {
        this.seataService.test(demo);
    }

}
