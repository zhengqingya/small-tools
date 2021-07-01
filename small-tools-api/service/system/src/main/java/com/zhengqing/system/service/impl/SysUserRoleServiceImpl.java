package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.system.entity.SysUserRole;
import com.zhengqing.system.mapper.SysUserRoleMapper;
import com.zhengqing.system.model.dto.SysUserRoleSaveDTO;
import com.zhengqing.system.service.ISysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(rollbackFor = Exception.class)
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public void addOrUpdateData(SysUserRoleSaveDTO params) {
        Integer userId = params.getUserId();
        String roleIds = StringUtils.join(params.getRoleIdList().toArray(), ",");
        // ① 先删除关联角色
        removeById(userId);
        // ② 再新增角色
        new SysUserRole(userId, roleIds).insert();
    }

}
