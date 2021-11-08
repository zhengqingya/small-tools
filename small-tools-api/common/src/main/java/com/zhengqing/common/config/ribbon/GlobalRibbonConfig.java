package com.zhengqing.common.config.ribbon;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> Ribbon配置类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/11/8 17:32
 */
@Configuration
public class GlobalRibbonConfig {

    @Bean
    public IRule getRule() {
        // 实现同一集群带版本优先的服务负载均衡策略
        return new TheSameClusterPriorityWithVersionRule();
    }

}
