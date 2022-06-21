package com.zhengqing.monitor;

import com.zhengqing.common.core.constant.AppConstant;
import com.zhengqing.common.core.constant.ServiceConstant;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableAdminServer
@SpringBootApplication
@ComponentScan(basePackages = {ServiceConstant.SERVICE_BASE_PACKAGE})
@EnableDiscoveryClient // 开启服务注册发现功能
//@EnableScheduling // 开启任务调度
@EnableTransactionManagement
@EnableFeignClients(basePackages = {AppConstant.RPC_BASE_PACKAGE}) // 开启Feign并扫描Feign客户端
public class MonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }
}
