package com.zhengqing.tool.generator.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 代码生成器 - FreeMarker模板数据配置表展示视图
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-11-02 19:23:15
 */
@Data
@ApiModel("代码生成器 - FreeMarker模板数据配置表展示视图")
public class CgFreeMarkerTemplateListVO {

    @ApiModelProperty(value = "主键ID")
    private Integer freeMarkerTemplateId;

    @ApiModelProperty(value = "键")
    private String templateKey;

    @ApiModelProperty(value = "值")
    private String templateValue;

    @ApiModelProperty(value = "模板关联人id")
    private Integer templateReUserId;

    @ApiModelProperty(value = "是否公共使用（1：是 0：否）")
    private Integer isCommon;

    @ApiModelProperty(value = "是否公共使用名称")
    private String isCommonName;

}
