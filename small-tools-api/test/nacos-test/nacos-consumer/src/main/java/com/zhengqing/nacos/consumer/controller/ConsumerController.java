package com.zhengqing.nacos.consumer.controller;

import com.zhengqing.nacos.consumer.feign.ProviderClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 消费者
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/10/7 3:50
 */
@RestController
public class ConsumerController {

    @Resource
    private ProviderClient providerClient;

    @GetMapping("/hello")
    public String hello() {
        return this.providerClient.hello();
    }

    @GetMapping("/hi")
    public String hi() {
        return "this is consumer!";
    }

}
