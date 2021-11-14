package com.zhengqing.demo.api;

import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.service.IDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Api(tags = {"测试 - seata"})
public class SeataController {

    @Autowired
    private IDemoService demoService;

    @PostMapping("saveOrUpdate")
    @ApiOperation("saveOrUpdate")
//    @GlobalTransactional(timeoutMills = 300000, name = "spring-cloud-demo-tx")
    public Demo saveOrUpdate(@Validated @RequestBody Demo demo) {
        Demo result = this.demoService.getDataByDbTest(1);

//        this.demoService.saveOrUpdate(demo);
//        int i = 1 / 0;
        return result;
    }


}
