package com.zhengqing.gateway;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.web.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

// 默认只会扫当前模块包，需要额外加上其它模块的
//@ComponentScan(basePackages = {
//        "com.zhengqing.gateway",
//        "com.zhengqing.common",
//})
@ComponentScan(
        basePackages = {ServiceConstant.SERVICE_BASE_PACKAGE},
        // 排除指定配置类
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                value = {WebConfig.class}
        )
)
@EnableDiscoveryClient // 开启服务注册发现功能
@SpringBootApplication
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }

}
