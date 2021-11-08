package com.zhengqing.common.config.ribbon;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.google.common.collect.Lists;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 同集群优先带版本的负载均衡策略
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/11/8 17:46
 */
@Slf4j
public class TheSameClusterPriorityWithVersionRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties discoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        try {
            // 第一步: 获取当前服务的集群名称 和 服务的版本号
            String clusterName = discoveryProperties.getClusterName();
            String version = discoveryProperties.getMetadata().get("version");
            // 第二步: 获取当前服务的负载均衡器
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            // 第三步: 获取目标服务的服务名
            String serviceName = loadBalancer.getName();
            // 第四步: 获取nacos提供的服务注册api
            NamingService namingService = discoveryProperties.namingServiceInstance();
            // 第五步: 获取所有服务名为serviceName的服务实例
            List<Instance> allInstances = namingService.getAllInstances(serviceName);
            // 第六步: 过滤相同版本的服务实例
            List<Instance> sameVersionInstance = new ArrayList<>();
            for (Instance instance : allInstances) {
                if (instance.getMetadata().get("version").equals(version)) {
                    sameVersionInstance.add(instance);
                }
            }
            // 第七步: 过滤有相同集群的服务实例
            List<Instance> sameClusterInstance = Lists.newLinkedList();
            for (Instance instance : sameVersionInstance) {
                if (instance.getClusterName().equals(clusterName)) {
                    sameClusterInstance.add(instance);
                }
            }
            // 第八步: 选择合适的服务实例
            Instance toBeChooseInstanc;
            if (CollectionUtils.isEmpty(sameClusterInstance)) {
                toBeChooseInstanc = WeightedBalancer.chooseInstanceByRandomWeight(sameVersionInstance);
                log.warn("跨集群调用, serviceName = {}, clusterName = {}, instances = {}",
                        serviceName,
                        clusterName,
                        toBeChooseInstanc
                );
            } else {
                toBeChooseInstanc = WeightedBalancer.chooseInstanceByRandomWeight(sameClusterInstance);
                log.warn("同集群调用 serviceName = {}, clusterName = {}, instances = {}",
                        serviceName,
                        clusterName,
                        toBeChooseInstanc
                );
            }
            return new NacosServer(toBeChooseInstanc);
        } catch (NacosException e) {
            log.error("Nacos同一集群优先调用异常: ", e);
            return null;
        }
    }

}
