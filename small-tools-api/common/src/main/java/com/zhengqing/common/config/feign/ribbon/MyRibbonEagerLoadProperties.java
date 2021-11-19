package com.zhengqing.common.config.feign.ribbon;

import com.zhengqing.common.constant.ProjectConstant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonEagerLoadProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <p>
 * 自定义配置ribbon饥饿加载模式
 * </p>
 *
 * @author zhengqingya
 * @description {@link org.springframework.cloud.netflix.ribbon.RibbonEagerLoadProperties }
 * @date 2021/11/18 10:55 下午
 */
@Data
@Slf4j
@Configuration
public class MyRibbonEagerLoadProperties {

    @Autowired
    private RibbonEagerLoadProperties ribbonEagerLoadProperties;

    @Bean
    public void addRibbonClients() {
        // 默认强行开启饥饿加载模式并加载系统所有服务名
        this.ribbonEagerLoadProperties.setEnabled(true);
        this.ribbonEagerLoadProperties.setClients(ProjectConstant.ALL_RPC_SERVICE_NAME_LIST);
    }

}
