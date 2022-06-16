package com.zhengqing.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> 加密类型枚举 </p>
 *
 * @author zhengqingya
 * @description 根据密码的前缀选择对应的加密方式
 * @date 2022/6/11 6:08 PM
 */
@Getter
@AllArgsConstructor
public enum PasswordEncoderTypeEnum {

    BCRYPT("{bcrypt}", "BCRYPT加密"),
    NOOP("{noop}", "无加密明文");

    private String prefix;
    private String desc;

}
