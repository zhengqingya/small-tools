package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.util.MyBeanUtil;
import com.zhengqing.system.entity.SysRole;
import com.zhengqing.system.mapper.SysRoleMapper;
import com.zhengqing.system.model.dto.SysRoleListDTO;
import com.zhengqing.system.model.dto.SysRoleSaveDTO;
import com.zhengqing.system.model.vo.*;
import com.zhengqing.system.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 系统管理 - 角色管理 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 15:01
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

    @Autowired
    private ISysRoleMenuBtnService sysRoleMenuBtnService;

    @Autowired
    private ISysMenuBtnService sysMenuBtnService;

    @Override
    public IPage<SysRoleListVO> listPage(SysRoleListDTO params) {
        return sysRoleMapper.selectRoles(new Page(), params);
    }

    @Override
    public List<SysRoleListVO> list(SysRoleListDTO params) {
        return sysRoleMapper.selectRoles(params);
    }

    @Override
    public Integer addOrUpdateData(SysRoleSaveDTO params) {
        SysRole sysRole = MyBeanUtil.copyProperties(params, SysRole.class);
        if (params.getRoleId() == null) {
            sysRoleMapper.insert(sysRole);
        } else {
            sysRoleMapper.updateById(sysRole);
        }
        return sysRole.getRoleId();
    }

    @Override
    public SysRolePermissionDetailVO detail(Integer roleId) {
        SysRole sysRole = sysRoleMapper.selectById(roleId);
        SysRolePermissionDetailVO result = MyBeanUtil.copyProperties(sysRole, SysRolePermissionDetailVO.class);
        List<Integer> menuIdList = sysRoleMenuService.getMenuIdsByRoleId(roleId);
        result.setMenuIdList(CollectionUtils.isEmpty(menuIdList) ? Lists.newArrayList() : menuIdList);
        return result;
    }

    @Override
    public SysRoleAllPermissionDetailVO permissionDetail(Integer roleId, Integer systemSource) {
        SysRoleAllPermissionDetailVO permissionDetail =
                MyBeanUtil.copyProperties(detail(roleId), SysRoleAllPermissionDetailVO.class);
        List<SysMenuTreeVO> menTree = sysMenuService.tree(systemSource);
        handleRecursionTree(menTree, roleId);
        permissionDetail.setMenuAndBtnPermissionTree(menTree);
        return permissionDetail;
    }

    /**
     * 递归树，填充角色菜单对应的菜单+按钮权限信息
     *
     * @param menTree: 树数据
     * @return void
     * @author zhengqingya
     * @date 2020/9/11 17:26
     */
    public void handleRecursionTree(List<SysMenuTreeVO> menTree, Integer roleId) {
        if (!CollectionUtils.isEmpty(menTree)) {
            menTree.forEach(menu -> {
                Integer menuId = menu.getMenuId();
                List<SysMenuBtnListVO> btnInfoList = sysMenuBtnService.getBtnInfoListByMenuId(menuId);
                List<Integer> btnIdList = sysRoleMenuBtnService.getPermissionBtnsByRoleIdAndMenuId(roleId, menuId);
                menu.setBtnInfoList(btnInfoList);
                menu.setBtnIdList(btnIdList);
                List<SysMenuTreeVO> menuChildren = menu.getChildren();
                handleRecursionTree(menuChildren, roleId);
            });
        }
    }

    @Override
    public void deleteRoleAndRoleMenu(Integer roleId) {
        // ① 删除该角色下关联的菜单
        sysRoleMenuService.deleteAllMenusByRoleId(roleId);
        // ② 删除该角色下关联的按钮
        sysRoleMenuBtnService.deleteBtnsByRoleId(roleId);
        // ③ 删除角色
        removeById(roleId);
    }

}
