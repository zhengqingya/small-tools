package com.zhengqing.common.context;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> 租户ID上下文 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/30 9:24 下午
 */
@Slf4j
public class TenantIdContext {

    /**
     * 租户ID
     */
    public static final ThreadLocal<Long> TENANT_ID_THREAD_LOCAL = new ThreadLocal<>();
    /**
     * 租户ID是否排除标识
     * true : 是 -> 查询条件,不自动拼接租户ID
     * false: 否 -> 查询条件,自动拼接上租户ID
     */
    public static final ThreadLocal<Boolean> TENANT_ID_FLAG_THREAD_LOCAL = new ThreadLocal<>();

    public static void setTenantId(Long tenantId) {
        TENANT_ID_THREAD_LOCAL.set(tenantId);
        TENANT_ID_FLAG_THREAD_LOCAL.set(false);
    }

    public static Long getTenantId() {
        return TENANT_ID_THREAD_LOCAL.get();
    }

    public static void removeFlag() {
        TENANT_ID_FLAG_THREAD_LOCAL.set(true);
    }


    public static Boolean getFlag() {
        return TENANT_ID_FLAG_THREAD_LOCAL.get();
    }

    public static void remove() {
        TENANT_ID_THREAD_LOCAL.remove();
        TENANT_ID_FLAG_THREAD_LOCAL.remove();
    }

}
