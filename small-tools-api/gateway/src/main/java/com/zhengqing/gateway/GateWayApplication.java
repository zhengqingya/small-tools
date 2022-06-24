package com.zhengqing.gateway;

import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.constant.ServiceConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(
        basePackages = {
                // 默认只会扫当前模块包，需要额外加上其它模块的
//                "com.zhengqing.gateway",
//                "com.zhengqing.common.redis",
                ServiceConstant.SERVICE_BASE_PACKAGE
        }
        // 排除指定配置类
//        ,
//        excludeFilters = @ComponentScan.Filter(
//                type = FilterType.ASSIGNABLE_TYPE,
//                value = {
//                        WebConfig.class,
//                        WebAppConfig.class
//                }
//        )
)
@EnableDiscoveryClient // 开启服务注册发现功能
@SpringBootApplication
@EnableFeignClients(basePackages = {AppConstant.RPC_BASE_PACKAGE}) // 开启Feign并扫描Feign客户端
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }

}
