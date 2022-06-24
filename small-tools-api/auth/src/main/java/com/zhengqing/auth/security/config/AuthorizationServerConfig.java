package com.zhengqing.auth.security.config;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.auth.security.core.clientdetails.ClientDetailsServiceImpl;
import com.zhengqing.auth.security.core.userdetails.sys.SysUserDetailsServiceImpl;
import com.zhengqing.auth.security.core.userdetails.ums.UmsUserDetailsServiceImpl;
import com.zhengqing.auth.security.extension.grant.captcha.CaptchaTokenGranter;
import com.zhengqing.auth.security.extension.refresh.PreAuthenticatedUserDetailsService;
import com.zhengqing.common.base.enums.ApiResultCodeEnum;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.common.security.enums.AuthClientIdEnum;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p> 授权服务器配置 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/6/11 2:19 PM
 */
@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * {@link SecurityConfig#authenticationManagerBean()}
     */
    private final AuthenticationManager authenticationManager;
    private final ClientDetailsServiceImpl clientDetailsService;
    private final SysUserDetailsServiceImpl sysUserDetailsService;
    private final UmsUserDetailsServiceImpl umsUserDetailsService;
    private final CustomAdditionalInformation customAdditionalInformation;
    private final JwtAccessTokenConverter jwtAccessTokenConverter;
    private final TokenStore tokenStore;

    /**
     * 配置客户端的详细信息 -- 校验客户端
     * 校验用户见 {@link SecurityConfig#configure(AuthenticationManagerBuilder)}
     */
    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) {
        clients.withClientDetails(this.clientDetailsService);
    }

    /**
     * 配置令牌的访问端点和令牌服务
     * 授权码和令牌有什么区别？授权码是用来获取令牌的，使用一次就失效，令牌则是用来获取资源的
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // 获取原有默认授权模式(授权码模式、密码模式、客户端模式、简化模式)的授权者
        List<TokenGranter> granterList = new ArrayList<>(Arrays.asList(endpoints.getTokenGranter()));
        // 添加验证码授权模式授权者
        granterList.add(
                new CaptchaTokenGranter(
                        endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                        endpoints.getOAuth2RequestFactory(), this.authenticationManager
                ));
        CompositeTokenGranter compositeTokenGranter = new CompositeTokenGranter(granterList);
        endpoints
                // 认证管理对象
                .authenticationManager(this.authenticationManager)
                .tokenGranter(compositeTokenGranter)
                // 配置授权码的存储
                .authorizationCodeServices(this.authorizationCodeServices())
                // 配置令牌的存储
                .tokenServices(this.tokenServices());
    }

    /**
     * 配置授权码的存储
     */
    @Bean
    AuthorizationCodeServices authorizationCodeServices() {
        // 内存
        return new InMemoryAuthorizationCodeServices();
    }


    /**
     * 配置 Token 的一些基本信息
     */
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        // 存储位置
        tokenServices.setTokenStore(this.tokenStore);
        // 是否支持刷新
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(this.clientDetailsService);
        // 配置JWT的内容增强器 注入“jwt添加额外信息”相关实例
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Lists.newArrayList(
                this.jwtAccessTokenConverter,
                this.customAdditionalInformation
        ));
        tokenServices.setTokenEnhancer(tokenEnhancerChain);

        // 多用户体系下，刷新token再次认证客户端ID和 UserDetailService 的映射Map
        Map<AuthClientIdEnum, UserDetailsService> clientUserDetailsServiceMap = Maps.newHashMap();
        // 系统管理客户端
        clientUserDetailsServiceMap.put(AuthClientIdEnum.WEB, this.sysUserDetailsService);
        // Android、IOS、H5 移动客户端
        clientUserDetailsServiceMap.put(AuthClientIdEnum.APP, this.umsUserDetailsService);
        // 微信小程序客户端
        clientUserDetailsServiceMap.put(AuthClientIdEnum.MINI, this.umsUserDetailsService);

        // 刷新token模式下，重写预认证提供者替换其AuthenticationManager，可自定义根据客户端ID和认证方式区分用户体系获取认证用户信息
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(new PreAuthenticatedUserDetailsService<>(clientUserDetailsServiceMap));
        tokenServices.setAuthenticationManager(new ProviderManager(Arrays.asList(provider)));

        /** refresh_token有两种使用方式：
         *  默认重复使用(true)：access_token过期刷新时， refresh_token过期时间未改变，仍以初次生成的时间为准
         *  非重复使用(false)：access_token过期刷新时， refresh_token过期时间延续，在refresh_token有效期内刷新便永不失效达到无需再次登录的目的
         */
        tokenServices.setReuseRefreshToken(true);
        return tokenServices;

    }


    /**
     * 自定义认证异常响应数据
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            response.setStatus(HttpStatus.HTTP_OK);
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().print(JSONUtil.toJsonStr(
                    ApiResult.fail(ApiResultCodeEnum.CLIENT_AUTHENTICATION_FAILED.getDesc())
            ));
            response.getWriter().flush();
        };
    }
}
