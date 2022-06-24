package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.core.enums.UserSexEnum;
import com.zhengqing.common.core.util.DesUtil;
import com.zhengqing.system.entity.SysRole;
import com.zhengqing.system.entity.SysUser;
import com.zhengqing.system.enums.SysMenuTypeEnum;
import com.zhengqing.system.enums.SysUserReRoleEnum;
import com.zhengqing.system.mapper.SysUserMapper;
import com.zhengqing.system.model.dto.*;
import com.zhengqing.system.model.vo.*;
import com.zhengqing.system.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
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
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final SysUserMapper sysUserMapper;

    private final ISysRoleService sysRoleService;

    private final ISysUserRoleService sysUserRoleService;

    private final PasswordEncoder passwordEncoder;

    private final ISysMenuService sysMenuService;

    private final ISysRolePermissionService sysRolePermissionService;

    private final ISysRoleMenuService sysRoleMenuService;


    @Override
    public IPage<SysUserDetailVO> listPage(SysUserListDTO params) {
        IPage<SysUserDetailVO> result = this.sysUserMapper.selectUsers(new Page<>(), params);
        this.handleUserList(result.getRecords());
        return result;
    }

    @Override
    public List<SysUserDetailVO> list(SysUserListDTO params) {
        List<SysUserDetailVO> userList = this.sysUserMapper.selectUsers(params);
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
                    List<SysRole> sysRoleList = this.sysRoleService.listByIds(roleIdsX);
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
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdateData(SysUserSaveDTO params) {
        Integer userId = params.getUserId();
        String username = params.getUsername();
        String nickname = params.getNickname();
        Byte sex = params.getSex();
        String phone = params.getPhone();
        String email = params.getEmail();
        String avatar = params.getAvatar();

        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setUsername(username);
        user.setNickname(nickname);
        user.setSexEnum(UserSexEnum.getEnum(sex));
        user.setPhone(phone);
        user.setEmail(email);
        user.setAvatarUrl(avatar);

        if (userId == null) {
//            user.setSalt(AppConstant.SALT);
//            user.setPassword(PasswordUtil.encodePassword(AppConstant.DEFAULT_PASSWORD, AppConstant.SALT));
            user.setPassword(this.passwordEncoder.encode(AppConstant.DEFAULT_PASSWORD));
            user.insert();

            // 塞个token信息
//            UserTokenInfo userTokenInfo = UserTokenInfo.buildUser(JSONObject.parseObject(JSONObject.toJSONString(user), Map.class));
//            String jwtToken = JwtUtil.buildJWT(userTokenInfo);
//            user.setToken(jwtToken);
//            user.updateById();

            // 绑定角色信息
            SysUserRoleSaveDTO userRoleSaveDTO = new SysUserRoleSaveDTO();
            userRoleSaveDTO.setUserId(user.getUserId());
            userRoleSaveDTO.setRoleIdList(Lists.newArrayList(SysUserReRoleEnum.凡人.getRoleId()));
            this.sysUserRoleService.addOrUpdateData(userRoleSaveDTO);
        } else {
            user.updateById();
        }
        return user.getUserId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Integer userId) {
        if (AppConstant.SYSTEM_SUPER_ADMIN_USER_ID.equals(userId)) {
            throw new MyException("您没有权限删除超级管理员！");
        }
        // ① 删除关联角色
        this.sysUserRoleService.removeById(userId);
        // ② 删除关联项目 FIXME
        // cgProjectService.deleteDataByUserId(userId);
        // ③ 删除该用户
        this.removeById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(SysUserUpdatePasswordDTO params) {
        String password = DesUtil.decrypt(params.getPassword(), AppConstant.DES_KEY);
        SysUser userInfo = this.sysUserMapper.selectById(params.getUserId());
//        String salt = userInfo.getSalt();
//        boolean isValid = PasswordUtil.isValidPassword(password, userInfo.getPassword(), salt);
        boolean isValid = this.passwordEncoder.encode(password) == userInfo.getPassword();
        // ① 校验原始密码是否正确
        if (!isValid) {
            throw new MyException(AppConstant.WRONG_OLD_PASSWORD);
        }
        // ② 修改密码
//        String newPassword = DesUtil.decrypt(params.getNewPassword(), AppConstant.DES_KEY);
//        userInfo.setPassword(PasswordUtil.encodePassword(newPassword, salt));
        userInfo.setPassword(this.passwordEncoder.encode(params.getNewPassword()));
        this.sysUserMapper.updateById(userInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Integer userId) {
        SysUser sysUser = this.sysUserMapper.selectById(userId);
        sysUser.setPassword(this.passwordEncoder.encode(AppConstant.DEFAULT_PASSWORD));
        this.sysUserMapper.updateById(sysUser);
    }

    @Override
    public SysUserDetailVO getUserInfoByUserId(Integer userId) {
        return this.sysUserMapper.selectUserByUserId(userId);
    }

    @Override
    public SysUserPermVO getUserPerm(SysUserPermDTO params) {
        Assert.isFalse(params.getUserId() == null && StringUtils.isBlank(params.getUsername()),
                "查询用户条件丢失！");
        // 1、拿到基础用户信息
        SysUserPermVO userPerm = this.sysUserMapper.selectUserPerm(params);
        Assert.notNull(userPerm, "用户不存在！");
        // 2、角色信息
        List<Integer> roleIdList = this.sysUserRoleService.listRoleId(userPerm.getUserId());
        Assert.notEmpty(roleIdList, "请联系管理员为其分配角色!");
        List<SysRole> roleList = this.sysRoleService.listByIds(roleIdList);
        Assert.notEmpty(roleList, "请联系管理员为其分配角色!");
        StringJoiner roleNameSj = new StringJoiner(",", "[", "]");
        roleList.forEach(item -> roleNameSj.add(item.getName()));
        userPerm.setRoleNames(roleNameSj.toString());
        List<String> roleCodeList = roleList.stream().map(e -> e.getCode()).collect(Collectors.toList());
        userPerm.setRoleCodeList(roleCodeList);

        // 3、权限
        // 角色可访问的菜单ID
        List<Integer> menuIdList = this.sysRoleMenuService.getMenuIdsByRoleIds(roleIdList);
        // 所有菜单
        List<SysMenuTreeVO> menuTreeList = this.sysMenuService.tree();
        // 所有按钮
        List<SysRoleMenuBtnListVO> btnList = this.sysRolePermissionService.listRoleMenuBtn();
        // 用户关联的权限
        List<SysMenuTreeVO> permTreeList = this.getUserPremTreeList(menuTreeList, menuIdList, roleIdList, btnList);
        userPerm.setPermissionTreeList(permTreeList);
        return userPerm;
    }


    /**
     * 获取用户菜单权限树
     *
     * @param menuTreeList 菜单树
     * @param menuIdList   用户所拥有的菜单权限ids
     * @param roleIdList   用户所拥有的角色ids
     * @param btnList      用户所拥有的菜单按钮权限
     * @return 过滤后的用户关联的权限菜单树
     * @author zhengqingya
     * @date 2020/9/11 14:34
     */
    private List<SysMenuTreeVO> getUserPremTreeList(List<SysMenuTreeVO> menuTreeList,
                                                    List<Integer> menuIdList,
                                                    List<Integer> roleIdList,
                                                    List<SysRoleMenuBtnListVO> btnList) {
        List<SysMenuTreeVO> resultList = Lists.newArrayList();
        for (SysMenuTreeVO menu : menuTreeList) {
            Integer menuId = menu.getMenuId();
            if (menuIdList.contains(menuId) && SysMenuTypeEnum.菜单.getType().equals(menu.getType())) {
                List<SysMenuTreeVO> menuChildList = menu.getChildren();
                if (!CollectionUtils.isEmpty(menuChildList)) {
                    menu.setChildren(this.getUserPremTreeList(menuChildList, menuIdList, roleIdList, btnList));
                }
                List<String> btnPermList = this.getUserBtnPermList(menuId, roleIdList, btnList);
                menu.setMeta(SysUserBtnVO.builder()
                        .title(menu.getTitle())
                        .icon(menu.getIcon())
                        .btnPermList(btnPermList)
                        .build());
                resultList.add(menu);
            }
        }
        return resultList;
    }

    /**
     * 获取用户按钮权限标识
     *
     * @param menuId     菜单id
     * @param roleIdList 用户所拥有的角色ids
     * @param btnList    按钮权限
     * @return 按钮权限标识
     * @author zhengqingya
     * @date 2020/9/11 14:36
     */
    private List<String> getUserBtnPermList(Integer menuId,
                                            List<Integer> roleIdList,
                                            List<SysRoleMenuBtnListVO> btnList) {
        if (CollectionUtils.isEmpty(btnList)) {
            return Lists.newArrayList();
        }
        Set<String> btnSet = new HashSet<>();
        btnList.forEach(btn -> {
            if (menuId.equals(btn.getMenuId()) && roleIdList.contains(btn.getRoleId())) {
                btnSet.add(btn.getBtnVal());
            }
        });
        return new ArrayList<>(btnSet);
    }

}
