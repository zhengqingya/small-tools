package com.zhengqing.tool.generator.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zhengqing.common.model.dto.BaseDTO;
import com.zhengqing.common.validator.fieldrepeat.FieldRepeatValidator;
import com.zhengqing.common.validator.fieldrepeat.Update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 项目代码模板保存参数
 * </p>
 *
 * @description :
 * @author : zhengqing
 * @date : 2019/8/22 11:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("项目代码模板保存参数")
@FieldRepeatValidator(tableName = "t_cg_project_template", idDbName = "project_template_id",
    fieldNames = {"projectTemplateId", "currentUserId"}, dbFieldNames = {"project_template_id", "data_re_user_id"},
    message = "模板类型重复，请重新选择！")
public class CgProjectTemplateSaveDTO extends BaseDTO {

    // 这里字段改动是注意 对应 `CgProjectTemplateTestDataDTO`

    @NotNull(groups = {Update.class}, message = "id不能为空!")
    @ApiModelProperty(value = "模板ID")
    private Integer projectTemplateId;

    @ApiModelProperty(value = "项目ID")
    @NotNull(message = "请选择关联项目!")
    private Integer projectId;

    @ApiModelProperty(value = "包ID")
    @NotNull(message = "请选择模板类型!")
    private Integer projectPackageId;

    @ApiModelProperty(value = "生成文件名 ex:.UserEntity")
    @NotBlank(message = "生成文件名不能为空!  例如: \"UserEntity\" ")
    private String fileName;

    @ApiModelProperty(value = "生成文件后缀名 ex:.java")
    @NotBlank(message = "生成文件后缀名不能为空!  例如: \".java\" ")
    private String fileSuffix;

    @ApiModelProperty(value = "模板内容")
    @NotBlank(message = "模板内容不能为空!")
    private String content;

    @NotNull(message = "是否作为基础模板使用（1：是 0：否） 不能为空！")
    @ApiModelProperty(value = "是否作为基础模板使用（1：是 0：否）")
    private Integer isBasic;

}
