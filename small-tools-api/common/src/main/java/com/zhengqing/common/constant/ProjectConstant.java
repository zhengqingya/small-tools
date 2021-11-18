package com.zhengqing.common.constant;

import org.assertj.core.util.Lists;

import java.util.List;

/**
 * <p> 全局常用变量 - 工程使用 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/7/20 18:16
 */
public interface ProjectConstant {

    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ 文件系列 ↓↓↓↓↓↓ ============================
    // ===============================================================================

    /**
     * 系统分隔符
     */
    String SYSTEM_SEPARATOR = "/";

    /**
     * 获取项目根目录
     */
    String PROJECT_ROOT_DIRECTORY = System.getProperty("user.dir").replaceAll("\\\\", SYSTEM_SEPARATOR);

    /**
     * 临时文件相关
     */
    String DEFAULT_FOLDER_TMP = PROJECT_ROOT_DIRECTORY + "/tmp";
    String DEFAULT_FOLDER_TMP_GENERATE = PROJECT_ROOT_DIRECTORY + "/tmp-generate";

    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ service ↓↓↓↓↓↓ ============================
    // ===============================================================================

    String SERVICE_BASE_PACKAGE = "com.zhengqing";
    /**
     * api前缀
     */
    String SERVICE_API_PREFIX_WEB = "/web/api";
    String SERVICE_API_PREFIX_WEB_SYSTEM = SERVICE_API_PREFIX_WEB + "/system";

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
