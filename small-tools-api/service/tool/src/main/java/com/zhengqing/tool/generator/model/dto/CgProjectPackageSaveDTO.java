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
 * 项目包保存参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/22 11:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("项目包保存参数")
@FieldRepeatValidator(tableName = "t_cg_project_package", fieldNames = {"projectId", "parentId", "name"},
        dbFieldNames = {"project_id", "parent_id", "name"}, message = "包名重复，请重新输入!")
public class CgProjectPackageSaveDTO extends BaseDTO {

    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空!")
    @ApiModelProperty(value = "包ID")
    private Integer id;

    @ApiModelProperty(value = "包名")
    @NotBlank(message = "包名称不能为空")
    @Length(max = 50, message = "包名不能超过50个字符")
    private String name;

    @NotNull(message = "父包id不能为空！")
    @ApiModelProperty(value = "父包ID")
    private Integer parentId;

    @NotNull(message = "项目id不能为空！")
    @ApiModelProperty(value = "包关联项目ID")
    private Integer projectId;

}
