package com.zhengqing.common.model.bo;

import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.util.MyBeanUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

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
@Accessors(chain = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenInfo {

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "过期时间")
    private long expirationTime;

    public static UserTokenInfo buildUser(Map<String, Object> map) {
        return buildUser(map, AppConstant.DEFAULT_EXPIRES_TIME);
    }

    public static UserTokenInfo buildUser(Map<String, Object> map, Long expirationTime) {
        UserTokenInfo userInfo = new UserTokenInfo();
        UserInfo user = MyBeanUtil.mapToObj(map, UserInfo.class);
        if (user == null) {
            return userInfo;
        }
        userInfo.setExpirationTime(System.currentTimeMillis() + expirationTime);
        userInfo.setUserId(user.getUserId());
        userInfo.setUsername(user.getUsername());
        userInfo.setNickname(user.getNickname());
        return userInfo;
    }

    @Data
    @NoArgsConstructor
    public static class UserInfo {
        @ApiModelProperty(value = "用户ID")
        private Integer userId;

        @ApiModelProperty(value = "账号")
        private String username;

        @ApiModelProperty(value = "昵称")
        private String nickname;

        @ApiModelProperty(value = "token")
        private String token;
    }

}
