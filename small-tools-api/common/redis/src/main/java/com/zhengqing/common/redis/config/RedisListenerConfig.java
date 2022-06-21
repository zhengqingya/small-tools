//package com.zhengqing.common.redis.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//
///**
// * <p>
// * redis监听事件配置
// * </p>
// *
// * @author zhengqingya
// * @description
// * @date 2020/11/13 13:56
// */
//@Configuration
//public class RedisListenerConfig {
//    @Bean
//    @Primary
//    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        return container;
//    }
//
//}
