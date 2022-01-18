package com.zhengqing.demo.api;

import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.util.MyDateUtil;
import com.zhengqing.common.util.RedisUtil;
import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.service.IDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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


}

