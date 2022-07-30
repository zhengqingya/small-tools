package com.zhengqing.demo.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zhengqing.common.db.mapper.MyBaseMapper;
import com.zhengqing.demo.service.IDemoService;
import com.zhengqing.demo.service.ISeataService;
import com.zhengqing.system.feign.ISystemClient;
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

    @Resource
    private ISystemClient systemClient;

    @Resource
    private MyBaseMapper myBaseMapper;

    @Override
    @GlobalTransactional
    public void test() {
        // 1、rpc调用其它服务
        String result = this.systemClient.test();
        System.out.println(result);
        // 2、自己服务
        this.addData();
    }

    @Transactional(rollbackFor = Exception.class)
    public void addData() {
        this.myBaseMapper.execSql("UPDATE t_demo SET username = '" + RandomUtil.randomString(5) + "' WHERE id = 2;");
        int i = 1 / 0;
    }

}
