package com.zhengqing.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysUser;
import com.zhengqing.system.model.dto.SysUserListDTO;
import com.zhengqing.system.model.dto.SysUserLoginDTO;
import com.zhengqing.system.model.dto.SysUserSaveDTO;
import com.zhengqing.system.model.dto.SysUserUpdatePasswordDTO;
import com.zhengqing.system.model.vo.SysUserDetailVO;
import com.zhengqing.system.model.vo.SysUserVO;

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
     * @param params: 查询参数
     * @return: 用戶信息
     * @author zhengqingya
     * @date 2020/9/10 10:11
     */
    IPage<SysUserDetailVO> listPage(SysUserListDTO params);

    /**
     * 列表
     *
     * @param params: 查询参数
     * @return: 用戶信息
     * @author zhengqingya
     * @date 2020/9/10 10:11
     */
    List<SysUserDetailVO> list(SysUserListDTO params);

    /**
     * 新增或更新
     *
     * @param params: 提交参数
     * @return: 用户id
     * @author zhengqingya
     * @date 2020/9/10 10:12
     */
    Integer addOrUpdateData(SysUserSaveDTO params);

    /**
     * 删除用户
     *
     * @param userId: 用户id
     * @return: void
     * @author zhengqingya
     * @date 2020/9/10 13:49
     */
    void deleteUser(Integer userId);

    /**
     * 修改用户密码
     *
     * @param params: 提交参数
     * @return: void
     * @author zhengqingya
     * @date 2020/9/10 11:03
     */
    void updatePassword(SysUserUpdatePasswordDTO params);

    /**
     * 重置用户密码
     *
     * @param userId:
     * @return: void
     * @author zhengqingya
     * @date 2020/9/10 11:03
     */
    void resetPassword(Integer userId);

    /**
     * 登录
     *
     * @param params: 登录参数
     * @return: token
     * @author zhengqingya
     * @date 2021/1/1 23:59
     */
    String login(SysUserLoginDTO params);

    /**
     * 根据用户id查询用户信息
     *
     * @param userId: 用户id
     * @return: 用户信息
     * @author zhengqingya
     * @date 2020/9/10 10:53
     */
    SysUserDetailVO getUserInfoByUserId(Integer userId);

    /**
     * 根据用户名查询用户信息
     *
     * @param username: 用户名
     * @return: 用户信息
     * @author zhengqingya
     * @date 2020/9/10 13:48
     */
    SysUserVO getUserInfoByUsername(String username);

    /**
     * 根据用户ids批量查询用户信息
     *
     * @param userIdList: 用户ids
     * @return: 用户信息
     * @author zhengqingya
     * @date 2020/9/21 16:18
     */
    List<SysUserVO> getUserInfoByUserIds(List<Integer> userIdList);

}
