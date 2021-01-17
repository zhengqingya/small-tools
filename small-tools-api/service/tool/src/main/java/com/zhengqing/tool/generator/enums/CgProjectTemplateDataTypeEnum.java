package com.zhengqing.tool.generator.enums;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 项目模板类型枚举类
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/11/15 15:06
 */
@Getter
@AllArgsConstructor
public enum CgProjectTemplateDataTypeEnum {

    基础模板(1, "基础模板"), 项目模板(0, "项目模板");

    private final Integer dataType;
    private final String desc;

    private static final List<CgProjectTemplateDataTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(CgProjectTemplateDataTypeEnum.values()));
    }

    public static CgProjectTemplateDataTypeEnum getEnum(Integer type) {
        for (CgProjectTemplateDataTypeEnum itemEnum : LIST) {
            if (itemEnum.getDataType().equals(type)) {
                return itemEnum;
            }
        }
        return 项目模板;
    }

}
