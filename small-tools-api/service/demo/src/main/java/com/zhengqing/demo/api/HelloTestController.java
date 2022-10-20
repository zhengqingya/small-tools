package com.zhengqing.demo.api;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhengqing.common.base.constant.ServiceConstant;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p> 测试 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/7/16 10:40 上午
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_DEMO + "/api/test")
@Api(tags = "test")
public class HelloTestController {
    @PostMapping("")
    public void test(@RequestBody List<ListObj> params) {
        System.out.println(JSON.toJSONString(params));
    }

    @PostMapping("/test1")
    public void test1(@RequestBody ListObj params) {
        System.err.println(JSON.toJSONString(params));
    }

    @GetMapping("/test2")
    public void test2(@ModelAttribute ListObj params) {
        System.err.println(JSON.toJSONString(params));
    }

    @Data
    static class ListObj {
        private Integer id;
        @JsonProperty("name_xx") // 字段别名
        private String name;
        private List<ListObj> childList;
    }

}

