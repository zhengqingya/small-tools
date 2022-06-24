package com.zhengqing.common.security.enums;

import com.zhengqing.common.base.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> 客户端ID </p>
 *
 * @author zhengqingya
 * @description 和`t_sys_oauth_client`表中的客户端ID对应
 * {@link com.zhengqing.system.entity.SysOauthClient}
 * @date 2022/6/10 15:18
 */
@Getter
@AllArgsConstructor
public enum AuthClientIdEnum implements IBaseEnum<String> {

    WEB("web", "系统管理web"),
    APP("app", "移动端(H5/Android/IOS)"),
    MINI("mini", "小程序端(微信小程序...)");

    private String value;
    private String desc;

}
