package com.zhengqing.common.mq.config;

import com.zhengqing.common.mq.dynamic.RabbitModuleInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> RabbitMQ配置类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/7/8 10:32
 */
@Slf4j
@Configuration
public class RabbitMqConfig {

    /**
     * 消息序列化配置
     */
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    /**
     * 动态创建队列、交换机初始化器
     */
    @Bean
    @ConditionalOnMissingBean
    public RabbitModuleInitializer rabbitModuleInitializer(AmqpAdmin amqpAdmin, RabbitModuleProperty rabbitModuleProperty) {
        return new RabbitModuleInitializer(amqpAdmin, rabbitModuleProperty);
    }

}
