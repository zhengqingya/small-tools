package com.zhengqing.tool.generator.model.dto;

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
 * 代码生成器 - FreeMarker模板数据配置表查询参数
 * </p>
 *
 * @author: zhengqing
 * @description:
 * @date: 2020-11-02 19:23:15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("代码生成器 - FreeMarker模板数据配置表查询参数")
public class CgFreeMarkerTemplateListDTO extends BaseDTO {

    @ApiModelProperty(value = "模板键")
    private String templateKey;

}
