package com.zhengqing.demo.api;

import cn.hutool.json.JSONUtil;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.service.IDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * <p> 测试redis问题 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/29 17:46
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "test-redis-cache")
@RequestMapping("/web/api/test/redis")
public class RedisCacheProblemController extends BaseController {

    private final IDemoService demoService;

    @GetMapping("test")
    @ApiOperation("测试")
    public String test() {
        // 逻辑过期
        RedisUtil.setWithLogicalExpire("test", JSONUtil.toJsonStr(Demo.builder().id(1L).build()), 5L, TimeUnit.SECONDS);
        // 缓存穿透
        Demo test1 = RedisUtil.queryWithPassThrough("test1", 1L, Demo.class, this.demoService::getById, 5L, TimeUnit.SECONDS);
        // 缓存击穿 -- 逻辑过期
        Demo test2 = RedisUtil.queryWithLogicalExpire("test2", 1L, Demo.class, this.demoService::getById, 5L, TimeUnit.SECONDS, "lock1");
        // 缓存击穿 -- 逻辑过期
        Demo test3 = RedisUtil.queryWithMutex("test3", 1L, Demo.class, this.demoService::getById, 5L, TimeUnit.SECONDS, "lock2");
        return "成功";
    }


}

