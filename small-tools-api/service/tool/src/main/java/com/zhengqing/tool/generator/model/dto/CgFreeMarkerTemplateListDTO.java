package com.zhengqing.tool.generator.model.dto;

import com.zhengqing.common.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 代码生成器 - FreeMarker模板数据配置表查询参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-11-02 19:23:15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("代码生成器 - FreeMarker模板数据配置表查询参数")
public class CgFreeMarkerTemplateListDTO extends BaseDTO {

    @ApiModelProperty(value = "模板键")
    private String templateKey;

}
