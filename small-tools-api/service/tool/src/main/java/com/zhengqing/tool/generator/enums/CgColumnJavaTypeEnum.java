package com.zhengqing.tool.generator.enums;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * java字段类型枚举类
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/11/15 22:04
 */
@Getter
@AllArgsConstructor
public enum CgColumnJavaTypeEnum {

    字符串类型_varchar("varchar", "String"), 字符串类型_text("text", "String"), 数字类型_tinyint("tinyint", "Integer"),
    数字类型_int("int", "Integer"), 布尔类型_bit("bit", "Boolean"), 时间类型("datetime", "Date");

    /**
     * 数据库字段类型
     */
    private final String columnTypeDb;
    /**
     * java字段类型
     */
    private final String columnTypeJava;

    private static final List<CgColumnJavaTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(CgColumnJavaTypeEnum.values()));
    }

    /**
     * 根据数据库字段类型匹配java字段类型
     *
     * @param columnTypeDb:
     *            数据库字段类型
     * @return: 枚举信息
     * @author : zhengqing
     * @date : 2020/11/15 22:09
     */
    public static CgColumnJavaTypeEnum getEnum(String columnTypeDb) {
        // 截取数据库字段类型 ex: `int(11)` -> `int`
        if (columnTypeDb.contains("(")) {
            columnTypeDb = columnTypeDb.substring(0, columnTypeDb.indexOf("("));
        }
        for (CgColumnJavaTypeEnum itemEnum : LIST) {
            if (itemEnum.getColumnTypeDb().equalsIgnoreCase(columnTypeDb)) {
                return itemEnum;
            }
        }
        return 字符串类型_varchar;
    }

}
