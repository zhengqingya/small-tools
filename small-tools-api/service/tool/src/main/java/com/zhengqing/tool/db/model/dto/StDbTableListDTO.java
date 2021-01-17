package com.zhengqing.tool.db.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 数据库表查询参数
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/9/4 13:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("数据库表查询参数")
public class StDbTableListDTO {

    @ApiModelProperty(value = "数据源id")
    private Integer dataSourceId;

}
