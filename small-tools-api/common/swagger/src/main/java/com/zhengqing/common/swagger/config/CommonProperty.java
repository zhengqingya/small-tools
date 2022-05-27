package com.zhengqing.common.swagger.config;

import lombok.Data;

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
public class CommonProperty {

    /**
     * ip
     */
    private String ip;

    /**
     * SWAGGER参数
     */
    private final Swagger swagger = new Swagger();

    /**
     * SWAGGER接口文档参数
     */
    @Data
    public static class Swagger {
        private String groupName;
        private String pathIncludePattern;
        private String title;
        private String description;
        private String version;
        private String termsOfServiceUrl;
        private String license;
        private String licenseUrl;
        private Contact contact;
    }

    @Data
    public static class Contact {
        private String name;
        private String url;
        private String email;
    }

}
