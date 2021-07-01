package com.zhengqing.tool.db.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 小工具 - 数据库 - 数据源配置信息表查询参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-09-02 14:45:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("小工具 - 数据库 - 数据源配置信息表查询参数")
public class StDbDataSourceListDTO {

    @ApiModelProperty(value = "数据源名称")
    private String name;

}
