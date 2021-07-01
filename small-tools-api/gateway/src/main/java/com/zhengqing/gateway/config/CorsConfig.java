package com.zhengqing.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * <p>
 * 全局配置解决跨域
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/22 9:09
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();
        // ① 设置你要允许的网站域名，如果全允许则设为 *
        corsConfig.addAllowedOrigin("*");
        // corsConfig.addAllowedOrigin("http://www.zhengqingya.com");
        // ② 如果要限制 HEADER 或 METHOD 请自行更改
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");
        // ③ 是否允许携带cookie跨域
        corsConfig.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", corsConfig);
        return new CorsWebFilter(source);
    }

}
