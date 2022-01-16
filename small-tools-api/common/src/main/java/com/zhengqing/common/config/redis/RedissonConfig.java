package com.zhengqing.common.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> Redisson配置类 </p>
 *
 * @author zhengqingya
 * @description https://www.bookstack.cn/read/redisson-wiki-zh/2.-%E9%85%8D%E7%BD%AE%E6%96%B9%E6%B3%95.md
 * @date 2022/1/14 11:03 下午
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port).setPassword(password);
        return Redisson.create(config);
    }

}
