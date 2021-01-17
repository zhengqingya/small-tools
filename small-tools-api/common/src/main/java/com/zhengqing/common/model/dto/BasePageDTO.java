package com.zhengqing.common.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 基础分页检索参数
 * </p>
 *
 * @description:
 * @author: zhengqing
 * @date: 2019/9/13 0013 1:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("后台基础传入参数")
public class BasePageDTO extends BaseDTO {

    @ApiModelProperty(value = "当前页", required = true, position = 0)
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页显示数量", required = true, position = 1)
    private Integer pageSize = Integer.MAX_VALUE;

}
