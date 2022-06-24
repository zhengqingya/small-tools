package com.zhengqing.auth.security.extension.grant.captcha;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.zhengqing.common.base.constant.SecurityConstant;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.common.security.enums.AuthGrantTypeEnum;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p> 验证码授权模式授权者 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/6/15 14:35
 */
public class CaptchaTokenGranter extends AbstractTokenGranter {

    /**
     * 声明授权者 CaptchaTokenGranter 支持授权模式 captcha
     * 根据接口传值 grant_type = captcha 的值匹配到此授权者
     * 匹配逻辑详见下面的两个方法
     *
     * @see CompositeTokenGranter#grant(String, TokenRequest)
     * @see AbstractTokenGranter#grant(String, TokenRequest)
     */
    private final AuthenticationManager authenticationManager;

    public CaptchaTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                               OAuth2RequestFactory requestFactory, AuthenticationManager authenticationManager
    ) {
        super(tokenServices, clientDetailsService, requestFactory, AuthGrantTypeEnum.CAPTCHA.getValue());
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        // 1、用户输入的验证码
        Map<String, String> paramMap = new LinkedHashMap(tokenRequest.getRequestParameters());
        String validateCode = paramMap.get("code");
        String uuid = paramMap.get("uuid");
        Assert.isTrue(StrUtil.isNotBlank(validateCode), "验证码不能为空");
        // 2、缓存中的验证码
        String validateCodeKey = SecurityConstant.CAPTCHA_CODE + uuid;
        String correctValidateCode = RedisUtil.get(validateCodeKey);
        // 3、校验
        Assert.isTrue(StrUtil.isNotBlank(correctValidateCode), "验证码已过期");
        Assert.isTrue(validateCode.equals(correctValidateCode), "您输入的验证码不正确");
        RedisUtil.delete(validateCodeKey);
        /**
         * 4、拿到用户名密码进行剩余正常逻辑
         * {@link org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter#getOAuth2Authentication}
         */
        String username = paramMap.get("username");
        String password = paramMap.get("password");
        // 移除后续无用参数
        paramMap.remove("password");
        paramMap.remove("code");
        paramMap.remove("uuid");

        Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
        ((AbstractAuthenticationToken) userAuth).setDetails(paramMap);

        try {
            userAuth = this.authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException var8) {
            throw new InvalidGrantException(var8.getMessage());
        } catch (BadCredentialsException var9) {
            throw new InvalidGrantException(var9.getMessage());
        }

        if (userAuth != null && userAuth.isAuthenticated()) {
            OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
            return new OAuth2Authentication(storedOAuth2Request, userAuth);
        } else {
            throw new InvalidGrantException("Could not authenticate user: " + username);
        }
    }

}
