package com.zhengqing.tool.other.model.dto;

import com.zhengqing.common.validator.common.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 小工具 - 其它 - 匿名事件表提交参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-10-25 13:27:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("小工具 - 其它 - 匿名事件表提交参数")
public class StOtherAnonymitySaveDTO {

    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @NotBlank(message = "内容不能为空!")
    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "匿名用户名")
    private String anonymousUserName;

    @ApiModelProperty(value = "匿名处理人")
    private String anonymousHandlerName;

    @ApiModelProperty(value = "备注")
    private String remark;

}
