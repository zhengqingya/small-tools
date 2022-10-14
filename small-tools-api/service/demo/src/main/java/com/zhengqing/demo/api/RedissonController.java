package com.zhengqing.demo.api;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.redis.util.RedissonUtil;
import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.service.IDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_DEMO + "/redisson")
@Api(tags = "test-redisson")
public class RedissonController extends BaseController {

    private final IDemoService demoService;

    @PostMapping("reentrantLock")
    @ApiOperation("可重入锁")
    public void reentrantLock() {
        // 加锁
        RLock redisLock = RedissonUtil.lock("test:lock", 5, TimeUnit.SECONDS);
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

