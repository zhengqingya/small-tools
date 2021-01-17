package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 系统管理-用户角色关联表
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/4/15 18:24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("系统管理-用户角色关联表")
@TableName("t_sys_user_role")
public class SysUserRole extends BaseEntity<SysUserRole> {

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "user_id", type = IdType.INPUT)
    private Integer userId;

    @ApiModelProperty(value = "角色ID")
    private String roleIds;

}
