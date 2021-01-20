package com.zhengqing.system.model.dto;

import javax.validation.constraints.NotNull;

import com.zhengqing.common.validator.fieldrepeat.UpdateGroup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 菜单保存参数
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/4/15 20:55
 */
@Data
@ApiModel("菜单保存参数")
public class SysMenuSaveDTO {

    @NotNull(groups = {UpdateGroup.class}, message = "菜单ID不能为空!")
    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

    @ApiModelProperty(value = "菜单名称")
    private String title;

    @ApiModelProperty(value = "菜单名称 - 英文")
    private String name;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单链接url")
    private String path;

    @ApiModelProperty(value = "父类菜单ID")
    private Integer parentId;

    @ApiModelProperty(value = "菜单排序")
    private Integer displayOrder;

    @ApiModelProperty(value = "组件名")
    private String component;

    @ApiModelProperty(value = "是否隐藏 1:隐藏 0:显示")
    private Integer hidden;

    @ApiModelProperty(value = "重定向路径")
    private String redirect;

    @ApiModelProperty(value = "菜单状态 1：启用  0：禁用")
    private Integer status;

    @ApiModelProperty(value = "菜单类型 0菜单 1按钮")
    private Integer type;

    @ApiModelProperty(value = "0 false 1 true")
    private Integer alwaysShow;

    @ApiModelProperty(value = "面包屑 0 false 1 true")
    private Integer breadcrumb;

    @NotNull(message = "系统来源不能为空！")
    @ApiModelProperty(value = "系统来源")
    private Integer systemSource;

}
