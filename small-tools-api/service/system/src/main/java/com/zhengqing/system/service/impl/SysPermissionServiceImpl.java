package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.system.entity.SysDict;
import com.zhengqing.system.entity.SysPermission;
import com.zhengqing.system.mapper.SysPermissionMapper;
import com.zhengqing.system.model.dto.SysMenuBtnSaveDTO;
import com.zhengqing.system.model.vo.SysMenuBtnListVO;
import com.zhengqing.system.model.vo.SysRoleRePermListVO;
import com.zhengqing.system.service.ISysDictService;
import com.zhengqing.system.service.ISysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    @Resource
    private ISysDictService sysDictService;

    @Override
    public List<Integer> getBtnIdsByMenuId(Integer menuId) {
        return this.sysPermissionMapper.getBtnIdsByMenuId(menuId);
    }

    @Override
    public void addOrUpdateData(SysMenuBtnSaveDTO params) {
        Integer menuId = params.getMenuId();
        // 1、先删除
        this.sysPermissionMapper.deleteByMenuId(menuId);
        // 2、再保存
        List<Integer> btnIdList = params.getBtnIdList();
        if (!CollectionUtils.isEmpty(btnIdList)) {
            Map<Integer, SysDict> sysDictMap = this.sysDictService.map(btnIdList);
            Assert.isTrue(sysDictMap.size() == btnIdList.size(), "数据字典数据丢失，请联系系统管理员！");
            List<SysPermission> sysPermissionList = Lists.newArrayList();
            btnIdList.forEach(btnId -> {
                SysDict sysDict = sysDictMap.get(btnId);
                SysPermission sysPermission = new SysPermission();
                sysPermission.setMenuId(menuId);
                sysPermission.setBtnId(btnId);
                sysPermission.setName(sysDict.getName());
                sysPermission.setBtnPerm(sysDict.getValue());
                // FIXME 规则后期制定
                sysPermission.setUrlPerm("GET:/web/api/*");
                sysPermissionList.add(sysPermission);
            });
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
