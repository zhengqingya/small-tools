package com.zhengqing.system.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 数据字典枚举
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/12 0:01
 */
@Getter
@AllArgsConstructor
public enum SysDictTypeEnum {

    权限按钮("permission_btn", "权限按钮"), 文件后缀名("file_suffix", "文件后缀名"), Element_Icon图标("element_icon", "Element_Icon图标"),
    小工具_爬虫_CSDN文章导出数据类型("st_crawler_csdn_export_data_type", "小工具_爬虫_CSDN文章导出数据类型"),
    小工具_数据库_数据源类型("st_db_data_source_type", "小工具_数据库_数据源类型"), 第三方帐号授权类型("oauth_type", "第三方帐号授权类型");

    private final String code;
    private final String desc;

    private static final List<SysDictTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(SysDictTypeEnum.values()));
    }

    /**
     * 根据指定的数据字典编码查找相应枚举类
     *
     * @param code: 数据字典编码
     * @return: 数据字典枚举信息
     * @author zhengqingya
     * @date 2020/8/30 2:56
     */
    public static SysDictTypeEnum getEnum(String code) {
        for (SysDictTypeEnum itemEnum : LIST) {
            if (itemEnum.getCode().equals(code)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的数据字典编码！");
    }

}
