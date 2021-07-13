package com.zhengqing.tool.crawler.enums;

import com.google.common.collect.Lists;
import com.zhengqing.common.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 文章导出数据类型枚举
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/12 0:01
 */
@Getter
@AllArgsConstructor
public enum StCrawlerExportDataTypeEnum {

    HTML(1, "HTML", ".html"), MARKDOWN(2, "MARKDOWN", ".md"), WORD(3, "WORD", ".docx"), PDF(4, "PDF", ".pdf");

    private final Integer type;
    private final String desc;
    private final String fileExtension;

    private static final List<StCrawlerExportDataTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(StCrawlerExportDataTypeEnum.values()));
    }

    /**
     * 根据指定的数据类型查找相应枚举类
     *
     * @param dataType: 数据类型
     * @return com.zhengqing.modules.basic.enums.EnumDictType
     * @author zhengqingya
     * @date 2020/8/30 2:56
     */
    public static StCrawlerExportDataTypeEnum getEnum(Integer dataType) {
        for (StCrawlerExportDataTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(dataType)) {
                return itemEnum;
            }
        }
        throw new MyException("未找到指定的文章导出数据类型！");
    }

}
