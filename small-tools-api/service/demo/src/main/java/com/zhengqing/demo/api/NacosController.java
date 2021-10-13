package com.zhengqing.demo.api;

import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.zhengqing.common.api.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

/**
 * <p> nacos-api </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/11 16:24
 */
@Slf4j
@RestController
@RequestMapping("/nacos/test")
@Api(tags = "nacos-api")
public class NacosController extends BaseController {

    @Value("${spring.cloud.nacos.discovery.username}")
    private String username;

    @Value("${spring.cloud.nacos.discovery.password}")
    private String password;

    // http://127.0.0.1:20040/nacos/test/getNacosInstance
    @SneakyThrows(Exception.class)
    @GetMapping("getNacosInstance")
    @ApiOperation("获取nacos注册实例信息")
    public void getNacosInstance() {
        /**
         * 可参考 https://github.com/alibaba/nacos/blob/master/example/src/main/java/com/alibaba/nacos/example/App.java
         */
        Properties properties = new Properties();
        properties.setProperty("serverAddr", "www.zhengqingya.com:8848,www.zhengqingya.com:8848");
        properties.setProperty("namespace", "dev");
        properties.setProperty("username", username);
        properties.setProperty("password", password);
        NamingService naming = NamingFactory.createNamingService(properties);
        naming.registerInstance("nacos.test.3", "www.zhengqingya.com", 8888, "TEST1");
        naming.registerInstance("nacos.test.3", "www.zhengqingya.com", 9999, "DEFAULT");
        log.info("nacos: {}", naming.getAllInstances("nacos.test.3"));
    }


}
