package com.zhengqing.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 菜单按钮保存参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 21:00
 */
@Data
@ApiModel("菜单按钮保存参数")
public class SysMenuBtnSaveDTO {

    @NotNull(message = "菜单id")
    @ApiModelProperty(value = "菜单id")
    private Integer menuId;

    @ApiModelProperty(value = "菜单下按钮的ids")
    private List<Integer> btnIdList;

}
