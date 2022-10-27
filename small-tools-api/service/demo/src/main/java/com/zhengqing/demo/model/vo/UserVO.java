package com.zhengqing.demo.model.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 用户信息 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/10/27 20:40
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 性别
     * {@link com.zhengqing.common.core.enums.UserSexEnum}
     */
    private Byte sex;
    /**
     * 昵称
     */
    private String nickname;

}

