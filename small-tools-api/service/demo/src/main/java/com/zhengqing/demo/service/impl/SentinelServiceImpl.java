package com.zhengqing.demo.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.zhengqing.common.util.IdGeneratorUtil;
import com.zhengqing.demo.service.ISentinelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> sentinel 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/5/19 10:21
 */
@Slf4j
@Service
public class SentinelServiceImpl implements ISentinelService {

    @Resource
    private IdGeneratorUtil idGeneratorUtil;

    @Override
    // 此注解可放在方法上，针对方法进行流控
    @SentinelResource(value = "common")
    public void common() {
        log.info("[common]：{}", DateUtil.now());
    }

}
