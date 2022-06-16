package com.zhengqing.common.core.model.bo;

import com.zhengqing.common.base.model.bo.BaseBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>
 * 用户token信息
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/28 23:16
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class JwtUserBO extends BaseBO {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "角色权限")
    private List<String> authorities;

    @ApiModelProperty(value = "客户端ID")
    private String clientId;

    @ApiModelProperty(value = "授权范围")
    private List<String> scope;

    @ApiModelProperty(value = "jwt的唯一身份标识，主要用来作为一次性token")
    private String jti;

    @ApiModelProperty(value = "过期时间(10位时间戳 单位：秒)")
    private Long exp;

    @ApiModelProperty(value = "过期时间(2022-06-01 23:06:53)")
    private String expireTime;

}
