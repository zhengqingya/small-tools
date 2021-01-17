package com.zhengqing.nacos.consumer.fallback;

import org.springframework.stereotype.Component;

import com.zhengqing.nacos.consumer.feign.ProviderClient;

@Component
public class ProviderFallback implements ProviderClient {

    @Override
    public String hello() {
        return "现在服务器忙，请稍后再试！";
    }

}
