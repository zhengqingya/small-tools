package com.zhengqing.demo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * <p> 发布订阅 -- 测试消费者 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/6/17 17:38
 */
@Slf4j
public class MyTestListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        log.info("redis收到订阅消息：{}", msg);
    }

}