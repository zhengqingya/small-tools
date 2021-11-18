package com.zhengqing.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 自定义负载均衡策略-规则类型枚举类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/13 18:46
 */
@Getter
@AllArgsConstructor
public enum BalancerRuleTypeEnum {

    WEIGHT("weight", "权重"),
    VERSION("version", "同一集群使用相同版本"),
    VERSION_WEIGHT("version_weight", "同一集群无相同版本，使用权重"),
    ;

    private final String type;
    private final String desc;

}
