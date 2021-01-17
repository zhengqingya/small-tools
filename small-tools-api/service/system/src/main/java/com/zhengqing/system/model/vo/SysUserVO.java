package com.zhengqing.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * <p>
 * 系统管理 - 用户信息-内部使用 TODO 后期修改
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/4/15 10:48
 */
@Data
@ApiModel("系统管理 - 用户信息-内部使用")
public class SysUserVO {

    @ApiModelProperty(value = "主键ID")
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

    @ApiModelProperty(value = "是否有效(1:有效 0:无效）")
    private Integer isValid;

    @ApiModelProperty(value = "盐值")
    private String salt;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty("性别值")
    private String sexName;

    @ApiModelProperty("角色ID")
    private String roleIds;

    @ApiModelProperty("角色")
    private String roleNames;

    @ApiModelProperty(value = "角色可访问菜单+按钮")
    private List<SysMenuTreeVO> menuAndBtnPermissionList;

}
