package com.zhengqing.auth.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.zhengqing.auth.model.dto.AuthDTO;
import com.zhengqing.common.base.constant.SecurityConstant;
import com.zhengqing.common.base.util.MyBeanUtil;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.common.security.context.JwtCustomUserContext;
import com.zhengqing.common.security.model.bo.JwtCustomUserBO;
import com.zhengqing.common.security.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p> OAuth2认证api </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/6/11 4:48 PM
 */
@Slf4j
@Api(tags = "OAuth2认证api")
@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
public class AuthController {

    private TokenEndpoint tokenEndpoint;
    private KeyPair keyPair;

    /**
     * headers: Authorization: "Basic d2ViOjEyMzQ1Ng=="   [ base64解密后为web/123456 ]
     * auth服务直接调用登录 =》 http://127.0.0.1:1219/oauth/token?client_id=web&client_secret=123456&grant_type=password&username=admin&password=123456
     * gateway服务调用登录 =》 http://127.0.0.1:1218/auth/oauth/token?client_id=web&client_secret=123456&grant_type=password&username=admin&password=123456
     * 刷新令牌 =》 http://127.0.0.1:1218/auth/oauth/token?client_id=web&client_secret=123456&grant_type=refresh_token&refresh_token=xxx
     * 验证码模式 =》 http://127.0.0.1:1218/auth/oauth/token?client_id=web&client_secret=123456&username=admin&password=123456&grant_type=captcha&code=xxx&uuid=xxx
     */
    @ApiOperation("登录")
    @GetMapping("token")
    public OAuth2AccessToken getAccessToken(@ApiIgnore Principal principal, @ModelAttribute AuthDTO params) {
        params.setClient_id(JwtUtil.getClientId());
        params.setClient_secret(JwtUtil.getClientSecretForBasic());
        return this.accessToken(principal, params);
    }

    @ApiOperation("登录(swagger授权使用)")
    @PostMapping("token")
    public Map<String, String> postAccessToken(@ApiIgnore Principal principal, @ModelAttribute AuthDTO params) {
        // swagger集成认证时会post请求进行base64加密
        params.setClient_id(JwtUtil.getClientId());
        params.setClient_secret(JwtUtil.getClientSecretForBasic());
        OAuth2AccessToken oAuth2AccessToken = this.accessToken(principal, params);
        /**
         * UI授权成功后取值逻辑见：{@see /knife4j-spring-ui/3.0.3/knife4j-spring-ui-3.0.3.jar!/META-INF/resources/webjars/oauth/oauth2.html}
         *                 that.cacheValue.accessToken=data.token_type+" "+data.access_token;
         *                 that.cacheValue.tokenType=data.token_type;
         */
        return new HashMap<String, String>(2) {{
            this.put("token_type", oAuth2AccessToken.getTokenType());
            this.put("access_token", oAuth2AccessToken.getValue());
        }};
    }

    @SneakyThrows(Exception.class)
    private OAuth2AccessToken accessToken(@ApiIgnore Principal principal, @ModelAttribute AuthDTO params) {
        log.info("OAuth认证授权 请求参数：{}", JSON.toJSONString(params));

        // 1、认证
        OAuth2AccessToken accessToken = this.tokenEndpoint.postAccessToken(principal, MyBeanUtil.objToMapStr(params)).getBody();

        // 2、拿到其中的自定义用户信息存入redis中
        Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
        JwtCustomUserBO user = JSONObject.parseObject(JSON.toJSONString(additionalInformation), JwtCustomUserBO.class);
        user.setToken(accessToken.getTokenType() + " " + accessToken.getValue());
        user.setExpireTime(MyDateUtil.dateToStr(accessToken.getExpiration()));
        RedisUtil.setEx(SecurityConstant.JWT_CUSTOM_USER + user.getJti(), JSON.toJSONString(user), accessToken.getExpiresIn(), TimeUnit.SECONDS);
        return accessToken;
    }

    @ApiOperation("注销")
    @DeleteMapping("logout")
    @SneakyThrows(Exception.class)
    public void logout() {
        JwtCustomUserBO jwtUserBO = JwtCustomUserContext.get();
        String jti = jwtUserBO.getJti();
        RedisUtil.delete(SecurityConstant.JWT_CUSTOM_USER + jti);
    }

    @ApiOperation("获取RSA公钥")
    @GetMapping("public-key")
    public Map<String, Object> getPublicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}
