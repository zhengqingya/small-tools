package com.zhengqing.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 * <p>
 * 系统管理 - 用户信息-展示内容
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/4/15 10:48
 */
@Data
@ApiModel("系统管理 - 用户信息-展示内容")
public class SysUserDetailVO {

    @ApiModelProperty(value = "主键ID")
    private Integer userId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "性别(0:未知 1:男 2:女)")
    private Integer sex;

    @ApiModelProperty("性别值")
    private String sexName;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "是否有效(1:有效 0:无效）")
    private Integer isValid;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty("角色ids")
    private String roleIds;

    @ApiModelProperty("角色")
    private String roleNames;

}
