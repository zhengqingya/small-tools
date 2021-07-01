package com.zhengqing.tool.generator.enums;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 模板是否公用类型枚举类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/8 15:28
 */
@Getter
@AllArgsConstructor
public enum CgFreeMarkerTemplateCommonTypeEnum {

    公用(1, "公用数据"), 未公用(0, "个人数据");

    private final Integer type;
    private final String desc;

    private static final List<CgFreeMarkerTemplateCommonTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(CgFreeMarkerTemplateCommonTypeEnum.values()));
    }

    public static CgFreeMarkerTemplateCommonTypeEnum getEnum(Integer type) {
        for (CgFreeMarkerTemplateCommonTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        return 未公用;
    }

}
