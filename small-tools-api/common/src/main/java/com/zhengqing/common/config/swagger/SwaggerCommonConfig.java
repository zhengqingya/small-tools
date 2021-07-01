package com.zhengqing.common.config.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.zhengqing.common.config.CommonProperty;
import com.zhengqing.common.config.CommonProperty.Swagger;
import com.zhengqing.common.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * <p>
 * swagger配置类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/5/15 17:41
 */
@Slf4j
@Configuration
@EnableKnife4j
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
// 禁用swagger
// @Profile({"dev"})
@ConditionalOnProperty(value = "on-off.swagger", havingValue = "true")
public class SwaggerCommonConfig {

    /**
     * 初始化默认组
     */
    @Bean
    @Order(value = 1)
    protected Docket initDefaultRestApi(CommonProperty commonProperty) {
        StopWatch watch = new StopWatch();
        watch.start();

        Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(this.apiInfo(commonProperty))
                .forCodeGeneration(true).genericModelSubstitutes(ResponseEntity.class)
                // 对不同的api路径作分组展示
                .groupName(Docket.DEFAULT_GROUP_NAME).select().apis(RequestHandlerSelectors.any()).paths(regex("/.*"))
                // 排除错误路径
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .paths(Predicates.not(PathSelectors.regex("/actuator")))
                .paths(Predicates.not(PathSelectors.regex("/actuator/.*"))).build().securitySchemes(this.securitySchemes())
                .securityContexts(this.securityContexts());

        watch.stop();
        log.info("************ Swagger DEFAULT_GROUP_NAME Started in {} ms ************", watch.getTotalTimeMillis());
        return docket;
    }

    /**
     * 初始化指定组
     */
    @Bean
    @Order(value = 2)
    protected Docket initOtherRestApi(CommonProperty commonProperty) {
        Swagger swagger = commonProperty.getSwagger();
        String groupName = swagger.getGroupName();
        StopWatch watch = new StopWatch();
        watch.start();

        Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(this.apiInfo(commonProperty))
                .forCodeGeneration(true).genericModelSubstitutes(ResponseEntity.class)
                // 对不同的api路径作分组展示
                .groupName(groupName).select().apis(RequestHandlerSelectors.any())
                .paths(regex(swagger.getPathIncludePattern())).build().securitySchemes(this.securitySchemes())
                .securityContexts(this.securityContexts());

        watch.stop();
        log.info("************ Swagger 【{}】 Started in {} ms ************", groupName, watch.getTotalTimeMillis());
        return docket;
    }

    /**
     * swagger-api接口描述信息
     */
    private ApiInfo apiInfo(CommonProperty commonProperty) {
        Swagger swagger = commonProperty.getSwagger();
        return new ApiInfoBuilder().title(swagger.getTitle()).description(swagger.getDescription())
                .version(swagger.getVersion()).termsOfServiceUrl(swagger.getTermsOfServiceUrl())
                .contact(new Contact(swagger.getContact().getName(), swagger.getContact().getUrl(),
                        swagger.getContact().getEmail()))
                .license(swagger.getLicense()).licenseUrl(swagger.getLicenseUrl()).build();
    }

    /**
     * 配置全局token
     */
    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = Lists.newArrayList();
        apiKeyList.add(new ApiKey("Token", AppConstant.REQUEST_HEADER_TOKEN, "header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> contextList = Lists.newArrayList();
        contextList.add(SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("^(?!auth).*$")).build());
        return contextList;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> list = Lists.newArrayList();
        list.add(new SecurityReference("token", authorizationScopes));
        return list;
    }

}
