package com.zhengqing.demo.model.vo;


import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 测试
 *
 * @author zhengqingya
 * @date 2022/09/01
 */
@NoArgsConstructor
@Data
public class TestVO {
    private User user;


    @NoArgsConstructor
    @lombok.Data
    public static class User {
        private String avatar;
        private String city;
        private String country;
        private String description;
        private String errorCode;
        private String gender;
        private String nickname;
        private String openId;
        private String province;
    }

}
