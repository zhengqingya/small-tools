package com.zhengqing.common.feign.config.loadbalancer.balancer;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.google.common.collect.Lists;
import com.zhengqing.common.feign.config.loadbalancer.WeightedBalancer;
import com.zhengqing.common.feign.enums.BalancerRuleTypeEnum;
import com.zhengqing.common.feign.util.BalancerInstanceUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * <p> 自定义负载均衡策略 </p>
 *
 * @author zhengqingya
 * @description {@link  org.springframework.cloud.loadbalancer.core.RandomLoadBalancer}
 * @date 2022/7/25 17:04
 */
@Slf4j
public class CustomInstanceLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    /**
     * nacos服务配置信息
     */
    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    @Resource
    private NacosServiceManager nacosServiceManager;

    /**
     * loadbalancer 提供的访问目标服务的名称
     */
    final String serviceName;

    /**
     * loadbalancer 提供访问的服务列表
     */
    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    /**
     * 负载均衡策略
     * {@link com.zhengqing.common.feign.enums.BalancerRuleTypeEnum }
     */
    @Value("${feign.rule-type:}")
    private String ruleType;

    public CustomInstanceLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceName) {
        this.serviceName = serviceName;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = this.serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next().map((serviceInstances) -> this.processInstanceResponse(supplier, serviceInstances));
    }

    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier, List<ServiceInstance> serviceInstances) {
        Response<ServiceInstance> serviceInstanceResponse = this.getInstanceResponse(serviceInstances);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    /**
     * 对负载均衡的服务进行筛选
     *
     * @param instances loadbalancer可访问目标服务的实例 (只会提供健康的实例，无需担心无法访问的情况)
     * @return 指定实例
     * @author zhengqingya
     * @date 2022/7/25 17:43
     */
    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
        if (instances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No servers available for service: " + this.serviceName);
            }
            return new EmptyResponse();
        }

        BalancerRuleTypeEnum balancerRuleTypeEnum = BalancerRuleTypeEnum.getEnum(this.ruleType);
        log.info("使用自定义负载均衡策略:[{}]", balancerRuleTypeEnum.getDesc());
        switch (balancerRuleTypeEnum) {
            case VERSION:
                // 同一集群优先带版本实例
                ServiceInstance instanceForVersionRule = this.getInstanceForVersionRule(instances);
                return new DefaultResponse(instanceForVersionRule);
            default:
                // 默认权重
                int index = ThreadLocalRandom.current().nextInt(instances.size());
                ServiceInstance instance = instances.get(index);
                return new DefaultResponse(instance);
        }
    }

    /**
     * 同一集群优先带版本实例
     *
     * @param loadbalancerInstanceList loadbalancer可访问目标服务的实例 (只会提供健康的实例，无需担心无法访问的情况)
     * @return 指定实例
     * @author zhengqingya
     * @date 2022/7/25 17:43
     */
    @SneakyThrows(Exception.class)
    private ServiceInstance getInstanceForVersionRule(List<ServiceInstance> loadbalancerInstanceList) {
        // 1、获取当前服务的分组名称、集群名称、版本号
        String groupName = this.nacosDiscoveryProperties.getGroup();
        String clusterName = this.nacosDiscoveryProperties.getClusterName();
        String version = this.nacosDiscoveryProperties.getMetadata().get("version");
        // 2、获取nacos提供的服务注册api
        NamingService namingService = this.nacosServiceManager.getNamingService(this.nacosDiscoveryProperties.getNacosProperties());
        // 3、获取所有服务名为serviceName的服务实例   false: 及时获取nacos注册服务信息
        List<Instance> nacosAllInstanceList = namingService.getAllInstances(this.serviceName, groupName, false);
        // 4、过滤有相同集群的服务实例
        List<Instance> nacosSameClusterInstanceList = Lists.newLinkedList();
        for (Instance instance : nacosAllInstanceList) {
            if (instance.getClusterName().equals(clusterName)) {
                nacosSameClusterInstanceList.add(instance);
            }
        }
        // 5、过滤相同版本的服务实例
        List<Instance> nacosSameVersionInstanceList = Lists.newLinkedList();
        for (Instance instance : nacosSameClusterInstanceList) {
            if (version.equals(instance.getMetadata().get("version"))) {
                nacosSameVersionInstanceList.add(instance);
            }
        }
        // 6、选择合适的服务实例
        Instance nacosToBeChooseInstance;
        if (CollectionUtils.isEmpty(nacosSameVersionInstanceList)) {
            nacosToBeChooseInstance = WeightedBalancer.chooseInstanceByRandomWeight(nacosSameClusterInstanceList);
            BalancerInstanceUtil.printInstance(BalancerRuleTypeEnum.VERSION_WEIGHT, nacosToBeChooseInstance);
        } else {
            nacosToBeChooseInstance = WeightedBalancer.chooseInstanceByRandomWeight(nacosSameVersionInstanceList);
            BalancerInstanceUtil.printInstance(BalancerRuleTypeEnum.VERSION, nacosToBeChooseInstance);
        }
        // 7、返回具体实例
        Map<String, ServiceInstance> loadbalancerInstanceMap = loadbalancerInstanceList.stream()
                .collect(Collectors.toMap(ServiceInstance::getInstanceId, t -> t, (k1, k2) -> k1));
        return loadbalancerInstanceMap.get(nacosToBeChooseInstance.getInstanceId());
    }

}

