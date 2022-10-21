package com.zhengqing.demo.api;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.custom.requestparamalias.RequestParamAlias;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

/**
 * <p> 测试 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/10/21 20:00
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_DEMO + "/api/thirdparty")
@Api(tags = "test-third-party")
public class ThirdPartyController {

    @PostMapping("post")
    public UserDTO post(@RequestBody UserDTO params) {
        System.err.println(JSON.toJSONString(params));
        return params;
    }

    @GetMapping("get")
    public String get(@RequestParam("xxx") String name) {
        return name;
    }

    @GetMapping("get-plus")
    public UserDTO getPlus(@ModelAttribute UserDTO params) {
        System.err.println(JSON.toJSONString(params));
        return params;
    }


    /**
     * 对接第三方平台
     * *** 属性别名 ***       fastjson/jackjson
     * 一: post请求
     * -      1. `@JsonProperty`：序列化和反序列化过程中修改java属性名
     * -      2. `@JsonAlias`：反序列化时让java属性可以接收多个别名
     * 二: get请求
     * -      需要自定义注解 ex: `@RequestParamAlias`
     */
    @Data
    static class UserDTO {
        @JsonProperty("user_id")
        private Long id;

        @JsonAlias({"user_name", "user_name_other"})
        private String userName;

        @RequestParamAlias("user_type")
        private String userType;
    }

}


