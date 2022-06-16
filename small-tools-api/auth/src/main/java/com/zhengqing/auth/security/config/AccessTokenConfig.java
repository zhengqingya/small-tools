package com.zhengqing.auth.security.config;

import com.zhengqing.auth.config.AuthProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * <p> 配置生成token存储 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/4/1 12:00
 */
@Configuration
@RequiredArgsConstructor
public class AccessTokenConfig {

    private final AuthProperty authProperty;

    /**
     * jwt token存储模式
     */
    @Bean
    public JwtTokenStore jwtTokenStore() {
        // jwt -- 无状态登录，服务端不需要保存信息
        return new JwtTokenStore(this.jwtAccessTokenConverter());
    }

    /**
     * 使用非对称加密算法对token签名
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(this.keyPair());
        return converter;
    }

    /**
     * 密钥库中获取密钥对(公钥+私钥)
     */
    @Bean
    public KeyPair keyPair() {
        AuthProperty.Jwt securityJwt = this.authProperty.getSecurityJwt();
        // 1、创建秘钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                // 秘钥位置
                new ClassPathResource(securityJwt.getKeyStoreLocation()),
                // 秘钥库密码
                securityJwt.getKeyStoreSecret().toCharArray()
        );
        // 2、基于工厂拿到私钥
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(
                securityJwt.getKeyPairAlias(),
                securityJwt.getKeyPairPassword().toCharArray()
        );
        return keyPair;
    }

}
