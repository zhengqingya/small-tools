package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 系统管理-菜单关联按钮权限表
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("系统管理-菜单关联按钮权限表")
@TableName("t_sys_menu_btn")
public class SysMenuBtn extends BaseEntity<SysMenuBtn> {

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

    @ApiModelProperty(value = "按钮值")
    private Integer btnId;

    @ApiModelProperty(value = "状态")
    private Integer status;

}
