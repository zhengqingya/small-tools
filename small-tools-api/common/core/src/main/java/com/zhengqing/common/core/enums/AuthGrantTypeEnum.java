package com.zhengqing.common.core.enums;

import com.zhengqing.common.base.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> 认证身份方式枚举 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/6/10 15:18
 */
@Getter
@AllArgsConstructor
public enum AuthGrantTypeEnum implements IBaseEnum<String> {

    USERNAME("username", "用户名"),
    PHONE("phone", "手机号"),
    OPENID("openid", "微信openid"),
    CAPTCHA("captcha", "验证码"),
    PHONE_CODE("phone_code", "手机验证码"),
    WECHAT("wechat", "微信"),
    ;

    private String value;
    private String desc;

}
