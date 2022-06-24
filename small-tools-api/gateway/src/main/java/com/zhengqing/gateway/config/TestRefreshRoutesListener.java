//package com.zhengqing.gateway.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.cloud.gateway.config.GatewayProperties;
//import org.springframework.cloud.gateway.route.RouteDefinition;
//import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.net.URI;
//
///**
// * <p> 自定义监听器 </p>
// *
// * @author zhengqingya
// * @description
// * @date 2021/11/19 14:49
// */
//@Slf4j
//@Component
//public class TestRefreshRoutesListener implements ApplicationListener<ApplicationReadyEvent> {
//
//    @Resource
//    private SpringClientFactory springClientFactory;
//
//    @Resource
//    private GatewayProperties gatewayProperties;
//
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent event) {
//        try {
//            for (RouteDefinition route : this.gatewayProperties.getRoutes()) {
//                URI uri = route.getUri();
//                String id = route.getId();
//                if (uri.toString().startsWith("lb")) {
//                    Method getContext = SpringClientFactory.class.getDeclaredMethod("getContext", String.class);
//                    getContext.setAccessible(true);
//                    getContext.invoke(this.springClientFactory, id);
//                    log.info("获取路由: {}", id);
//                }
//            }
//        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
