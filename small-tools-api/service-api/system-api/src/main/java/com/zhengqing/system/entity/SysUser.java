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
 * 系统管理-用户基础信息表
 * </p>
 *
 * @author: zhengqing
 * @date: 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("系统管理-用户基础信息表")
@TableName("t_sys_user")
// 对注解分组的排序，可以通脱他判断先后顺序
// @GroupSequence({FieldRepeatValidator.class,NotNull.class, Default.class})
// @JsonIgnoreProperties(ignoreUnknown = true)
public class SysUser extends BaseEntity<SysUser> {

    @ApiModelProperty(value = "主键ID groups:标识在更新的时候才能验证非空")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "性别(0:未知 1:男 2:女)")
    private Integer sex;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "盐值")
    private String salt;

    @ApiModelProperty(value = "token")
    private String token;

}
