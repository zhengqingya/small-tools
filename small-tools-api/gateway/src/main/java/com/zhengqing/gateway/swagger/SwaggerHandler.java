package com.zhengqing.gateway.swagger;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

/**
 * <p>
 * swagger聚合接口，三个接口都是swagger-ui.html需要访问的接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/12/19 18:05
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/swagger-resources")
public class SwaggerHandler {

    private final MySwaggerResourcesProvider mySwaggerResourcesProvider;

    /**
     * 权限处理器
     */
    @GetMapping("/configuration/security")
    public ResponseEntity<SecurityConfiguration> securityConfiguration() {
        return new ResponseEntity<>(SecurityConfigurationBuilder.builder().build(), HttpStatus.OK);
    }

    /**
     * UI处理器
     */
    @GetMapping("/configuration/ui")
    public ResponseEntity<UiConfiguration> uiConfiguration() {
        return new ResponseEntity<>(UiConfigurationBuilder.builder().build(), HttpStatus.OK);
    }

    /**
     * 聚合各个服务的swagger接口
     */
    @GetMapping("")
    public ResponseEntity swaggerResources() {
        return new ResponseEntity<>(mySwaggerResourcesProvider.get(), HttpStatus.OK);
    }

}
