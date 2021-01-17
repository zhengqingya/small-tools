package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysUserRole;
import com.zhengqing.system.model.dto.SysUserRoleSaveDTO;

/**
 * <p>
 * 系统管理 - 用户角色管理 服务类
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/9/10 11:52
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 新增或更新
     *
     * @param params:
     *            提交参数
     * @return: void
     * @author : zhengqing
     * @date : 2020/9/10 14:29
     */
    void addOrUpdateData(SysUserRoleSaveDTO params);

}
