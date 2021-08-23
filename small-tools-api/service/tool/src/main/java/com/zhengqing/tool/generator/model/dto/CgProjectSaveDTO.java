package com.zhengqing.tool.generator.model.dto;

import com.zhengqing.common.model.dto.BaseDTO;
import com.zhengqing.common.validator.fieldrepeat.FieldRepeatValidator;
import com.zhengqing.common.validator.fieldrepeat.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 项目管理保存参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/22 11:12
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("项目管理保存参数")
@FieldRepeatValidator(tableName = "t_cg_project", fieldNames = {"name"}, dbFieldNames = {"name"},
        message = "项目名称重复，请重新输入！")
public class CgProjectSaveDTO extends BaseDTO {

    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空!")
    @ApiModelProperty(value = "项目ID")
    private Integer id;

    @ApiModelProperty(value = "项目名称")
    @NotBlank(message = "项目名称不能为空")
    @Length(max = 20, message = "项目名称不能超过20个字符")
    private String name;

}
