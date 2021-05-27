package com.zhengqing.demo.api;

import com.zhengqing.demo.threadpool.AsyncTaskThread;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("")
public class ThreadPoolController {

    @Autowired
    private AsyncTaskThread asyncTaskThread;

    @SneakyThrows
    @GetMapping("async/task")
    public List<Integer> asyncTask() {
        log.info("执行任务前...");
        long currentTimeMillisBefore = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        Future<Integer> integerFuture1 = asyncTaskThread.task1();
        Future<Integer> integerFuture2 = asyncTaskThread.task2();
        Future<Integer> integerFuture3 = asyncTaskThread.task3();
        asyncTaskThread.task4();
        asyncTaskThread.task5();
        //获取异步任务的处理结果，异步任务没有处理完成，会一直阻塞，可以设置超时时间，使用 get 的重载方法
        list.add(integerFuture1.get());
        list.add(integerFuture2.get());
        list.add(integerFuture3.get());
        long currentTimeMillisAfter = System.currentTimeMillis();
        log.info("task任务总耗时:" + (currentTimeMillisAfter - currentTimeMillisBefore) + "ms");
        log.info("执行任务后...");
        return list;
    }

}
