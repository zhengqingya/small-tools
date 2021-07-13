package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysMenuBtn;
import com.zhengqing.system.model.dto.SysMenuBtnSaveDTO;
import com.zhengqing.system.model.vo.SysMenuBtnListVO;

import java.util.List;

/**
 * <p>
 * 系统管理-菜单按钮权限 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:31
 */
public interface ISysMenuBtnService extends IService<SysMenuBtn> {

    /**
     * 通过菜单ID获取已经配置的按钮ids
     *
     * @param menuId: 菜单id
     * @return 按钮ids
     * @author zhengqingya
     * @date 2020/9/10 21:19
     */
    List<Integer> getBtnIdsByMenuId(Integer menuId);

    /**
     * 保存菜单按钮ids
     *
     * @param params: 提交参数
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 21:10
     */
    void addOrUpdateData(SysMenuBtnSaveDTO params);

    /**
     * 通过菜单id查询菜单按钮权限信息
     *
     * @param menuId: 菜单id
     * @return 菜单按钮权限信息
     * @author zhengqingya
     * @date 2020/9/10 22:06
     */
    List<SysMenuBtnListVO> getBtnInfoListByMenuId(Integer menuId);

}
