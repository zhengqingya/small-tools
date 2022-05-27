package com.zhengqing.system.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.core.constant.AppConstant;
import com.zhengqing.common.base.context.ContextHandler;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.util.MyBeanUtil;
import com.zhengqing.system.entity.SysRole;
import com.zhengqing.system.entity.SysUserRole;
import com.zhengqing.system.enums.SysCacheTypeEnum;
import com.zhengqing.system.enums.SysMenuTypeEnum;
import com.zhengqing.system.model.vo.*;
import com.zhengqing.system.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统管理 - 权限系列缓存 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 18:51
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysPermissionServiceImpl implements ISysPermissionService {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysRoleMenuBtnService sysRoleMenuBtnService;

    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Override
    public void updateCacheSysPermission(SysCacheTypeEnum cacheType, Integer userId) {
        // TODO 后期将权限还是弄成查库 或者 优化缓存权限？？？
        switch (cacheType) {
            // case 系统菜单:
            // menuTree();
            // // 菜单改变随之按钮缓存也相应改变
            // List<SysUser> sysUserList = sysUserMapper.selectList(null);
            // sysUserList.forEach(e -> {
            // Integer userIdX = e.getUserId();
            // getPermissionByUserId(userIdX);
            // updateUserInfoByUserId(userIdX);
            // });
            // break;
            // case 个人菜单按钮权限:
            // this.getPermissionByUserId(userId);
            // break;
            // case 个人所有信息:
            // this.updateUserInfoByUserId(userId);
            // break;
            // case 系统所有人菜单按钮权限:
            // this.menuTree();
            // // 菜单改变随之按钮缓存也相应改变
            // List<SysUser> sysUserListX = sysUserService.list();
            // sysUserListX.forEach(e -> {
            // Integer userIdX = e.getUserId();
            // this.getPermissionByUserId(userIdX);
            // this.updateUserInfoByUserId(userIdX);
            // });
            // break;
            // default:
            // break;
        }
    }

    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ 菜单权限 ↓↓↓↓↓↓ ============================
    // ===============================================================================

    /**
     * 菜单权限
     *
     * @return 菜单树列表
     */
    @Override
    public List<SysMenuTreeVO> menuTree(Integer systemSource) {
        // ①、拿到所有菜单
        List<SysMenuTreeVO> allMenuList = this.sysMenuService.selectMenuTree(systemSource);
        // ②、准备一个空的父菜单集合
        List<SysMenuTreeVO> parentMenuList = Lists.newArrayList();
        // ③、遍历子菜单 -> 进行对父菜单的设置
        for (SysMenuTreeVO parentMenu : allMenuList) {
            if (parentMenu.getParentId().equals(AppConstant.MENU_PARENT_ID)) {
                parentMenuList.add(parentMenu);
            }
        }
        // ④、遍历出父菜单对应的子菜单
        for (SysMenuTreeVO parent : parentMenuList) {
            List<SysMenuTreeVO> child = this.getChildMenu(parent.getMenuId(), allMenuList);
            parent.setChildren(child);
        }
        // RedisUtil.set(Constants.CACHE_SYS_MENU_TREE, JSON.toJSONString(parentMenuList));
        // log.info("更新菜单权限缓存成功!");
        return parentMenuList;
    }

    /**
     * 递归子菜单
     *
     * @param parentMenuId: 父菜单id
     * @param allMenuList:  所有菜单
     * @return 菜单树列表
     * @author zhengqingya
     * @date 2020/9/10 20:56
     */
    private List<SysMenuTreeVO> getChildMenu(Integer parentMenuId, List<SysMenuTreeVO> allMenuList) {
        // ⑤、存放子菜单的集合
        List<SysMenuTreeVO> childMenuList = Lists.newArrayList();
        for (SysMenuTreeVO menu : allMenuList) {
            if (menu.getParentId().equals(parentMenuId)) {
                childMenuList.add(menu);
            }
        }
        // ⑥、递归
        for (SysMenuTreeVO userGroup : childMenuList) {
            userGroup.setChildren(this.getChildMenu(userGroup.getMenuId(), allMenuList));
        }
        // if (childMenuList.size() == 0) {
        // return null;
        // }
        return childMenuList;
    }

    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ 菜单+按钮权限 ↓↓↓↓↓↓ ============================
    // ===============================================================================

    @Override
    public SysPermissionVO getPermissionByUserId(Integer userId, Integer systemSource) {
        log.debug("=========== 用户ID为：{} ===========", userId);
        // 通过用户ID取得用户角色
        SysUserRole sysUserRole = this.sysUserRoleService.getById(userId);
        if (sysUserRole == null) {
            throw new MyException(AppConstant.NO_PERMISSION);
        }
        String roleIds = sysUserRole.getRoleIds();
        if (StringUtils.isBlank(roleIds)) {
            throw new MyException(AppConstant.NO_PERMISSION);
        }
        List<Integer> roleIdList =
                Arrays.stream(roleIds.split(",")).map(e -> Integer.parseInt(e.trim())).collect(Collectors.toList());
        List<SysRole> sysRoleList = this.sysRoleService.listByIds(roleIdList);
        if (CollectionUtils.isEmpty(sysRoleList)) {
            return null;
        }

        SysPermissionVO permissionVO = this.getRoleAndMenuInfo(sysRoleList, roleIdList);
        List<SysMenuTreeVO> menuBtnPermissionTreeList =
                this.menuAndBtnPermissionTree(permissionVO.getMenuIdList(), roleIdList, systemSource);
        permissionVO.setMenuAndBtnPermissionTree(menuBtnPermissionTreeList);
        // RedisUtil.set(Constants.CACHE_SYS_PERMISSION_PREFIX + userId, JSON.toJSONString(permissionVO));
        // log.info("更新`{}`菜单+按钮权限缓存成功!", userId);
        return permissionVO;
    }

    /**
     * 返回角色+菜单权限信息
     *
     * @param sysRoleList: 角色信息
     * @param roleIdList:  角色ids
     * @return 权限信息
     */
    private SysPermissionVO getRoleAndMenuInfo(List<SysRole> sysRoleList, List<Integer> roleIdList) {
        StringBuilder roleNames = new StringBuilder();
        StringBuilder roleCodes = new StringBuilder();
        sysRoleList.forEach(role -> {
            // 角色名
            roleNames.append("【");
            roleNames.append(role.getName());
            roleNames.append("】");
            // 角色编号
            roleCodes.append("【");
            roleCodes.append(role.getCode());
            roleCodes.append("】");
        });
        SysPermissionVO result = new SysPermissionVO();
        result.setRoleList(MyBeanUtil.copyList(sysRoleList, SysRoleListVO.class));
        result.setRoleNames(roleNames.toString());
        result.setRoleCodes(roleCodes.toString());
        result.setRoleIdList(roleIdList);
        result.setMenuIdList(this.sysRoleMenuService.getMenuIdsByRoleIds(roleIdList));
        return result;
    }

    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ 菜单+按钮权限 ↓↓↓↓↓↓ ============================
    // ===============================================================================

    /**
     * 查询菜单+其拥有的按钮权限
     *
     * @param menuIdList:   菜单权限ids
     * @param roleIdList:   角色权限ids
     * @param systemSource: 系统来源
     * @return 带权限的菜单树
     * @author zhengqingya
     * @date 2020/9/11 14:32
     */
    private List<SysMenuTreeVO> menuAndBtnPermissionTree(List<Integer> menuIdList, List<Integer> roleIdList,
                                                         Integer systemSource) {
        // 【法一】： 从redis缓存中取数据...
        // List<SysMenuTreeVO> menuTreeList =
        // JSONArray.parseArray(RedisUtil.get(Constants.CACHE_SYS_MENU_TREE), SysMenuTreeVO.class);

        // 【法二】：直接查
        List<SysMenuTreeVO> menuTreeList = this.menuTree(systemSource);
        List<SysRoleMenuBtnListVO> btnList = this.sysRoleMenuBtnService.getAllRoleMenuBtns();
        List<SysMenuTreeVO> menuBtnTreeList = this.filterTree(menuTreeList, menuIdList, roleIdList, btnList);
        return menuBtnTreeList;
    }

    /**
     * 过滤用户菜单树
     *
     * @param menuTreeList: 菜单树
     * @param menuIdList:   用户所拥有的菜单权限ids
     * @param roleIdList:   用户所拥有的角色ids
     * @param btnList:      用户所拥有的菜单按钮权限
     * @return 过滤后的用户关联的权限菜单树
     * @author zhengqingya
     * @date 2020/9/11 14:34
     */
    private List<SysMenuTreeVO> filterTree(List<SysMenuTreeVO> menuTreeList, List<Integer> menuIdList,
                                           List<Integer> roleIdList, List<SysRoleMenuBtnListVO> btnList) {
        List<SysMenuTreeVO> result = Lists.newArrayList();
        for (SysMenuTreeVO menu : menuTreeList) {
            Integer menuId = menu.getMenuId();
            if (menuIdList.contains(menuId) && SysMenuTypeEnum.菜单.getType().equals(menu.getType())) {
                List<SysMenuTreeVO> menuChildren = menu.getChildren();
                if (!CollectionUtils.isEmpty(menuChildren)) {
                    menu.setChildren(this.filterTree(menuChildren, menuIdList, roleIdList, btnList));
                }

                List<String> btnPermissions = this.getBtnsByRoleIdMenuId(menuId, roleIdList, btnList);
                Map<String, Object> metaMap = Maps.newHashMap();
                metaMap.put("title", menu.getTitle());
                metaMap.put("icon", menu.getIcon());
                metaMap.put("btnPermissions", btnPermissions);
                menu.setMeta(metaMap);
                result.add(menu);
            }
        }
        return result;
    }

    /**
     * 根据角色菜单勾选按钮权限
     *
     * @param menuId:     菜单id
     * @param roleIdList: 用户所拥有的角色ids
     * @param btnList:    按钮权限
     * @return 按钮权限
     * @author zhengqingya
     * @date 2020/9/11 14:36
     */
    private List<String> getBtnsByRoleIdMenuId(Integer menuId, List<Integer> roleIdList,
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
        // log.debug("菜单id:【{}】 所拥有按钮权限:【{}】", menuId, btnSet);
        return new ArrayList<>(btnSet);
    }

    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ 个人所有信息 ↓↓↓↓↓↓ ============================
    // ===============================================================================

    private void updateUserInfoByUserId(Integer userId) {
        SysUserDetailVO sysUserDetailVO = this.sysUserService.getUserInfoByUserId(userId);
        SysUserVO currentUserInfo = MyBeanUtil.copyProperties(sysUserDetailVO, SysUserVO.class);
        // 【法一】：从redis缓存中取权限数据...
        // SysPermissionVO permissionVO = JSONObject
        // .parseObject(RedisUtil.get(Constants.CACHE_SYS_PERMISSION_PREFIX + userId), SysPermissionVO.class);
        // 【法二】：直接查
        SysPermissionVO permissionVO = this.getPermissionByUserId(userId, null);

        // FIXME SecurityUser securityUser = new SecurityUser(currentUserInfo, permissionVO);
        // RedisUtil.set(Constants.CACHE_SYS_USER_INFO_PREFIX + userId, JSON.toJSONString(securityUser));
        // log.info("更新`{}`用户所有信息缓存成功!", userId);

        // 判断是否为当前登录人自己 FIXME
        // if (userId.equals(((SecurityUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        // .getCurrentUserInfo().getUserId())) {
        // UsernamePasswordAuthenticationToken authentication =
        // new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
        // // 全局注入角色权限信息和登录用户基本信息
        // SecurityContextHolder.getContext().setAuthentication(authentication);
        // }
    }

    @Override
    public SysUserVO getUserInfoByUserId(Integer userId, Integer systemSource) {
        // 【法一】： 从redis缓存中取数据...
        // String key = Constants.CACHE_SYS_USER_INFO_PREFIX + userId;
        // SecurityUser user = JSONObject.parseObject(RedisUtil.get(key), SecurityUser.class);

        // 【法二】：直接查
        SysUserDetailVO sysUserDetailVO = this.sysUserService.getUserInfoByUserId(userId);
        SysPermissionVO permissionVO = this.getPermissionByUserId(userId, systemSource);
        SysUserVO currentUserInfo = MyBeanUtil.copyProperties(sysUserDetailVO, SysUserVO.class);
        currentUserInfo.setRoleNames(permissionVO.getRoleNames());
        currentUserInfo.setMenuAndBtnPermissionList(permissionVO.getMenuAndBtnPermissionTree());
        return currentUserInfo;
    }

    @Override
    public SysUserVO appGetCurrentUserInfoAndPermission(Integer systemSource) {
        // 【法一】： 从redis缓存中取数据...
        // SysUserVO user = JSONObject
        // .parseObject(RedisUtil.get(Constants.CACHE_SYS_USER_INFO_PREFIX + appGetUserId()), SecurityUser.class)
        // .getCurrentUserInfo();

        // 【法二】：直接查
        return this.getUserInfoByUserId(ContextHandler.getUserId(), systemSource);
    }

}
