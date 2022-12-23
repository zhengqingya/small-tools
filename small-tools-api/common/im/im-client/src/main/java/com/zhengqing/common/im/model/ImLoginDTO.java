package com.zhengqing.common.im.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> IM 登录 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/12/23 15:08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImLoginDTO {
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 密码
     */
    private String password;
}
