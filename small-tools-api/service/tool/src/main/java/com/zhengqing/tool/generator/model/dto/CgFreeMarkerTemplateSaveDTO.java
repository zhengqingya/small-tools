package com.zhengqing.tool.generator.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 代码生成器 - FreeMarker模板数据配置表保存参数
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
@ApiModel("代码生成器 - FreeMarker模板数据配置表保存参数")
// @FieldRepeatValidator(tableName = "t_cg_free_marker_template", fieldNames = {"templateKey"},
// dbFieldNames = {"template_key"}, message = "模板键重复，请重新输入！")
public class CgFreeMarkerTemplateSaveDTO extends BaseDTO {

    @NotNull(groups = {UpdateGroup.class}, message = "主键ID不能为空!")
    @ApiModelProperty(value = "主键ID")
    private Integer freeMarkerTemplateId;

    @NotBlank(message = "模板键不能为空！")
    @ApiModelProperty(value = "键")
    private String templateKey;

    @ApiModelProperty(value = "值")
    private String templateValue;

    @ApiModelProperty(value = "是否公共使用（1：是 0：否） - 注：只有管理员才用权限处理此字段哦")
    private Integer isCommon;

}
