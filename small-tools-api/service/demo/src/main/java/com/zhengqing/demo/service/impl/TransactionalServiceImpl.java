package com.zhengqing.demo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.service.ITransactionalService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionalServiceImpl implements ITransactionalService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void testTransactional01() {
        Demo demo = Demo.builder().username("admin").password("123456").build();
        demo.insert();
        Integer id = demo.getId();
        log.debug("主键id： 【{}】", id);

        log.debug("可能异常启动...");
        this.handleData01();
        log.debug("无异常...");
    }

    public void handleData01() {
        int a = 1 / 0;
    }

    @Override
    public void testTransactional02() {
        Demo demo = Demo.builder().username("admin").password("123456").build();
        demo.insert();
        Integer id = demo.getId();
        log.debug("主键id： 【{}】", id);

        log.debug("可能异常启动...");
        try {
            this.handleData01();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("无异常...");
    }

    @Override
    public void testTransactional03() {

    }

}
