package com.zhengqing.tool.generator.enums;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 数据库类型
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2019/8/22 11:15
 */
@Getter
@AllArgsConstructor
public enum CgDatabaseTypeEnum {

    MySQL("com.mysql.jdbc.Driver", 1), Oracle("oracle.jdbc.driver.OracleDriver", 2);

    private final String driver;
    private final Integer type;

    private static final List<CgDatabaseTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(CgDatabaseTypeEnum.values()));
    }

    public static CgDatabaseTypeEnum getEnum(Integer type) {
        for (CgDatabaseTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        return MySQL;
    }

}
