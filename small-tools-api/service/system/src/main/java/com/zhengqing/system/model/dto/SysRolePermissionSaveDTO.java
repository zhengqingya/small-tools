package com.zhengqing.system.model.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.zhengqing.common.validator.fieldrepeat.UpdateGroup;
import com.zhengqing.system.model.vo.SysMenuTreeVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 保存角色权限信息传入参数
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/9/10 15:00
 */
@Data
@ApiModel("保存角色权限信息传入参数")
public class SysRolePermissionSaveDTO {

    @NotNull(message = "角色id不能为空！", groups = UpdateGroup.class)
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "角色可访问的菜单ids")
    private List<Integer> menuIdList;

    @ApiModelProperty(value = "菜单树(含拥有的按钮权限，需循环里面的菜单保存其关联的按钮权限信息数据)")
    private List<SysMenuTreeVO> menuAndBtnPermissionTree;

}
