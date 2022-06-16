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
    String CACHE_SYS_DICT_PREFIX = "small-tools:system:dict:";
    /**
     * 系统属性缓存
     */
    String CACHE_SYS_PROPERTY_PREFIX = "small-tools:system:property:";
    /**
     * 系统缓存
     */
    String CACHE_SYS_MENU_TREE = "small-tools:system:sys_menu_tree";
    /**
     * 个人缓存
     */
    String CACHE_SYS_USER_INFO_PREFIX = "small-tools:system:sys_user_info_";
    String CACHE_SYS_PERMISSION_PREFIX = "small-tools:system:sys_permission_";


}
