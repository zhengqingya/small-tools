package com.zhengqing.auth.security.core.userdetails.sys;

import com.zhengqing.common.base.enums.ApiResultCodeEnum;
import com.zhengqing.common.base.http.ApiResult;
import com.zhengqing.system.feign.ISysUserFeignApi;
import com.zhengqing.system.model.vo.SysUserPermVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * <p> 加载用户信息 - B端 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/6/10 16:58
 */
@Slf4j
@Service("sysUserDetailsService")
@RequiredArgsConstructor
public class SysUserDetailsServiceImpl implements UserDetailsService {

    private final ISysUserFeignApi sysUserFeignApi;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserDetails userDetails = null;
        ApiResult<SysUserPermVO> result = this.sysUserFeignApi.getUserInfoByUsername(username);
        if (result.checkIsSuccess()) {
            SysUserPermVO user = result.getData();
            if (user != null) {
                userDetails = new SysUserDetails(user);
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
