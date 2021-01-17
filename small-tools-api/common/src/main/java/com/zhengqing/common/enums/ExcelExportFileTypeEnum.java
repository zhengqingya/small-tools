package com.zhengqing.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 报表导出模板枚举类
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/9/7 13:56
 */
@Getter
@AllArgsConstructor
public enum ExcelExportFileTypeEnum {

    CSDN文章信息数据("/CSDN文章信息数据导出模板.xls", "CSDN文章信息数据");

    /**
     * 导出模板文件
     */
    private String templateFile;
    /**
     * 导出表格名
     */
    private String sheetName;

}
