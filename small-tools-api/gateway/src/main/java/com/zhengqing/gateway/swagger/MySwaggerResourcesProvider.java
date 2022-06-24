package com.zhengqing.gateway.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 聚合各个服务的swagger接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/12/19 18:06
 */
@Primary
@Component
public class MySwaggerResourcesProvider implements SwaggerResourcesProvider {

    /**
     * swagger2默认的url后缀
     */
    private static final String SWAGGER2URL = "/v2/api-docs";

    /**
     * 网关路由
     */
    @Resource
    private RouteLocator routeLocator;

    /**
     * 网关应用名称
     */
    @Value("${spring.application.name}")
    private String self;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routeHosts = new ArrayList<>();
        // 获取所有可用的host：serviceId
        this.routeLocator.getRoutes().filter(route -> route.getUri().getHost() != null)
                .filter(route -> !this.self.equals(route.getUri().getHost()))
                .subscribe(route -> routeHosts.add(route.getUri().getHost()));

        // 记录已经添加过的server，存在同一个应用注册了多个服务在nacos上
        Set<String> dealed = new HashSet<>();
        routeHosts.forEach(instance -> {
            String instanceName = instance.toLowerCase();
            // 拼接swagger访问url，样式为`/serviceId/v2/api-info?group=serviceId`，当网关调用这个接口时，会自动通过负载均衡寻找对应的主机
            String url = "/" + instanceName + SWAGGER2URL + "?group=" + instanceName;
            // 无group时 使用默认
            if (!dealed.contains(url)) {
                dealed.add(url);
                SwaggerResource swaggerResource = new SwaggerResource();
                swaggerResource.setUrl(url);
                swaggerResource.setName(instance);
                // 服务下拉列表读取映射
                resources.add(swaggerResource);
            }
        });
        return resources;
    }

}
