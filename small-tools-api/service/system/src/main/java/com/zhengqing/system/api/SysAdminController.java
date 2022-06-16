package com.zhengqing.system.api;

import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.system.model.vo.SysRoleRePermListVO;
import com.zhengqing.system.service.ISysPermissionBusinessService;
import com.zhengqing.system.service.ISysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 管理员api
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/19 13:49
 */
@RestController
@RequestMapping("")
@Api(tags = "管理员api")
@RequiredArgsConstructor
public class SysAdminController extends BaseController {

    private final ISysPermissionService sysPermissionService;

    private final ISysPermissionBusinessService sysPermissionBusinessService;

    @ApiOperation("获取角色权限映射数据")
    @GetMapping("listRoleRePerm")
    public List<SysRoleRePermListVO> listRoleRePerm() {
        return this.sysPermissionService.listRoleRePerm();
    }

    @ApiOperation("刷新Redis缓存中的角色菜单权限")
    @GetMapping("refreshRedisPerm")
    public void refreshRedisPerm() {
        this.sysPermissionBusinessService.refreshRedisPerm();
    }

}
