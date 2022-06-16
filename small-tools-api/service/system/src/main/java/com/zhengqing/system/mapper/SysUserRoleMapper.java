package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.system.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统管理 - 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 18:44
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据用户id查询关联角色ids
     *
     * @param userId 用户id
     * @return 角色ids
     * @author zhengqingya
     * @date 2022/6/14 12:39
     */
    List<Integer> listRoleId(@Param("userId") Integer userId);

}
