package com.zhengqing.auth.security.config;

import cn.hutool.core.util.StrUtil;
import com.zhengqing.auth.security.core.userdetails.sys.SysUserDetails;
import com.zhengqing.auth.security.core.userdetails.ums.UmsUserDetails;
import com.zhengqing.common.security.enums.AuthSourceEnum;
import com.zhengqing.common.security.model.bo.JwtCustomUserBO;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * <p> JWT内容增强 </p>
 *
 * @author zhengqingya
 * @description JWT 默认生成的用户信息主要是用户角色、用户名等，这里在生成的 JWT 上面添加额外的信息
 * {@link JwtCustomUserBO}
 * @date 2022/4/2 10:21
 */
@Component
public class CustomAdditionalInformation implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> info = accessToken.getAdditionalInformation();
        info.put("author", "zhengqingya");
        Object principal = authentication.getUserAuthentication().getPrincipal();
        if (principal instanceof SysUserDetails) {
            // ****** B端后台系统用户
            info.put("authSource", AuthSourceEnum.B.getValue());
            SysUserDetails sysUserDetails = (SysUserDetails) principal;
            info.put("sysUserId", sysUserDetails.getSysUserId());
            info.put("username", sysUserDetails.getUsername());
            // 认证身份标识(username:用户名；)
            if (StrUtil.isNotBlank(sysUserDetails.getAuthenticationIdentity())) {
                info.put("authenticationIdentity", sysUserDetails.getAuthenticationIdentity());
            }
        } else if (principal instanceof UmsUserDetails) {
            // ****** C端小程序用户
            info.put("authType", AuthSourceEnum.C.getValue());
            UmsUserDetails umsUserDetails = (UmsUserDetails) principal;
            info.put("umsUserId", umsUserDetails.getUmsUserId());
            info.put("username", umsUserDetails.getUsername());
            // 认证身份标识(mobile:手机号；openId:开放式认证系统唯一身份标识)
            if (StrUtil.isNotBlank(umsUserDetails.getAuthenticationIdentity())) {
                info.put("authenticationIdentity", umsUserDetails.getAuthenticationIdentity());
            }
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }

}
