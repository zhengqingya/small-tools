package com.zhengqing.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 报表导入模板枚举类
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/9/7 14:23
 */
@Getter
@AllArgsConstructor
public enum ExcelImportFileTypeEnum {

    CSDN文章信息数据("/CSDN文章信息数据.xml");

    /**
     * 导入映射文件XML
     */
    private String mappingXml;

}
