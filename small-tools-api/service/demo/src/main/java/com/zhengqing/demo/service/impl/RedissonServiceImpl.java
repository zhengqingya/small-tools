package com.zhengqing.demo.service.impl;

import com.zhengqing.common.base.context.SysUserContext;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.service.IDemoService;
import com.zhengqing.demo.service.IRedissonService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p> Redisson 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/5/19 10:21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RedissonServiceImpl implements IRedissonService {

    private final IDemoService demoService;
    private final RedissonClient redissonClient;

    @Override
    @GlobalTransactional
    @SneakyThrows(Exception.class)
    public void seckill() {
        Integer userId = SysUserContext.getUserId();
        String key = "test_lock_" + userId;
        // 加锁
        RLock lock = this.redissonClient.getLock(key);
//        lock.lock(5, TimeUnit.SECONDS);
        // 尝试加锁，最多等待100秒，上锁以后50秒自动解锁
        boolean isLock = lock.tryLock(100, 50, TimeUnit.SECONDS);
        if (!isLock) {
//            return;
        }
        try {
            // 模拟扣减库存
            this.demoService.updateNum(1, -1);
            this.handleData(key);
            Demo demo = this.demoService.getById(1);
            log.info("time:{} incrBy: {}", MyDateUtil.nowStr(), demo.getNum());
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    @SneakyThrows(Exception.class)
    public void handleData(String key) {
        RLock lock = this.redissonClient.getLock(key);
        boolean isLock = lock.tryLock(100, 50, TimeUnit.SECONDS);
        if (!isLock) {
            return;
        }
        System.out.println(1);
        lock.unlock();
    }


}
