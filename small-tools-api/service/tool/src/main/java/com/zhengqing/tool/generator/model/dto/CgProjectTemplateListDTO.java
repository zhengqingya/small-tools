package com.zhengqing.tool.generator.model.dto;

import com.zhengqing.common.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 项目代码模板表查询参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/22 11:13
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApiModel("项目代码模板表查询参数")
public class CgProjectTemplateListDTO extends BaseDTO {

    @ApiModelProperty(value = "模板ID")
    private Integer projectTemplateId;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    @NotNull(message = "是否作为基础模板使用（1：是 0：否） 不能为空！")
    @ApiModelProperty(value = "是否作为基础模板使用（1：是 0：否）")
    private Integer isBasic;

    // @ApiModelProperty(value = "是否排除顶级父包")
    // private Boolean isExcludeParentPackage;

}
