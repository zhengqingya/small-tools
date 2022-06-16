package com.zhengqing.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// 默认只会扫当前模块包，需要额外加上其它模块的
@ComponentScan(basePackages = {
        "com.zhengqing.gateway",
        "com.zhengqing.common.redis.util"
})
@EnableDiscoveryClient // 开启服务注册发现功能
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }

}
