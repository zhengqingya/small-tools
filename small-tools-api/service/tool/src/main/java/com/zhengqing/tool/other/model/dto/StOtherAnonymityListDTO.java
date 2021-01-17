package com.zhengqing.tool.other.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 小工具 - 其它 - 匿名事件表查询参数
 * </p>
 *
 * @author: zhengqing
 * @description:
 * @date: 2020-10-25 13:27:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("小工具 - 其它 - 匿名事件表查询参数")
public class StOtherAnonymityListDTO {

    @ApiModelProperty(value = "模糊查询内容")
    private String content;

    @ApiModelProperty(value = "状态：是否处理 （0:未处理  1:已处理）")
    private Integer status;

}
