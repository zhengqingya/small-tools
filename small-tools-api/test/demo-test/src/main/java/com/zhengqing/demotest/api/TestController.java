package com.zhengqing.demotest.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 测试api
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2021/1/9 1:38
 */
@Slf4j
@RestController
@RequestMapping("")
public class TestController {

    @Value(value = "${spring.application.name}")
    private String applicationName;

    @GetMapping("/index")
    public String index() {
        return "您好，欢迎访问【" + applicationName + "】";
    }

    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        // 模拟业务耗时处理流程
        Thread.sleep(2 * 1000L);
        return "hello";
    }

}
