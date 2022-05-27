package com.zhengqing.demo.api;

import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.lock.RedisLock;
import com.zhengqing.common.core.custom.lock.RedisLockType;
import com.zhengqing.demo.model.dto.DemoListDTO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 测试redisson分布式锁 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/29 17:46
 */
@Slf4j
@RestController
@RequestMapping("/web/api/demo/redisson")
@Api(tags = "redisson-lock-test")
public class RedissonLockController extends BaseController {

    @PostMapping("reentrantLock")
    @RedisLock(key = "'reentrantLock'", lockType = RedisLockType.REENTRANT_LOCK)
    public void reentrantLock(String key) {
        log.info("lock");
    }

    @PostMapping("fairLock")
    @RedisLock(key = "'test' + ':' + #params.id", lockType = RedisLockType.FAIR_LOCK)
    public void fairLock(DemoListDTO params) {
        log.info("lock");
    }

    @PostMapping("readLock")
    @RedisLock(key = "#params.id", lockType = RedisLockType.READ_LOCK)
    public void readLock(DemoListDTO params) {
        log.info("lock");
    }

    @PostMapping("writeLock")
    @RedisLock(key = "#params.id", lockType = RedisLockType.WRITE_LOCK)
    public void writeLock(DemoListDTO params) {
        log.info("lock");
    }

}

