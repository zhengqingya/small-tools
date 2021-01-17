package com.zhengqing.nacos.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhengqing.nacos.consumer.fallback.ProviderFallback;

@FeignClient(value = "nacos-provider", fallback = ProviderFallback.class)
public interface ProviderClient {

    @RequestMapping("/hello")
    public String hello();

}
