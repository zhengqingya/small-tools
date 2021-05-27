package com.zhengqing.demo.threadpool;

import com.zhengqing.common.constant.ThreadPoolConstant;
import com.zhengqing.demo.entity.Demo;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class AsyncTaskThread {

    @Async(ThreadPoolConstant.SMALL_TOOLS_THREAD_POOL)
    @Transactional(rollbackFor = Exception.class)
    @SneakyThrows
    public Future<Integer> task1() {
        log.info("task1任务开始...");
        long currentTimeMillisBefore = System.currentTimeMillis();
        Demo.builder().username("test01").password("123456").build().insert();
        int a = 1 / 0;
        TimeUnit.SECONDS.sleep(1);
        long currentTimeMillisAfter = System.currentTimeMillis();
        log.info("task1任务当前线程： " + Thread.currentThread());
        log.info("task1任务耗时:" + (currentTimeMillisAfter - currentTimeMillisBefore) + "ms");
        return new AsyncResult<>(1);
    }

    @Async
    @SneakyThrows
    public Future<Integer> task2() {
        log.info("task2任务开始...");
        long currentTimeMillisBefore = System.currentTimeMillis();
        int a = 1 / 0;
        TimeUnit.SECONDS.sleep(2);
        long currentTimeMillisAfter = System.currentTimeMillis();
        log.info("task2任务当前线程： " + Thread.currentThread());
        log.info("task2任务耗时:" + (currentTimeMillisAfter - currentTimeMillisBefore) + "ms");
        return new AsyncResult<>(2);
    }

    @Async
    @SneakyThrows
    public Future<Integer> task3() {
        log.info("task3任务开始...");
        long currentTimeMillisBefore = System.currentTimeMillis();
        TimeUnit.SECONDS.sleep(3);
        long currentTimeMillisAfter = System.currentTimeMillis();
        log.info("task3任务当前线程： " + Thread.currentThread());
        log.info("task3任务耗时:" + (currentTimeMillisAfter - currentTimeMillisBefore) + "ms");
        return new AsyncResult<>(3);
    }

    @Async
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public void task4() {
        log.info("task4任务开始...");
        long currentTimeMillisBefore = System.currentTimeMillis();
        Demo.builder().username("test").password("123456").build().insert();
        int a = 1 / 0;
        TimeUnit.SECONDS.sleep(1);
        long currentTimeMillisAfter = System.currentTimeMillis();
        log.info("task4任务当前线程： " + Thread.currentThread());
        log.info("task4任务耗时:" + (currentTimeMillisAfter - currentTimeMillisBefore) + "ms");
    }


    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public void task5() {
        log.info("task1任务开始...");
        long currentTimeMillisBefore = System.currentTimeMillis();
        this.saveData();
        int a = 1 / 0;
        TimeUnit.SECONDS.sleep(1);
        long currentTimeMillisAfter = System.currentTimeMillis();
        log.info("task1任务当前线程： " + Thread.currentThread());
        log.info("task1任务耗时:" + (currentTimeMillisAfter - currentTimeMillisBefore) + "ms");
    }

    @Async(ThreadPoolConstant.SMALL_TOOLS_THREAD_POOL)
    public void saveData() {
        Demo.builder().username("test05").password("123456").build().insert();
    }

}
