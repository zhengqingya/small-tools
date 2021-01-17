package com.zhengqing.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zhengqing.common.constant.AppConstant;

@SpringBootApplication
@ComponentScan(basePackages = {AppConstant.BASE_PACKAGES})
@EnableDiscoveryClient // 开启服务注册发现功能
@EnableTransactionManagement
@EnableFeignClients(basePackages = {AppConstant.BASE_PACKAGES}) // 开启Feign并扫描Feign客户端
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
