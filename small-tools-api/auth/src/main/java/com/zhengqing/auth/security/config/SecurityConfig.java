package com.zhengqing.auth.security.config;

import com.zhengqing.auth.config.AuthProperty;
import com.zhengqing.auth.enums.PasswordEncoderTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;


/**
 * <p> Security 核心配置类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/4/1 11:50
 */
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService sysUserDetailsService;
    private final AuthProperty authProperty;

    /**
     * 密码编码器
     * 委托方式，根据密码的前缀选择对应的encoder {@link PasswordEncoderTypeEnum}
     * 密码判读 {@link DaoAuthenticationProvider#additionalAuthenticationChecks}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 认证管理对象
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置用户 -- 校验用户
     * 校验客户端见 {@link AuthorizationServerConfig#configure(ClientDetailsServiceConfigurer)}
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth
                // 用户名密码认证
                .authenticationProvider(this.passwordAuthenticationProvider());
    }

    /**
     * 用户名密码认证授权提供者
     */
    @Bean
    public DaoAuthenticationProvider passwordAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.sysUserDetailsService);
        provider.setPasswordEncoder(this.passwordEncoder());
        // 是否隐藏用户不存在异常，默认:true-隐藏；false-抛出异常；
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    /**
     * 权限配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 放行url
                .authorizeRequests().antMatchers(this.authProperty.getAuth().getIgnoreUrls()).permitAll()
                // 其余所有请求都需要认证
                .anyRequest().authenticated()
                // 禁用CSRF
                .and().csrf().disable();
    }

}
