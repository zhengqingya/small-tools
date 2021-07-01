package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.util.MyBeanUtil;
import com.zhengqing.system.entity.SysMenu;
import com.zhengqing.system.mapper.SysMenuMapper;
import com.zhengqing.system.model.dto.SysMenuListDTO;
import com.zhengqing.system.model.dto.SysMenuSaveDTO;
import com.zhengqing.system.model.vo.SysMenuTreeVO;
import com.zhengqing.system.service.ISysMenuService;
import com.zhengqing.system.service.ISysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 系统管理-菜单表 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 18:51
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private ISysPermissionService sysPermissionService;

    @Override
    public IPage<SysMenu> listPage(SysMenuListDTO params) {
        return sysMenuMapper.selectMenus(new Page(), params);
    }

    @Override
    public List<SysMenu> list(SysMenuListDTO params) {
        return sysMenuMapper.selectMenus(params);
    }

    @Override
    public Integer addOrUpdateData(SysMenuSaveDTO params) {
        SysMenu sysMenu = MyBeanUtil.copyProperties(params, SysMenu.class);
        Integer menuId = params.getMenuId();
        if (menuId == null) {
            sysMenuMapper.insert(sysMenu);
        } else {
            sysMenuMapper.updateById(sysMenu);
        }
        return sysMenu.getMenuId();
    }

    @Override
    public List<SysMenuTreeVO> tree(Integer systemSource) {
        // 法一：走缓存
        // List<SysMenuTreeVO> result =
        // JSONArray.parseArray(RedisUtil.get(Constants.CACHE_SYS_MENU_TREE), SysMenuTreeVO.class);
        // 法二：走数据库
        return sysPermissionService.menuTree(systemSource);
    }

    @Override
    public List<SysMenuTreeVO> selectMenuTree(Integer systemSource) {
        return sysMenuMapper.selectMenuTree(systemSource);
    }

}
