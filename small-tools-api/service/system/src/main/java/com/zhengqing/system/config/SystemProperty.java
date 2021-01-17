package com.zhengqing.system.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.zhengqing.common.config.CommonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zhyd.oauth.config.AuthConfig;

/**
 * <p>
 * 配置信息
 * </p>
 *
 * @description :
 * @author : zhengqing
 * @date : 2019/8/19 9:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Configuration
@ConfigurationProperties(prefix = "small-tools", ignoreUnknownFields = false)
public class SystemProperty extends CommonProperty {

    /**
     * 第三方授权参数
     */
    private final ThirdpartOauth thirdpartOauth = new ThirdpartOauth();

    @Data
    public static class ThirdpartOauth {
        private String redirectUrlPrefix;
        private String webRedirectUrl;
        private String webBindRedirectUrl;
        private AuthConfig gitee;
        private AuthConfig github;
        private AuthConfig qq;
        private AuthConfig giteeBind;
        private AuthConfig githubBind;
    }

}
