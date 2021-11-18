package com.zhengqing.common.config.feign.ribbon.version;

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
import com.zhengqing.common.config.feign.ribbon.WeightedBalancer;
import com.zhengqing.common.enums.BalancerRuleTypeEnum;
import com.zhengqing.common.util.BalancerInstanceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 自定义负载均衡策略-同一集群优先带版本实例
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/11/8 17:46
 */
@Slf4j
public class BalancerVersionRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties discoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        try {
            // 1、获取当前服务的分组名称、集群名称、版本号
            String groupName = discoveryProperties.getGroup();
            String clusterName = discoveryProperties.getClusterName();
            String version = discoveryProperties.getMetadata().get("version");
            // 2、获取当前服务的负载均衡器
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            // 3、获取目标服务的服务名
            String serviceName = loadBalancer.getName();
            // 4、获取nacos提供的服务注册api
            NamingService namingService = discoveryProperties.namingServiceInstance();
            // 5、获取所有服务名为serviceName的服务实例
            List<Instance> allInstanceList = namingService.getAllInstances(serviceName, groupName);
            // 6、过滤有相同集群的服务实例
            List<Instance> sameClusterInstanceList = Lists.newLinkedList();
            for (Instance instance : allInstanceList) {
                if (instance.getClusterName().equals(clusterName)) {
                    sameClusterInstanceList.add(instance);
                }
            }
            // 7、过滤相同版本的服务实例
            List<Instance> sameVersionInstanceList = Lists.newLinkedList();
            for (Instance instance : sameClusterInstanceList) {
                if (instance.getMetadata().get("version").equals(version)) {
                    sameVersionInstanceList.add(instance);
                }
            }
            // 8、选择合适的服务实例
            Instance toBeChooseInstance;
            if (CollectionUtils.isEmpty(sameVersionInstanceList)) {
                toBeChooseInstance = WeightedBalancer.chooseInstanceByRandomWeight(sameClusterInstanceList);
                BalancerInstanceUtil.printInstance(BalancerRuleTypeEnum.VERSION_WEIGHT, toBeChooseInstance);
            } else {
                toBeChooseInstance = WeightedBalancer.chooseInstanceByRandomWeight(sameVersionInstanceList);
                BalancerInstanceUtil.printInstance(BalancerRuleTypeEnum.VERSION, toBeChooseInstance);
            }
            return new NacosServer(toBeChooseInstance);
        } catch (NacosException e) {
            log.error("Nacos同一集群带版本优先调用异常: ", e);
            return null;
        }
    }

}
