package com.zhengqing.nacos.provider.controller;

import com.zhengqing.nacos.provider.config.AppConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 生产者$
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/10/7$ 3:14$
 */
@RefreshScope // 实现配置自动更新
@RestController
public class ProviderController {

    // SPEL
    @Value("${myName}")
    private String name;

    @Value("${test}")
    private String test;

    @Resource
    private AppConfig appConfig;

//    @Value("#{'${order.print.remark-filter-str-list}'.split('-')}")
//    private List<String> strList;

    @GetMapping("/hello")
    public String hello() {
        // int result = 1 / 0;
        return "《hello》:" + this.name + " *** 《test》:" + this.test;
    }

    @GetMapping("/hi")
    public String hi() {
        return "this is provider!";
    }

}
