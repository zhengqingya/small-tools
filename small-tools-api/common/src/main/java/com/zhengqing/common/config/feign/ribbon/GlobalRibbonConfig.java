package com.zhengqing.common.config.feign.ribbon;

import com.netflix.loadbalancer.IRule;
import com.zhengqing.common.config.feign.ribbon.weight.BalancerWeightRule;
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
        // 自定义负载均衡策略
        // 同一集群优先带版本实例
//        return new BalancerVersionRule();
        // 权重
        return new BalancerWeightRule();
    }

}
