package com.zhengqing.demo.threadpool;

import com.zhengqing.common.core.constant.ThreadPoolConstant;
import com.zhengqing.common.feign.context.RequestHeaderHandler;
import com.zhengqing.system.feign.ISystemClient;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class SystemTaskThread {

    private ISystemClient systemClient;

    @SneakyThrows
    @Async(ThreadPoolConstant.SMALL_TOOLS_THREAD_POOL)
    public void getRequestHeaderUserId(Map<String, String> headerMap) {
        RequestHeaderHandler.setHeaderMap(headerMap);
        log.info("子线程请求头值: {}", this.systemClient.getRequestHeaderUserId());
    }

}
