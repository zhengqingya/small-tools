package com.zhengqing.system.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zhengqing.common.validator.fieldrepeat.FieldRepeatValidator;
import com.zhengqing.common.validator.fieldrepeat.UpdateGroup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 数据字典保存参数
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/4/15 20:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("数据字典保存参数")
@FieldRepeatValidator(tableName = "t_sys_dict", fieldNames = {"name", "dictTypeId"},
    dbFieldNames = {"name", "dict_type_id"}, message = "字段名称重复，请重新输入！")
public class SysDictSaveDTO {

    @NotNull(groups = {UpdateGroup.class}, message = "字典id不能为空!")
    @ApiModelProperty(value = "字典id")
    private Integer id;

    @NotNull(message = "字典类型id不能为空!")
    @ApiModelProperty(value = "字典类型id(关联`t_sys_dict_type`表`id`字段)")
    private Integer dictTypeId;

    @NotBlank(message = "字典名称不能为空!")
    @ApiModelProperty(value = "名称")
    private String name;

    @NotBlank(message = "字典值不能为空!")
    @ApiModelProperty(value = "值")
    private String value;

    @NotNull(message = "展示顺序不能为空!")
    @ApiModelProperty(value = "排序")
    private Integer sort;

}
