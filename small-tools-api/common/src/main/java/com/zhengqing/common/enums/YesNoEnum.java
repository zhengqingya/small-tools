package com.zhengqing.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 是/否
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/4/12 0:01
 */
@Getter
@AllArgsConstructor
public enum YesNoEnum {
    是(1), 否(0);

    private final int value;

}
