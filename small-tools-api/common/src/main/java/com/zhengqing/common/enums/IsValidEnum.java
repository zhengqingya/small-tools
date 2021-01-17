package com.zhengqing.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 数据是否有效枚举
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/4/12 0:01
 */
@Getter
@AllArgsConstructor
public enum IsValidEnum {

    有效(1, "有效"), 无效(0, "无效");

    /**
     * 值
     */
    private final int value;
    /**
     * 描述
     */
    private final String desc;

}
