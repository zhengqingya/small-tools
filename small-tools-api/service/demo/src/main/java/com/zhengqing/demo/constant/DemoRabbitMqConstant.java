package com.zhengqing.demo.constant;

import com.zhengqing.common.mq.constant.BaseRabbitMqConstant;

/**
 * <p> mq常量 - demo </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/15 13:48
 */
public interface DemoRabbitMqConstant extends BaseRabbitMqConstant {

    /**
     * 交换机
     */
    String EXCHANGE = "demo.exchange";
    String DELAY_EXCHANGE = "demo.delay.exchange";

    /**
     * 测试普通队列
     */
    String TEST_ROUTING_KEY = "demo.test.routing.key";
    String TEST_QUEUE = "demo.test.queue";

    /**
     * 测试延时队列
     */
    String TEST_DELAY_ROUTING_KEY = "demo.test_delay.routing.key";
    String TEST_DELAY_QUEUE = "demo.test_delay.queue";

}
