package com.zhengqing.system.constant;

/**
 * <p>
 * 全局常用变量 - system
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/15 14:38
 */
public interface SystemConstant {

    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ redis缓存系列 ↓↓↓↓↓↓ ============================
    // ===============================================================================

    /**
     * 数据字典缓存
     */
    String CACHE_SYS_DICT_PREFIX = "SYSTEM:DICT:";
    /**
     * 系统属性缓存
     */
    String CACHE_SYS_PROPERTY_PREFIX = "SYSTEM:PROPERTY:";
    /**
     * 系统缓存
     */
    String CACHE_SYS_MENU_TREE = "SYS_MENU_TREE";
    /**
     * 个人缓存
     */
    String CACHE_SYS_USER_INFO_PREFIX = "SYS_USER_INFO_";
    String CACHE_SYS_PERMISSION_PREFIX = "SYS_PERMISSION_";


}
