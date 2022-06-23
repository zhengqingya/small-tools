package com.zhengqing.auth.config;

import com.zhengqing.common.web.config.CommonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 配置信息
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/19 9:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Configuration
@ConfigurationProperties(prefix = "small-tools")
public class AuthProperty extends CommonProperty {

    /**
     * 安全认证
     */
    private final Auth auth = new Auth();

    /**
     * JWT
     */
    private Jwt securityJwt;

    @Data
    @NoArgsConstructor
    public static class Auth {
        /**
         * 忽略安全认证的url
         */
        private String[] ignoreUrls;
    }

    @Data
    @NoArgsConstructor
    public static class Jwt {
        /**
         * 证书路径
         */
        private String keyStoreLocation;
        /**
         * 证书秘钥
         */
        private String keyStoreSecret;
        /**
         * 证书别名
         */
        private String keyPairAlias;
        /**
         * 证书密码
         */
        private String keyPairPassword;
    }

}
