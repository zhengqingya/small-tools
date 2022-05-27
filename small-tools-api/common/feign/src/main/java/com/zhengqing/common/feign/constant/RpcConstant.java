package com.zhengqing.common.feign.constant;

import org.assertj.core.util.Lists;

import java.util.List;

/**
 * <p> RPC常用变量 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/7/20 18:16
 */
public interface RpcConstant {

    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ rpc ↓↓↓↓↓↓ ================================
    // ===============================================================================

    String RPC_BASE_PACKAGE = "com.zhengqing";

    /**
     * 服务
     */
    String RPC_DEMO = "demo";
    String RPC_SYSTEM = "system";
    String RPC_TOOL = "tool";
    List<String> ALL_RPC_SERVICE_NAME_LIST = Lists.newArrayList(
            RPC_DEMO, RPC_SYSTEM, RPC_TOOL
    );

    /**
     * rpc-api前缀
     */
    String RPC_API_PREFIX_WEB = "/rpc/client/web";
    String RPC_API_PREFIX_WEB_SYSTEM = RPC_API_PREFIX_WEB + "/system";


    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ other ↓↓↓↓↓↓ ==============================
    // ===============================================================================


}
