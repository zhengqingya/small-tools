package com.zhengqing.gateway.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * <p>
 * 配置信息
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/13 10:23
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "small-tools")
public class GatewayProperty {

    /**
     * 安全认证
     */
    private final Auth auth = new Auth();

    @Data
    @NoArgsConstructor
    public static class Auth {
        /**
         * 忽略安全认证的url
         */
        private List<String> ignoreUrls;
        /**
         * Web前端Api需要拦截的url
         */
        private List<String> webApiUrls;
        /**
         * 开放接口Api需要拦截的url
         */
        private List<String> openApiUrls;
    }

}
