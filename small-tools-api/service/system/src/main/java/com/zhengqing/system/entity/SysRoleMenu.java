package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 系统管理-角色菜单关联表
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("系统管理-角色菜单关联表")
@TableName("t_sys_role_menu")
public class SysRoleMenu extends BaseEntity<SysRoleMenu> {

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

}
