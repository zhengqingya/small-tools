package com.zhengqing.demo.api;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p> 测试 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/7/16 10:40 上午
 */
@RestController
@RequestMapping("/api/test")
@Api(tags = "test")
public class HelloTestController {
    @PostMapping("")
    public void test(@RequestBody List<ListObj> params) {
        System.out.println(JSON.toJSONString(params));
    }
}

@Data
class ListObj {
    private Integer id;
    private String name;
    private List<ListObj> childList;
}

