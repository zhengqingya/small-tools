package com.zhengqing.monitor.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * <p> 监控权限配置 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/6/21 9:16 PM
 */
@EnableWebSecurity
public class WebSecurityConfigurer {
    private final String adminContextPath;

    public WebSecurityConfigurer(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminContextPath + "/");

        return httpSecurity
                .headers().frameOptions().disable()
                .and().authorizeRequests()
                .antMatchers(this.adminContextPath + "/assets/**"
                        , this.adminContextPath + "/login"
                        , this.adminContextPath + "/actuator/**"
                        , this.adminContextPath + "/instances/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(this.adminContextPath + "/login")
                .successHandler(successHandler).and()
                .logout().logoutUrl(this.adminContextPath + "/logout")
                .and()
                .httpBasic().and()
                .csrf()
                .disable()
                .build();
    }

}
