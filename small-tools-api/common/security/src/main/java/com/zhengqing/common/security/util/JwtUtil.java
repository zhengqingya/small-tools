package com.zhengqing.common.security.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.nimbusds.jose.JWSObject;
import com.zhengqing.common.base.constant.BaseConstant;
import com.zhengqing.common.base.constant.SecurityConstant;
import com.zhengqing.common.base.context.AuthSourceContext;
import com.zhengqing.common.base.context.SysUserContext;
import com.zhengqing.common.base.context.UmsUserContext;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.security.enums.AuthGrantTypeEnum;
import com.zhengqing.common.security.enums.AuthSourceEnum;
import com.zhengqing.common.security.model.bo.JwtUserBO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

/**
 * <p>
 * jwt工具类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/8/1 21:52
 */
@Slf4j
public class JwtUtil {


    /**
     * 解析token
     *
     * @param token jwt
     * @return 用户信息
     * @author zhengqingya
     * @date 2022/6/14 10:35 PM
     */
    @SneakyThrows(Exception.class)
    public static JwtUserBO parse(String token) {
        token = StrUtil.replaceIgnoreCase(token, SecurityConstant.JWT_PREFIX, Strings.EMPTY);
        String payload = StrUtil.toString(JWSObject.parse(token).getPayload());
        Assert.notBlank(payload, "token失效");
        JwtUserBO jwtUserBO = JSONObject.parseObject(payload, JwtUserBO.class);
        jwtUserBO.setExpireTime(MyDateUtil.dateToStr(new Date(jwtUserBO.getExp() * 1000)));
        return jwtUserBO;
    }

    /**
     * 获取用户id
     *
     * @return 用户id
     * @author zhengqingya
     * @date 2022/6/15 13:03
     */
    public static String getUserId() {
        String authType = AuthSourceContext.get();
        if (StringUtils.isBlank(authType)) {
            return BaseConstant.DEFAULT_CONTEXT_KEY_USER_ID;
        }
        return String.valueOf(
                AuthSourceEnum.B.getValue().equals(authType)
                        ? SysUserContext.getUserId()
                        : UmsUserContext.getUserId()
        );
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     * @author zhengqingya
     * @date 2022/6/15 13:03
     */
    public static String getUsername() {
        String authType = AuthSourceContext.get();
        if (StringUtils.isBlank(authType)) {
            return BaseConstant.DEFAULT_CONTEXT_KEY_USERNAME;
        }
        return String.valueOf(
                AuthSourceEnum.B.getValue().equals(authType)
                        ? SysUserContext.getUsername()
                        : UmsUserContext.getUsername()
        );
    }

    /**
     * 获取客户端ID
     *
     * @return 客户端ID
     * @author zhengqingya
     * @date 2022/6/16 14:14
     */
    @SneakyThrows
    public static String getClientId() {
        // 方式一：从请求路径中获取
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String clientId = request.getParameter(SecurityConstant.CLIENT_ID_KEY);
        if (StrUtil.isNotBlank(clientId)) {
            return clientId;
        }

        // 方式二：从请求头获取 -- tips:swagger集成认证时会进行base64加密
        String token = request.getHeader(SecurityConstant.AUTHORIZATION_KEY);
        if (token.startsWith(SecurityConstant.BASIC_PREFIX)) {
            return getClientIdForBasic();
        }
        // 如果从请求头获取的是认证过后的token，则解析token中的数据
        return parse(token).getClientId();
    }

    /**
     * 从请求头中获取clientId
     *
     * @return clientId
     * @author zhengqingya
     * @date 2022/6/27 17:49
     */
    public static String getClientIdForBasic() {
        return getClientIdAndSecret().split(":")[0];
    }

    /**
     * 从请求头中获取clientSecret
     *
     * @return clientSecret
     * @author zhengqingya
     * @date 2022/6/27 17:49
     */
    public static String getClientSecretForBasic() {
        return getClientIdAndSecret().split(":")[1];
    }

    /**
     * 从请求头中获取clientId和clientSecret
     * headers: Authorization: "Basic d2ViOjEyMzQ1Ng=="   [ base64解密后为web/123456 ]
     *
     * @return clientId:clientSecret
     * @author zhengqingya
     * @date 2022/6/27 17:48
     */
    private static String getClientIdAndSecret() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String basic = request.getHeader(SecurityConstant.AUTHORIZATION_KEY);
        basic = basic.replace(SecurityConstant.BASIC_PREFIX, Strings.EMPTY);
        return new String(Base64.getDecoder().decode(basic.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    /**
     * 获取认证身份标识
     *
     * @return {@link AuthGrantTypeEnum}
     * @author zhengqingya
     * @date 2022/6/16 14:14
     */
    @SneakyThrows
    public static String getAuthGrantType() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String refreshToken = request.getParameter(SecurityConstant.REFRESH_TOKEN_KEY);
        String payload = StrUtil.toString(JWSObject.parse(refreshToken).getPayload());
        JSONObject jsonObject = JSONObject.parseObject(payload);
        String authGrantType = jsonObject.getString(SecurityConstant.GRANT_TYPE);
        if (StrUtil.isBlank(authGrantType)) {
            authGrantType = AuthGrantTypeEnum.USERNAME.getValue();
        }
        return authGrantType;
    }

}
