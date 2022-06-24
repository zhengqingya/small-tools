package com.zhengqing.gateway.controller;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.config.PropertiesRouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 网关测试api
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/10/7 20:39
 */
@Slf4j
@RestController
@Api(tags = "网关测试api")
public class GatewayController {

    @Value("${server.port}")
    private String port;

    @Value("${spring.cloud.nacos.discovery.group}")
    private String group;

    @Resource
    private NacosServiceManager nacosServiceManager;

    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Resource
    private PropertiesRouteDefinitionLocator propertiesRouteDefinitionLocator;

    @SneakyThrows(Exception.class)
    @GetMapping("getAllInstancesForDemo")
    @ApiOperation("获取nacos注册实例信息 - demo服务")
    public List<Instance> getAllInstancesForDemo() {
        NamingService namingService = this.nacosServiceManager.getNamingService(this.nacosDiscoveryProperties.getNacosProperties());
        log.info("仅获取健康实例服务信息: {}", namingService.getAllInstances("demo", this.group));
        return namingService.getAllInstances("demo", this.group, false);
    }

    @GetMapping("/index")
    @ApiOperation("index")
    public String hello() {
        return "port:" + this.port;
    }

    @GetMapping("/hi")
    @ApiOperation("hi")
    public String hei() {
        return "this is gateway!";
    }

    @GetMapping("getGatewayRoute")
    @ApiOperation("getGatewayRoute")
    public Flux<RouteDefinition> getGatewayRoute() {
        return this.propertiesRouteDefinitionLocator.getRouteDefinitions();
    }

}
