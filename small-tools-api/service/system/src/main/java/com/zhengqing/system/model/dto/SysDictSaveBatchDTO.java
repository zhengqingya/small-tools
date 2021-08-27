package com.zhengqing.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 数据字典批量保存参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("数据字典批量保存参数")
public class SysDictSaveBatchDTO {

    @ApiModelProperty(value = "字典id", example = "1")
    private Integer id;

    @NotBlank(message = "字典类型id不能为空!")
    @ApiModelProperty(value = "字典类型编码", example = "permission_btn")
    private String code;

    @NotBlank(message = "字典名称不能为空!")
    @ApiModelProperty(value = "名称", example = "新增")
    private String name;

    @NotBlank(message = "字典值不能为空!")
    @ApiModelProperty(value = "值", required = true, example = "add")
    private String value;

    @NotNull(message = "展示顺序不能为空!")
    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "备注", example = "this is the add.")
    private String remark;

}
