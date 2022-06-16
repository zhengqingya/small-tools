package com.zhengqing.demo.service.impl;

import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.service.IDemoService;
import com.zhengqing.demo.service.ITransactionalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;

@Slf4j
@Service
public class TransactionalServiceImpl implements ITransactionalService {

    @Resource
    private IDemoService demoService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void testTransactional01() {
        Demo demo = Demo.builder().username("test-01").password("123456").build();
        demo.insert();
        Long id = demo.getId();
        log.debug("主键id： 【{}】", id);

        log.debug("可能异常启动...");
        this.handleData01();
        log.debug("无异常...");
    }

    public void handleData01() {
        int a = 1 / 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testTransactional02() {
        Demo demo = Demo.builder().username("test-02").password("123456").build();
        demo.insert();
        Long id = demo.getId();
        log.debug("主键id： 【{}】", id);

        log.debug("可能异常启动...");
        try {
            this.handleData01();
        } catch (Exception e) {
            log.error(e.getMessage());
            // 手工回滚异常
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        log.debug("无异常...");
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testTransactional03() {
        this.handleData03();
        // 异步任务
        this.asyncExecute();
    }

    public void handleData03() {
        Demo demo = Demo.builder().username("test-03-simple-method").password("123456").build();
        demo.insert();
//        int a = 1 / 0;
    }


    @Async
    @Transactional(rollbackFor = Exception.class)
    public void asyncExecute() {
        Demo demo = Demo.builder().username("test-03-async-method-01").password("123456").build();
        demo.insert();
//        int a1 = 1 / 0;

        // 设置回滚点,只回滚以下异常
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        try {
            Demo demo2 = Demo.builder().username("test-03-async-method-02").password("123456").build();
            demo2.insert();
            int exception = 1 / 0;
        } catch (Exception e) {
            // 手工回滚异常,回滚到savePoint
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            log.error("异步执行任务失败: {}", e.getMessage());

            Demo demo3 = Demo.builder().username("test-03-async-method-03").password("123456").build();
            demo3.insert();
//            int a2 = 1 / 0;
            return;
        }
    }

    @Override
    public void testTransactional04() {
        this.demoService.asyncExecuteTransactional();
    }

    //    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testTransactional05() {
        Demo.builder().username("test-05-simple-method").password("123456").build().insert();
        // 异步任务
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                TransactionalServiceImpl.this.demoService.asyncExecute05();
            }
        });
        log.info("[testTransactional05] ...");
    }


}
