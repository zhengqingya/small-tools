package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.system.entity.SysRoleMenuBtn;
import com.zhengqing.system.model.vo.SysRoleMenuBtnListVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统管理-角色菜单按钮表 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 19:00
 */
public interface SysRoleMenuBtnMapper extends BaseMapper<SysRoleMenuBtn> {

    /**
     * 获取所有角色菜单按钮权限信息
     *
     * @return 所有角色关联的菜单按钮信息
     * @author zhengqingya
     * @date 2020/9/10 17:54
     */
    List<SysRoleMenuBtnListVO> selectRoleMenuBtns();

    /**
     * 通过角色ID和菜单ID查询该菜单所拥有的所有按钮
     *
     * @param roleId: 角色ID
     * @param menuId: 菜单ID
     * @return 菜单所拥有的所有按钮ids
     * @author zhengqingya
     * @date 2020/9/10 17:58
     */
    List<Integer> selectBtnsByRoleIdAndMenuId(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    /**
     * 根据角色id删除关联所有按钮权限
     *
     * @param roleId: 角色id
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 17:50
     */
    @Delete("DELETE FROM t_sys_role_menu_btn WHERE role_id = #{roleId}")
    void deleteBtnsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 根据角色ID和菜单ID删除按钮
     *
     * @param roleId: 角色ID
     * @param menuId: 菜单ID
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 17:57
     */
    @Delete("DELETE FROM t_sys_role_menu_btn WHERE role_id = #{roleId} AND menu_id = #{menuId}")
    void deleteBtnsByRoleIdAndMenuId(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

}
