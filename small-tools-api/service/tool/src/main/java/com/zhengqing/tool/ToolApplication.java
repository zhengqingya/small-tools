package com.zhengqing.tool;

import com.zhengqing.common.core.constant.AppConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {AppConstant.BASE_PACKAGES})
@EnableDiscoveryClient // 开启服务注册发现功能
@EnableScheduling // 开启任务调度
@EnableTransactionManagement
@EnableFeignClients(basePackages = {AppConstant.BASE_PACKAGES}) // 开启Feign并扫描Feign客户端
public class ToolApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToolApplication.class, args);
    }
}
