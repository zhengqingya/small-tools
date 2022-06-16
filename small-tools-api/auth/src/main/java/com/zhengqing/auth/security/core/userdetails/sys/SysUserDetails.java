package com.zhengqing.auth.security.core.userdetails.sys;

import cn.hutool.core.collection.CollectionUtil;
import com.zhengqing.auth.enums.PasswordEncoderTypeEnum;
import com.zhengqing.common.core.enums.AuthGrantTypeEnum;
import com.zhengqing.system.model.vo.SysUserPermVO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * <p> 用户信息 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/6/10 16:57
 */
@Data
public class SysUserDetails implements UserDetails {

    /**
     * 扩展字段：用户ID
     */
    private Integer sysUserId;

    /**
     * 认证身份标识
     * {@link AuthGrantTypeEnum}
     */
    private String authenticationIdentity;

    private String username;
    private String password;
    private Boolean enabled;
    private Collection<SimpleGrantedAuthority> authorities;

    /**
     * 系统管理用户
     */
    public SysUserDetails(SysUserPermVO user) {
        this.setSysUserId(user.getUserId());
        this.setUsername(user.getUsername());
        this.setPassword(PasswordEncoderTypeEnum.BCRYPT.getPrefix() + user.getPassword());
        this.setEnabled(true);
        List<String> roleList = user.getRoleCodeList();
        if (CollectionUtil.isNotEmpty(roleList)) {
            this.authorities = new ArrayList<>();
            roleList.forEach(role -> this.authorities.add(new SimpleGrantedAuthority(role)));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
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
