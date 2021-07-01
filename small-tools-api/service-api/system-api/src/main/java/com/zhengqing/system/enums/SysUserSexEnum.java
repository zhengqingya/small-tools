package com.zhengqing.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 性别枚举类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/28 22:56
 */
@Getter
@AllArgsConstructor
public enum SysUserSexEnum {

    未知(0, "未知"), 男(1, "男"), 女(2, "女");

    private final Integer type;
    private final String desc;

}
