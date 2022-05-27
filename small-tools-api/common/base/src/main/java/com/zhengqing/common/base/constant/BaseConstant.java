package com.zhengqing.common.base.constant;

import com.zhengqing.common.swagger.config.swagger.SwaggerCommonConfig;

/**
 * <p>
 * 全局常用变量 - base
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/10/12 14:47
 */
public interface BaseConstant {

    /**
     * 用户ID、用户名、消息上下文Key 【注：key名称一定不要和前端请求参数中的属性名一样，否则会拿不到真正的值！！！】
     */
    String CONTEXT_KEY_USER_ID = "small_tools_user_id";
    String CONTEXT_KEY_USERNAME = "small_tools_username";

    Integer DEFAULT_CONTEXT_KEY_USER_ID = 0;
    String DEFAULT_CONTEXT_KEY_USERNAME = "未知";

    /**
     * 请求头 - token
     * 修改值时需修改 {@link SwaggerCommonConfig#securitySchemes()}
     */
    String REQUEST_HEADER_TOKEN = "ZQ-X-TOKEN";

}
