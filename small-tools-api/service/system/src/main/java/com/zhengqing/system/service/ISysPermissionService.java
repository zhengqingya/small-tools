package com.zhengqing.system.service;

import com.zhengqing.system.enums.SysCacheTypeEnum;
import com.zhengqing.system.model.vo.SysMenuTreeVO;
import com.zhengqing.system.model.vo.SysPermissionVO;
import com.zhengqing.system.model.vo.SysUserVO;

import java.util.List;

/**
 * <p>
 * 系统管理 - 权限系列缓存 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 18:51
 */
public interface ISysPermissionService {

    /**
     * 更新权限缓存处理
     *
     * @param cacheType: 缓存类型
     * @param userId:    用户id
     * @return: void
     * @author zhengqingya
     * @date 2021/1/13 21:21
     */
    void updateCacheSysPermission(SysCacheTypeEnum cacheType, Integer userId);

    /**
     * 获取菜单树
     *
     * @param systemSource: 系统来源
     * @return: 菜单树信息
     * @author zhengqingya
     * @date 2021/1/13 20:44
     */
    List<SysMenuTreeVO> menuTree(Integer systemSource);

    /**
     * 根据用户ID获取权限信息（角色+菜单+其下对应的按钮）
     *
     * @param userId:       用户id
     * @param systemSource: 系统来源
     * @return: 权限信息
     * @author zhengqingya
     * @date 2021/1/13 21:21
     */
    SysPermissionVO getPermissionByUserId(Integer userId, Integer systemSource);

    /**
     * 根据userId获取指定用户信息
     *
     * @param userId:       用户id
     * @param systemSource: 系统来源
     * @return: 用户信息
     * @author zhengqingya
     * @date 2020/8/30 15:31
     */
    SysUserVO getUserInfoByUserId(Integer userId, Integer systemSource);

    /**
     * 获取当前登录人基本信息+角色+权限...
     *
     * @param systemSource: 系统来源
     * @return: 用户权限信息
     * @author zhengqingya
     * @date 2020/8/30 15:39
     */
    SysUserVO appGetCurrentUserInfoAndPermission(Integer systemSource);

}
