package com.zhengqing.auth.security.core.userdetails.ums;

import com.zhengqing.common.base.enums.ApiResultCodeEnum;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.common.security.enums.AuthGrantTypeEnum;
import com.zhengqing.ums.feign.IUmsUserFeignApi;
import com.zhengqing.ums.model.dto.UmsUserDTO;
import com.zhengqing.ums.model.vo.UmsUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <p> 加载用户信息 - C端 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/6/10 16:57
 */
@Service("umsUserDetailsService")
@RequiredArgsConstructor
public class UmsUserDetailsServiceImpl implements UserDetailsService {

    private final IUmsUserFeignApi umsUserFeignApi;

    /**
     * 认证方式 -- 用户名
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        return this.baseLoadUser(AuthGrantTypeEnum.USERNAME, UmsUserDTO.builder().username(username).build());
    }

    /**
     * 认证方式 -- 手机号码
     */
    public UserDetails loadUserByPhone(String phone) {
        return this.baseLoadUser(AuthGrantTypeEnum.PHONE, UmsUserDTO.builder().phone(phone).build());
    }

    /**
     * 认证方式 -- 微信openid
     */
    public UserDetails loadUserByOpenId(String openId) {
        return this.baseLoadUser(AuthGrantTypeEnum.OPENID, UmsUserDTO.builder().openid(openId).build());
    }

    /**
     * 根据不同的方式进行认证
     *
     * @param authGrantTypeEnum 认证身份标识枚举
     * @param params            参数参数
     * @return 用户详情
     * @author zhengqingya
     * @date 2022/6/16 12:48
     */
    private UserDetails baseLoadUser(AuthGrantTypeEnum authGrantTypeEnum, UmsUserDTO params) {
        UmsUserDetails userDetails = null;
        ApiResult<UmsUserVO> result = this.umsUserFeignApi.getUserInfo(params);

        if (result.checkIsSuccess()) {
            UmsUserVO member = result.getData();
            if (null != member) {
                userDetails = new UmsUserDetails(member);
                userDetails.setAuthenticationIdentity(authGrantTypeEnum.getValue());
            }
        }
        if (userDetails == null) {
            throw new UsernameNotFoundException(ApiResultCodeEnum.USER_NOT_EXIST.getDesc());
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return userDetails;
    }

}
