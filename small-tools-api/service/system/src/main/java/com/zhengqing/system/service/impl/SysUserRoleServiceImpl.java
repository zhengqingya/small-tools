package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.system.entity.SysUserRole;
import com.zhengqing.system.mapper.SysUserRoleMapper;
import com.zhengqing.system.model.dto.SysUserRoleSaveDTO;
import com.zhengqing.system.service.ISysUserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 系统管理 - 用户角色管理 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/10 11:52
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    private final SysUserRoleMapper sysUserRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(SysUserRoleSaveDTO params) {
        Integer userId = params.getUserId();
        List<Integer> roleIdList = params.getRoleIdList();
        // ① 先删除关联角色
        this.removeById(userId);
        // ② 再新增角色
        roleIdList.forEach(roleId ->
                SysUserRole.builder()
                        .userId(userId)
                        .roleId(roleId)
                        .build()
                        .insert());
    }

    @Override
    public List<Integer> listRoleId(Integer userId) {
        return this.sysUserRoleMapper.listRoleId(userId);
    }

}
