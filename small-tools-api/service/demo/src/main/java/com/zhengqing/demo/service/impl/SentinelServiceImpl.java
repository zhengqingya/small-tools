//package com.zhengqing.demo.service.impl;
//
//import cn.hutool.core.date.DateUtil;
//import com.alibaba.csp.sentinel.annotation.SentinelResource;
//import com.alibaba.csp.sentinel.slots.block.BlockException;
//import com.zhengqing.demo.service.ISentinelService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
///**
// * <p> sentinel 服务实现类 </p>
// *
// * @author zhengqingya
// * @description
// * @date 2022/5/19 10:21
// */
//@Slf4j
//@Service
//public class SentinelServiceImpl implements ISentinelService {
//
//
//    @Override
//    // 此注解可放在方法上，针对方法进行流控，声明此注解之后，不会走sentinel统一异常处理，需要自定义
//    @SentinelResource(value = "common", blockHandler = "blockHandlerForCommon")
//    public void common() {
//        log.info("[common]：{}", DateUtil.now());
//    }
//
//    public void blockHandlerForCommon(BlockException ex) {
//        log.info("[blockHandlerForCommon]：{}", DateUtil.now());
//    }
//
//}
