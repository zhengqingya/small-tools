package com.zhengqing.gateway.config;

import com.zhengqing.common.constant.AppConstant;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * <p>
 * 网关常用变量
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/9 18:32
 */
public interface GatewayConstant extends AppConstant {

    /**
     * rpc服务名
     */
    List<String> RPC_SERVICE_NAME_LIST = Lists.newArrayList(
            "demo",
            "system",
            "tool"
    );

}
