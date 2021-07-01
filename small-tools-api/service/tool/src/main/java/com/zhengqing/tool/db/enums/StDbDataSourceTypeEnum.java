package com.zhengqing.tool.db.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 数据源类型枚举类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/2 15:52
 */
@Getter
@AllArgsConstructor
public enum StDbDataSourceTypeEnum {

    MySQL("1", "com.mysql.jdbc.Driver", "MySQL"), Oracle("2", "oracle.jdbc.driver.OracleDriver", "Oracle");

    private final String type;
    private final String driverClassName;
    private final String desc;

    private static final List<StDbDataSourceTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(StDbDataSourceTypeEnum.values()));
    }

    public static StDbDataSourceTypeEnum getEnum(String type) {
        for (StDbDataSourceTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到正确的数据源类型！");
    }

}
