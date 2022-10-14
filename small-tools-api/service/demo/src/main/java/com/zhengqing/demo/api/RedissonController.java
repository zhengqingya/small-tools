package com.zhengqing.demo.api;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.demo.service.IRedissonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final IRedissonService redissonService;

    @SneakyThrows(Exception.class)
    @PostMapping("reentrantLock")
    @ApiOperation("可重入锁")
    public void reentrantLock() {

    }

    @ApiOperation("秒杀")
    @GetMapping("seckill")
    public String seckill() {
        this.redissonService.seckill();
        return "success";
    }

}

