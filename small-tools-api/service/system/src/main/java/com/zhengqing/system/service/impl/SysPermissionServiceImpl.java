package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.system.entity.SysPermission;
import com.zhengqing.system.mapper.SysPermissionMapper;
import com.zhengqing.system.model.dto.SysMenuBtnSaveDTO;
import com.zhengqing.system.model.vo.SysMenuBtnListVO;
import com.zhengqing.system.model.vo.SysRoleRePermListVO;
import com.zhengqing.system.service.ISysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统管理-权限 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:31
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<Integer> getBtnIdsByMenuId(Integer menuId) {
        return this.sysPermissionMapper.getBtnIdsByMenuId(menuId);
    }

    @Override
    public void addOrUpdateData(SysMenuBtnSaveDTO params) {
        Integer menuId = params.getMenuId();
        // ① 先删除
        this.sysPermissionMapper.deleteByMenuId(menuId);
        List<Integer> btnIdList = params.getBtnIdList();

        List<SysPermission> sysPermissionList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(btnIdList)) {
            btnIdList.forEach(btnId -> {
                SysPermission sysPermission = new SysPermission();
                sysPermission.setMenuId(menuId);
                sysPermission.setBtnId(btnId);
                sysPermissionList.add(sysPermission);
            });
            // ② 再保存
            this.saveBatch(sysPermissionList);
        }
    }

    @Override
    public List<SysMenuBtnListVO> getBtnInfoListByMenuId(Integer menuId) {
        return this.sysPermissionMapper.selectBtnInfoListByMenuId(menuId);
    }

    @Override
    public List<SysRoleRePermListVO> listRoleRePerm() {
        return this.sysPermissionMapper.listRoleRePerm();
    }

}
