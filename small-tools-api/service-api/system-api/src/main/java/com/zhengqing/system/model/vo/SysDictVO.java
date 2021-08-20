package com.zhengqing.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 数据字典表
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:57
 */
@Data
@ApiModel("数据字典表")
public class SysDictVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典ID")
    private Integer id;

    @ApiModelProperty(value = "字典类型id(关联`t_sys_dict_type`表`id`字段)")
    private Integer dictTypeId;

    @ApiModelProperty(value = "类型编码")
    private String code;

    @ApiModelProperty(value = "字典名(展示用)")
    private String name;

    @ApiModelProperty(value = "字典值")
    private String value;

    @ApiModelProperty(value = "展示排序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;

}
