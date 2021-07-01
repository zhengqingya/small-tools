package com.zhengqing.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.exception.MyException;
import com.zhengqing.common.model.bo.UserTokenInfo;
import com.zhengqing.common.util.DESUtil;
import com.zhengqing.common.util.JwtUtil;
import com.zhengqing.system.entity.SysRole;
import com.zhengqing.system.entity.SysUser;
import com.zhengqing.system.enums.SysUserReRoleEnum;
import com.zhengqing.system.mapper.SysUserMapper;
import com.zhengqing.system.model.dto.*;
import com.zhengqing.system.model.vo.SysPermissionVO;
import com.zhengqing.system.model.vo.SysUserDetailVO;
import com.zhengqing.system.model.vo.SysUserVO;
import com.zhengqing.system.service.ISysPermissionService;
import com.zhengqing.system.service.ISysRoleService;
import com.zhengqing.system.service.ISysUserRoleService;
import com.zhengqing.system.service.ISysUserService;
import com.zhengqing.system.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户管理 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:33
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private ISysPermissionService sysPermissionService;

    // @Autowired
    // private ICgProjectService cgProjectService;

    @Override
    public IPage<SysUserDetailVO> listPage(SysUserListDTO params) {
        IPage<SysUserDetailVO> result = sysUserMapper.selectUsers(new Page<>(), params);
        handleUserList(result.getRecords());
        return result;
    }

    @Override
    public List<SysUserDetailVO> list(SysUserListDTO params) {
        List<SysUserDetailVO> userList = sysUserMapper.selectUsers(params);
        // handleUserList(userList);
        return userList;
    }

    public void handleUserList(List<SysUserDetailVO> userList) {
        userList.forEach(userInfo -> {
            // 拼接用户角色名
            String roleIds = userInfo.getRoleIds();
            if (StringUtils.isNotBlank(roleIds)) {
                List<Integer> roleIdsX =
                        Arrays.stream(roleIds.split(",")).map(e -> Integer.parseInt(e.trim())).collect(Collectors.toList());
                StringBuilder roleNames = new StringBuilder();
                if (!CollectionUtils.isEmpty(roleIdsX)) {
                    List<SysRole> sysRoleList = sysRoleService.listByIds(roleIdsX);
                    sysRoleList.forEach(role -> {
                        roleNames.append("[");
                        roleNames.append(role.getName());
                        roleNames.append("] ");
                    });
                    userInfo.setRoleNames(roleNames.toString());
                }
            }
        });
    }

    @Override
    public Integer addOrUpdateData(SysUserSaveDTO params) {
        Integer userId = params.getUserId();
        String username = params.getUsername();
        String nickname = params.getNickname();
        Integer sex = params.getSex();
        String phone = params.getPhone();
        String email = params.getEmail();
        String avatar = params.getAvatar();

        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setUsername(username);
        user.setNickname(nickname);
        user.setSex(sex);
        user.setPhone(phone);
        user.setEmail(email);
        user.setAvatar(avatar);

        if (userId == null) {
            user.setSalt(AppConstant.SALT);
            user.setPassword(PasswordUtil.encodePassword(AppConstant.DEFAULT_PASSWORD, AppConstant.SALT));
            user.insert();

            // 塞个token信息
            UserTokenInfo userTokenInfo =
                    UserTokenInfo.buildUser(JSONObject.parseObject(JSONObject.toJSONString(user), Map.class));
            String jwtToken = JwtUtil.buildJWT(userTokenInfo);
            user.setToken(jwtToken);
            user.updateById();

            // 绑定角色信息
            SysUserRoleSaveDTO userRoleSaveDTO = new SysUserRoleSaveDTO();
            userRoleSaveDTO.setUserId(user.getUserId());
            userRoleSaveDTO.setRoleIdList(Lists.newArrayList(SysUserReRoleEnum.凡人.getRoleId()));
            sysUserRoleService.addOrUpdateData(userRoleSaveDTO);
        } else {
            user.updateById();
        }
        return user.getUserId();
    }

    @Override
    public void deleteUser(Integer userId) {
        if (AppConstant.SYSTEM_SUPER_ADMIN_USER_ID.equals(userId)) {
            throw new MyException("您没有权限删除超级管理员！");
        }
        // ① 删除关联角色
        sysUserRoleService.removeById(userId);
        // ② 删除关联项目 FIXME
        // cgProjectService.deleteDataByUserId(userId);
        // ③ 删除该用户
        this.removeById(userId);
    }

    @Override
    public void updatePassword(SysUserUpdatePasswordDTO params) {
        String password = DESUtil.decryption(params.getPassword(), AppConstant.DES_KEY);
        SysUser userInfo = sysUserMapper.selectById(params.getUserId());
        String salt = userInfo.getSalt();
        boolean isValid = PasswordUtil.isValidPassword(password, userInfo.getPassword(), salt);
        // ① 校验原始密码是否正确
        if (!isValid) {
            throw new MyException(AppConstant.WRONG_OLD_PASSWORD);
        }
        // ② 修改密码
        String newPassword = DESUtil.decryption(params.getNewPassword(), AppConstant.DES_KEY);
        userInfo.setPassword(PasswordUtil.encodePassword(newPassword, salt));
        sysUserMapper.updateById(userInfo);
    }

    @Override
    public void resetPassword(Integer userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        sysUser.setPassword(PasswordUtil.encodePassword(AppConstant.DEFAULT_PASSWORD, sysUser.getSalt()));
        sysUserMapper.updateById(sysUser);
    }

    @Override
    public String login(SysUserLoginDTO params) {
        String username = params.getUsername();
        // des解密
        String password = DESUtil.decryption(params.getPassword(), AppConstant.DES_KEY);

        SysUserVO currentUserInfo = this.getUserInfoByUsername(username);
        if (Objects.isNull(currentUserInfo)) {
            throw new MyException(AppConstant.NO_USERNAME);
        }
        // 下面获取其相关权限
        Integer userId = currentUserInfo.getUserId();
        // 获取角色+对应的菜单+所拥有的按钮权限
        SysPermissionVO permissionVO = sysPermissionService.getPermissionByUserId(userId, null);
        if (permissionVO == null) {
            throw new MyException(AppConstant.NO_PERMISSION);
        }

        // 验证密码
        boolean isValid =
                PasswordUtil.isValidPassword(password, currentUserInfo.getPassword(), currentUserInfo.getSalt());
        if (!isValid) {
            throw new MyException(AppConstant.WRONG_PASSWORD);
        }

        UserTokenInfo userTokenInfo =
                UserTokenInfo.buildUser(JSONObject.parseObject(JSONObject.toJSONString(currentUserInfo), Map.class));
        String jwtToken = JwtUtil.buildJWT(userTokenInfo);
        // currentUserInfo.setToken(jwtToken);
        return jwtToken;
    }

    @Override
    public SysUserDetailVO getUserInfoByUserId(Integer userId) {
        return sysUserMapper.selectUserByUserId(userId);
    }

    @Override
    public SysUserVO getUserInfoByUsername(String username) {
        return sysUserMapper.selectUserByUsername(username);
    }

    @Override
    public List<SysUserVO> getUserInfoByUserIds(List<Integer> userIdList) {
        return sysUserMapper.selectUserInfoByUserIds(userIdList);
    }

}
