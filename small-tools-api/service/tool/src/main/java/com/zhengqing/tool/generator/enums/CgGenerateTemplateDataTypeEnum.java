package com.zhengqing.tool.generator.enums;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 生成模板数据类型
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/12/4 23:35
 */
@Getter
@AllArgsConstructor
public enum CgGenerateTemplateDataTypeEnum {

    默认数据(1, "默认数据"), 测试模板生成配置数据(2, "测试模板生成配置数据");

    private final Integer type;
    private final String desc;

    private static final List<CgGenerateTemplateDataTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(CgGenerateTemplateDataTypeEnum.values()));
    }

    public static CgGenerateTemplateDataTypeEnum getEnum(Integer type) {
        for (CgGenerateTemplateDataTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        return 默认数据;
    }

}
