package com.zhengqing.demo.api;

import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.mq.util.MqUtil;
import com.zhengqing.demo.constant.DemoRabbitMqConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 测试 - mq
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/01/13 10:11
 */
@Slf4j
@RestController
@RequestMapping("/test/mq")
@Api(tags = {"mq"})
@RequiredArgsConstructor
public class MqController {

    private final RabbitTemplate rabbitTemplate;

    @PostMapping("sendMsg")
    @ApiOperation("sendMsg")
    public void sendMsg() {
        // 普通队列
        MqUtil.send(DemoRabbitMqConstant.EXCHANGE, DemoRabbitMqConstant.TEST_ROUTING_KEY, "hello");

        // 延时队列 -- 死信队列
//        this.rabbitTemplate.convertAndSend("test.exchange", "test.create.routing.key", "delay...");
        this.rabbitTemplate.convertAndSend("test.exchange",
                "test.create.routing.key",
                "delay..." + MyDateUtil.nowStr(),
                // 单条消息设置过期时间，如果队列中也设置了过期时间，以两者的最小过期时间计算  即这里的ttl以nacos中的5秒为准  在5秒内此消息未被消费成功则自动删除
                message -> {
                    message.getMessageProperties().setExpiration("10000");
                    return message;
                });

        // 延时队列 -- 插件方式
        MqUtil.sendDelay(
                DemoRabbitMqConstant.DELAY_EXCHANGE,
                DemoRabbitMqConstant.TEST_DELAY_ROUTING_KEY,
                MyDateUtil.nowStr(),
                1000 * 5
        );
    }

    @RabbitHandler
    @RabbitListener(queues = {DemoRabbitMqConstant.TEST_QUEUE})
    public void receiveMsg(String msg) {
        log.info("[MQ消费者] 普通队列 接收消息: {}", msg);
    }

    @RabbitHandler
    @RabbitListener(queues = {DemoRabbitMqConstant.TEST_DELAY_QUEUE})
    public void receiveDelayMsg(String msg) {
        log.info("[MQ消费者] 延时队列 接收消息: {}", msg);
    }

//    @SneakyThrows
//    @RabbitHandler
//    @RabbitListener(queues = {"test.delay.queue"})
//    public void delay(String msg) {
//        log.info("[test.delay.queue] 接收消息: {}", msg);
//        ThreadUtil.sleep(1, TimeUnit.SECONDS);
////        throw new Exception("xxx");
//    }

    @RabbitHandler
    @RabbitListener(queues = {"test.close.queue"})
    public void close(String msg) {
        log.info("[test.close.queue] 接收消息: {}", msg);
    }

}
