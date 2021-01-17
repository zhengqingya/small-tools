package com.zhengqing.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 菜单按钮展示参数
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/9/10 22:03
 */
@Data
@ApiModel("菜单按钮展示参数")
public class SysMenuBtnListVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

    @ApiModelProperty(value = "按钮值")
    private Integer btnId;

    @ApiModelProperty(value = "按钮名称")
    private String btnName;
}
