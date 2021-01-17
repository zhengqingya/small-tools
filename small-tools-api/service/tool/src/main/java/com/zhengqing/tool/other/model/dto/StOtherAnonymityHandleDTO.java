package com.zhengqing.tool.other.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 小工具 - 其它 - 匿名事件表提交参数
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
@ApiModel("小工具 - 其它 - 匿名事件表处理参数")
public class StOtherAnonymityHandleDTO {

    @NotNull(message = "主键ID不能为空!")
    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @ApiModelProperty(value = "匿名处理人")
    private String anonymousHandlerName;

    @NotBlank(message = "备注不能为空!")
    @ApiModelProperty(value = "备注")
    private String remark;

}
