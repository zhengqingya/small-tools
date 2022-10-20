package com.zhengqing.demo.api;

import com.alibaba.fastjson.JSON;
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
 * @date 2022/7/16 10:40 上午
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_DEMO + "/api/jackson")
@Api(tags = "test-jackson")
public class JacksonController {

    @PostMapping("/test1")
    public User test1(@RequestBody User params) {
        System.err.println(JSON.toJSONString(params));
        return params;
    }

    @GetMapping("/test2")
    public User test2(@ModelAttribute User params) {
        System.err.println(JSON.toJSONString(params));
        return params;
    }

    @PostMapping("/test3")
    public User test3(@ModelAttribute User params) {
        System.err.println(JSON.toJSONString(params));
        return params;
    }


    @Data
    static class User {
        private Integer id;

        // post请求
        // @JsonProperty("name_xx") // 字段别名
        // @JsonAlias({"name_xx", "name_2"})
        // get请求
        @RequestParamAlias("name_xx")
        private String name;
    }

}


