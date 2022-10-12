package com.zhengqing.demo.api;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.common.redis.constant.RedisConstant;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.demo.constant.DemoConstant;
import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.service.IDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
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
@Api(tags = "test-redis")
public class RedisController extends BaseController {

    @Resource
    private IDemoService demoService;


    @PostMapping("set")
    @ApiOperation("设置值")
    public String set() {
        Long result = RedisUtil.incrBy("test", 1);
        log.info("incrBy: {}", result);
        return "成功";
    }

    @PostMapping("reentrantLock")
    @ApiOperation("可重入锁")
    public void reentrantLock() {
        // 加锁
        RLock redisLock = RedisUtil.lock("test:lock", 5, TimeUnit.SECONDS);
        try {

            // 模拟扣减库存
            this.demoService.updateNum(1, -1);

            Demo demo = this.demoService.getById(1);
//            demo.setNum(demo.getNum() - 1);
//            ThreadUtil.sleep(1, TimeUnit.SECONDS);
//            demo.updateById();

            log.info("time:{} incrBy: {}", MyDateUtil.nowStr(), demo.getNum());
        } finally {
            // 释放锁
            redisLock.unlock();
        }
    }

    @GetMapping("getRandomCode")
    @ApiOperation("获取随机码")
    public List<String> getRandomCode() {
        List<String> codeList = Lists.newLinkedList();
        for (int i = 0; i < 10; i++) {
            codeList.add(IdGeneratorUtil.generateRandomCode());
        }
        return codeList;
    }

    @ApiOperation("发布订阅")
    @GetMapping("publishSubscribe")
    public String publishSubscribe() {
        RedisUtil.publish(RedisConstant.REDIS_CHANNEL_TEST, RandomUtil.randomString(10));
        RedisUtil.publish(DemoConstant.REDIS_CHANNEL_DEMO, RandomUtil.randomString(10));
        return "success";
    }

    @ApiOperation("秒杀")
    @GetMapping("seckill")
    public String seckill() {
        this.demoService.seckill();
        return "success";
    }

}

