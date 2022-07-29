package com.zhengqing.demo.service.impl;

import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.service.IDemoService;
import com.zhengqing.demo.service.ISeataService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p> seata 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/5/19 10:21
 */
@Slf4j
@Service
public class SeataServiceImpl implements ISeataService {

    @Resource
    private IDemoService demoService;

    @Override
    @GlobalTransactional
    public void test(Demo demo) {
        // 1、rpc调用其它服务
        
        // 2、自己服务
        this.addData(demo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addData(Demo demo) {
        this.demoService.saveOrUpdate(demo);
        int i = 1 / 0;
    }

}
