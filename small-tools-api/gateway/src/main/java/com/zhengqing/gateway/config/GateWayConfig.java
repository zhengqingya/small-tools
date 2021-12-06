package com.zhengqing.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <p> Gateway配置动态路由 </p>
 *
 * @author zhengqingya
 * @description 可参考 https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.1.RELEASE/reference/html/#route-metadata-configuration
 * @date 2021/12/6 7:11 下午
 */
@Configuration
public class GateWayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        for (String serviceName : GatewayConstant.RPC_SERVICE_NAME_LIST) {
            // 配置路由规则 ex: 当访问地址"http://127.0.0.1:1218/web/api/demo/demo/detail"时会自动转发到地址"http://127.0.0.1:1218/demo/web/api/demo/demo/detail"
            routes.route(serviceName, r -> r
                    .path(String.format("/web/api/%s/**", serviceName))
                    .uri(String.format("lb://%s", serviceName))).build();
        }
        return routes.build();
    }

}
