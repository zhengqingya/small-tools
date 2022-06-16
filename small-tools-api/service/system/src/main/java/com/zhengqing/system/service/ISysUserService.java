package com.zhengqing.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysUser;
import com.zhengqing.system.model.dto.SysUserListDTO;
import com.zhengqing.system.model.dto.SysUserPermDTO;
import com.zhengqing.system.model.dto.SysUserSaveDTO;
import com.zhengqing.system.model.dto.SysUserUpdatePasswordDTO;
import com.zhengqing.system.model.vo.SysUserDetailVO;
import com.zhengqing.system.model.vo.SysUserPermVO;

import java.util.List;

/**
 * <p>
 * 用户管理 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:33
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 列表分页
     *
     * @param params 查询参数
     * @return 用戶信息
     * @author zhengqingya
     * @date 2020/9/10 10:11
     */
    IPage<SysUserDetailVO> listPage(SysUserListDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 用戶信息
     * @author zhengqingya
     * @date 2020/9/10 10:11
     */
    List<SysUserDetailVO> list(SysUserListDTO params);

    /**
     * 新增或更新
     *
     * @param params 提交参数
     * @return 用户id
     * @author zhengqingya
     * @date 2020/9/10 10:12
     */
    Integer addOrUpdateData(SysUserSaveDTO params);

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 13:49
     */
    void deleteUser(Integer userId);

    /**
     * 修改用户密码
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 11:03
     */
    void updatePassword(SysUserUpdatePasswordDTO params);

    /**
     * 重置用户密码
     *
     * @param userId:
     * @return void
     * @author zhengqingya
     * @date 2020/9/10 11:03
     */
    void resetPassword(Integer userId);

    /**
     * 根据用户id查询用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     * @author zhengqingya
     * @date 2020/9/10 10:53
     */
    SysUserDetailVO getUserInfoByUserId(Integer userId);

    /**
     * 获取用户的基本信息+角色+权限...
     *
     * @param params 查询参数
     * @return 用户权限信息
     * @author zhengqingya
     * @date 2020/9/21 16:18
     */
    SysUserPermVO getUserPerm(SysUserPermDTO params);

}
