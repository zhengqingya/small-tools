package com.zhengqing.gateway.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 动态聚合各个服务的swagger接口
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
     * swagger3默认的url后缀
     * {@link com.zhengqing.common.swagger.config.Knife4jConfig}
     */
    private static final String SWAGGER_3_URL = "/v3/api-docs?group=";

    /**
     * nacos注册中心
     */
    @Resource
    private DiscoveryClient discoveryClient;

    /**
     * 网关应用名称
     */
    @Value("${spring.application.name}")
    private String gatewayServiceName;

    @Override
    public List<SwaggerResource> get() {
        // swagger下拉列表服务资源
        List<SwaggerResource> resourceList = new ArrayList<>();
        // 动态获取nacos注册中心上的服务实例名
        List<String> nacosServiceNameList = this.discoveryClient.getServices();
        for (String instance : nacosServiceNameList) {
            String instanceName = instance.toLowerCase();
            if (this.gatewayServiceName.equals(instanceName)) {
                continue;
            }
            // 拼接swagger访问url，样式为`/demo/v3/api-info?group=demo`，当网关调用这个接口时，会自动通过负载均衡寻找对应的主机
            String url = "/" + instanceName + SWAGGER_3_URL + instanceName;
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setUrl(url);
            swaggerResource.setName(instance);
            resourceList.add(swaggerResource);
        }
        return resourceList;
    }

}
