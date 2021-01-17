package com.zhengqing.nacos.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhengqing.nacos.consumer.feign.ProviderClient;

/**
 * <p>
 * 消费者
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/10/7 3:50
 */
@RestController
public class ConsumerController {

    @Autowired
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
