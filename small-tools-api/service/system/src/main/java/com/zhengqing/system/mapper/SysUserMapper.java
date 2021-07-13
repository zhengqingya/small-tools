package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.system.entity.SysUser;
import com.zhengqing.system.model.dto.SysUserListDTO;
import com.zhengqing.system.model.vo.SysUserDetailVO;
import com.zhengqing.system.model.vo.SysUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统管理-用户基础信息表 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:05
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 列表分页
     *
     * @param page:
     * @param filter:
     * @return 用户信息
     * @author zhengqingya
     * @date 2020/9/10 10:28
     */
    IPage<SysUserDetailVO> selectUsers(IPage<SysUserListDTO> page, @Param("filter") SysUserListDTO filter);

    /**
     * 列表
     *
     * @param filter:
     * @return 用户信息
     * @author zhengqingya
     * @date 2020/9/10 10:34
     */
    List<SysUserDetailVO> selectUsers(@Param("filter") SysUserListDTO filter);

    /**
     * 根据用户id查询用户信息
     *
     * @param userId: 用户id
     * @return 用户信息
     * @author zhengqingya
     * @date 2020/9/10 10:49
     */
    SysUserDetailVO selectUserByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户名查询用户信息
     *
     * @param username: 用户名
     * @return 用户信息
     * @author zhengqingya
     * @date 2020/9/10 13:39
     */
    SysUserVO selectUserByUsername(@Param("username") String username);

    /**
     * 根据用户ids批量查询用户信息
     *
     * @param userIdList: 用户ids
     * @return 用户信息
     * @author zhengqingya
     * @date 2020/9/21 16:18
     */
    List<SysUserVO> selectUserInfoByUserIds(@Param("userIdList") List<Integer> userIdList);

}
