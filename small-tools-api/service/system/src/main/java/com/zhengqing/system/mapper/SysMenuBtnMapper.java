package com.zhengqing.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.system.entity.SysMenuBtn;
import com.zhengqing.system.model.vo.SysMenuBtnListVO;

/**
 * <p>
 * 系统管理-菜单按钮权限 Mapper 接口
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/4/15 19:00
 */
public interface SysMenuBtnMapper extends BaseMapper<SysMenuBtn> {

    /**
     * 根据菜单ID查询已经配置的按钮ids
     *
     * @param menuId:
     *            菜单id
     * @return: 按钮ids
     * @author : zhengqing
     * @date : 2020/9/10 21:21
     */
    @Select("SELECT sd.id FROM t_sys_menu_btn mb INNER JOIN t_sys_dict sd on sd.id = mb.btn_id WHERE mb.is_valid=1 AND mb.menu_id = #{menuId}")
    List<Integer> getBtnIdsByMenuId(@Param("menuId") Integer menuId);

    /**
     * 根据菜单id删除所属的按钮ids
     *
     * @param menuId:
     *            菜单id
     * @return: void
     * @author : zhengqing
     * @date : 2020/9/10 21:36
     */
    @Delete("DELETE FROM t_sys_menu_btn WHERE menu_id = #{menuId}")
    void deleteByMenuId(@Param("menuId") Integer menuId);

    /**
     * 批量保存菜单按钮ids
     *
     * @param btnIdList:
     *            菜单按钮ids
     * @return: void
     * @author : zhengqing
     * @date : 2020/9/10 21:36
     */
    void insertBatchBtns(@Param("menuId") Integer menuId, @Param("btnIdList") List<Integer> btnIdList);

    /**
     * 通过菜单id查询菜单按钮权限信息
     *
     * @param menuId:
     *            菜单id
     * @return: 菜单按钮权限信息
     * @author : zhengqing
     * @date : 2020/9/10 22:18
     */
    // @Select("SELECT mb.id, mb.menu_id, mb.btn_id btnId, ( SELECT sd.name FROM t_sys_dict sd WHERE sd.id = mb.btn_id )
    // btnName FROM t_sys_menu_btn mb WHERE mb.is_valid=1 AND mb.menu_id = #{menuId}")
    List<SysMenuBtnListVO> selectBtnInfoListByMenuId(@Param("menuId") Integer menuId);

}
