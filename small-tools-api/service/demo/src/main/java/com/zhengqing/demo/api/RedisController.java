package com.zhengqing.demo.api;

import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * <p> 测试redis </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/29 17:46
 */
@Slf4j
@RestController
@RequestMapping("/web/api/demo/redis")
@Api(tags = "测试redis")
public class RedisController extends BaseController {


    @GetMapping("set")
    @ApiOperation("设置值")
    public void set() {
        Long test = RedisUtil.incrBy("test", 1);
        log.info("incrBy: {}", test);
    }

    @GetMapping("reentrantLock")
    @ApiOperation("可重入锁")
    public void reentrantLock() {
        // https://www.bookstack.cn/read/redisson-wiki-zh/spilt.1.8.-%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81%E5%92%8C%E5%90%8C%E6%AD%A5%E5%99%A8.md
        RLock redisLock = RedisUtil.getLock("test:lock");
        // 加锁以后5秒钟自动解锁
        // 无需调用unlock方法手动解锁
        redisLock.lock(5, TimeUnit.SECONDS);
        try {
            Long test = RedisUtil.incrBy("test", 1);
            log.info("incrBy: {}", test);
        } finally {
            // 释放锁
            redisLock.unlock();
        }
    }


}
