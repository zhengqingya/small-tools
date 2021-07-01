package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.system.entity.SysRoleMenu;
import com.zhengqing.system.mapper.SysRoleMenuMapper;
import com.zhengqing.system.model.dto.SysRoleMenuBtnSaveDTO;
import com.zhengqing.system.model.dto.SysRoleMenuSaveDTO;
import com.zhengqing.system.model.dto.SysRolePermissionSaveDTO;
import com.zhengqing.system.model.vo.SysMenuTreeVO;
import com.zhengqing.system.service.ISysRoleMenuBtnService;
import com.zhengqing.system.service.ISysRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 系统管理 - 角色菜单关联服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:31
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private ISysRoleMenuBtnService sysRoleMenuBtnService;

    @Override
    public List<Integer> getMenuIdsByRoleId(Integer roleId) {
        return sysRoleMenuMapper.selectMenuIdsByRoleId(roleId);
    }

    @Override
    public List<Integer> getMenuIdsByRoleIds(List<Integer> roleIdList) {
        return sysRoleMenuMapper.selectMenuIdsByRoleIds(roleIdList);
    }

    @Override
    public void saveRoleMenuIds(SysRoleMenuSaveDTO params) {
        Integer roleId = params.getRoleId();
        sysRoleMenuMapper.deleteAllMenusByRoleId(roleId);
        List<Integer> menuIdList = params.getMenuIdList();
        if (!CollectionUtils.isEmpty(menuIdList)) {
            List<SysRoleMenu> roleMenuList = Lists.newArrayList();
            menuIdList.forEach(menuId -> {
                SysRoleMenu roleMenuItem = new SysRoleMenu();
                roleMenuItem.setRoleId(roleId);
                roleMenuItem.setMenuId(menuId);
                roleMenuList.add(roleMenuItem);
            });
            // sysRoleMenuMapper.batchInsertRoleMenuIds(roleId, menuIdList);
            saveBatch(roleMenuList);
        }
    }

    @Override
    public void saveRolePermission(SysRolePermissionSaveDTO params) {
        Integer roleId = params.getRoleId();
        // 1、先保存角色关联的菜单权限信息
        sysRoleMenuMapper.deleteAllMenusByRoleId(roleId);
        List<Integer> menuIdList = params.getMenuIdList();
        if (!CollectionUtils.isEmpty(menuIdList)) {
            List<SysRoleMenu> roleMenuList = Lists.newArrayList();
            menuIdList.forEach(menuId -> {
                SysRoleMenu roleMenuItem = new SysRoleMenu();
                roleMenuItem.setRoleId(roleId);
                roleMenuItem.setMenuId(menuId);
                roleMenuList.add(roleMenuItem);
            });
            saveBatch(roleMenuList);
        }

        // 2、再保存角色关联的按钮权限信息
        handleMenuAndBtnPermissionTree(roleId, params.getMenuAndBtnPermissionTree());
    }

    /**
     * 递归处理菜单+按钮权限树信息数据 -> 保存按钮权限数据
     *
     * @param roleId:                   角色id
     * @param menuAndBtnPermissionTree: 权限树信息
     * @return: void
     * @author zhengqingya
     * @date 2020/9/14 11:24
     */
    public void handleMenuAndBtnPermissionTree(Integer roleId, List<SysMenuTreeVO> menuAndBtnPermissionTree) {
        if (!CollectionUtils.isEmpty(menuAndBtnPermissionTree)) {
            menuAndBtnPermissionTree.forEach(menu -> {
                Integer menuId = menu.getMenuId();
                // ① 先删除按钮权限数据
                sysRoleMenuBtnService.deleteBtnsByRoleIdAndMenuId(roleId, menuId);

                // ② 保存按钮权限数据
                List<Integer> btnIdList = menu.getBtnIdList();
                if (!CollectionUtils.isEmpty(btnIdList)) {
                    SysRoleMenuBtnSaveDTO btnSaveItem = new SysRoleMenuBtnSaveDTO();
                    btnSaveItem.setRoleId(roleId);
                    btnSaveItem.setMenuId(menuId);
                    btnSaveItem.setBtnIdList(btnIdList);
                    sysRoleMenuBtnService.saveRoleMenuBtnIds(btnSaveItem);
                }

                // ③ 判断如果有子树则递归下去
                List<SysMenuTreeVO> children = menu.getChildren();
                if (!CollectionUtils.isEmpty(children)) {
                    handleMenuAndBtnPermissionTree(roleId, children);
                }
            });
        }
    }

    @Override
    public void deleteAllMenusByRoleId(Integer roleId) {
        sysRoleMenuMapper.deleteAllMenusByRoleId(roleId);
    }

}
