package com.zhengqing.auth.api;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> Oauth2测试权限接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/6/11 5:22 PM
 */
@RestController
@RequestMapping("")
@Api(tags = "Oauth2测试权限接口")
public class HelloController {

    @GetMapping("hello")
    public String hello() {
        return "Hello World...";
    }

}
