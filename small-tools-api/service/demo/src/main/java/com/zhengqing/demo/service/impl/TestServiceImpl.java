package com.zhengqing.demo.service.impl;

import com.zhengqing.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestServiceImpl implements TestService {


    /**
     * 测试
     *
     * @return void
     * @author zhengqingya
     * @date 2022/8/30
     */
    public void test() {

        System.out.println(666);

        
    }


    public void test1() {
        System.out.println(666);
    }


    public void test2() {
        System.out.println(666);
    }


}
