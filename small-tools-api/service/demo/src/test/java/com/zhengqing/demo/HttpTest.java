package com.zhengqing.demo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> http请求测试 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/10/21 20:08
 */
public class HttpTest {

    @Test
    public void test() throws Exception {
        String url = "";
        Map<String, String> headerMap = new HashMap<String, String>(1) {{
            this.put("access-token", "xxx");
        }};
        UserDTO userDTO = UserDTO.builder().id(1L).name("test").build();
        // get请求
        String body = HttpUtil.createGet(url)
                .addHeaders(headerMap)
                .form(BeanUtil.beanToMap(userDTO))
                .body("")
                .execute()
                .body();
        // post请求
        String body2 = HttpUtil.createPost(url)
                .addHeaders(headerMap)
                .body(JSONUtil.toJsonStr(userDTO))
                .form(null)
                .execute()
                .body();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class UserDTO {
        private Long id;
        private String name;
    }

}
