package com.zhengqing.tool.generator.model.dto;

import javax.validation.constraints.NotBlank;

import com.zhengqing.common.model.dto.BaseDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 代码生成器 - FreeMarker模板数据测试提交参数
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/11/17 22:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("代码生成器 - FreeMarker模板数据测试提交参数")
public class CgFreeMarkerTemplateTestDataDTO extends BaseDTO {

    @NotBlank(message = "模板内容不能为空！")
    @ApiModelProperty(value = "模板内容")
    private String templateContent;

}
