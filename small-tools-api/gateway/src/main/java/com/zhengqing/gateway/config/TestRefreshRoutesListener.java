package com.zhengqing.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;

@Slf4j
@Component
public class TestRefreshRoutesListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private SpringClientFactory springClientFactory;

    @Autowired
    private GatewayProperties gatewayProperties;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            for (RouteDefinition route : gatewayProperties.getRoutes()) {
                URI uri = route.getUri();
                if (uri.toString().startsWith("lb")) {
                    Method getContext = SpringClientFactory.class.getDeclaredMethod("getContext", String.class);
                    getContext.setAccessible(true);
                    getContext.invoke(this.springClientFactory, route.getId());
                    log.info("执行操作");
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
