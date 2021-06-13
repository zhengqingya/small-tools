package com.zhengqing.demo.service.impl;

import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.service.ITransactionalInvalidService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TransactionalInvalidServiceImpl implements ITransactionalInvalidService {

    @Override
    public void testTransactionalInvalid01() {
        Demo demo = Demo.builder().username("admin").password("123456").build();
        demo.insert();
        Integer id = demo.getId();
        log.debug("主键id： 【{}】", id);

        log.debug("可能异常启动...");
        this.handleData01();
        log.debug("无异常...");
    }

    @Transactional
    public void handleData01() {
        int a = 1 / 0;
    }

    @Override
    public void testTransactionalInvalid02() {
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
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public void testTransactionalInvalid03() {

    }

    @Override
    @Transactional
    public void testTransactionalInvalid04() {

    }

}
