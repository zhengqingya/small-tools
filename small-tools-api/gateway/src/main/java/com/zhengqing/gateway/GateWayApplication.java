package com.zhengqing.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.zhengqing.common.config.feign"})
@EnableDiscoveryClient // 开启服务注册发现功能
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }

}
