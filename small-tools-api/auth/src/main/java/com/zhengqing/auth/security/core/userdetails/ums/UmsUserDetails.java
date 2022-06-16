package com.zhengqing.auth.security.core.userdetails.ums;

import com.zhengqing.common.core.enums.AuthGrantTypeEnum;
import com.zhengqing.ums.model.vo.UmsUserVO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;


/**
 * <p> 用户信息 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/6/10 16:50
 */
@Data
public class UmsUserDetails implements UserDetails {

    private Long umsUserId;
    private String username;
    private Boolean enabled;

    /**
     * 认证身份标识
     * {@link AuthGrantTypeEnum}
     */
    private String authenticationIdentity;

    public UmsUserDetails(UmsUserVO umsUserVO) {
        this.setUmsUserId(umsUserVO.getId());
        this.setUsername(umsUserVO.getOpenid());
        this.setEnabled(true);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new HashSet<>();
        return collection;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
