package com.zhengqing.gateway.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 网关测试api
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/10/7 20:39
 */
@RestController
@Api(tags = "网关测试api")
public class GatewayController {

    @Value("${server.port}")
    private String port;

    @Value("${logging.config}")
    private String loggingConfig;

    @GetMapping("/index")
    @ApiOperation("index")
    public String hello() {
        return "port:" + port + "loggingConfig:" + loggingConfig;
    }

    @GetMapping("/hi")
    @ApiOperation("hi")
    public String hei() {
        return "this is gateway!";
    }

}
