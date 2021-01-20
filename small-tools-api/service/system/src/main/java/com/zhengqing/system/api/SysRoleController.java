package com.zhengqing.system.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.validator.fieldrepeat.UpdateGroup;
import com.zhengqing.common.validator.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.system.model.dto.SysRoleListDTO;
import com.zhengqing.system.model.dto.SysRoleMenuBtnSaveDTO;
import com.zhengqing.system.model.dto.SysRoleMenuSaveDTO;
import com.zhengqing.system.model.dto.SysRolePermissionSaveDTO;
import com.zhengqing.system.model.dto.SysRoleSaveDTO;
import com.zhengqing.system.model.vo.SysMenuBtnListVO;
import com.zhengqing.system.model.vo.SysRoleAllPermissionDetailVO;
import com.zhengqing.system.model.vo.SysRoleListVO;
import com.zhengqing.system.model.vo.SysRolePermissionDetailVO;
import com.zhengqing.system.service.ISysMenuBtnService;
import com.zhengqing.system.service.ISysRoleMenuBtnService;
import com.zhengqing.system.service.ISysRoleMenuService;
import com.zhengqing.system.service.ISysRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 系统管理 - 角色管理接口
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/9/10 18:33
 */
@Slf4j
@RestController
@RequestMapping("/web/api/role")
@Api(tags = "系统管理 - 角色管理接口")
public class SysRoleController extends BaseController {

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysMenuBtnService sysMenuBtnService;

    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

    @Autowired
    private ISysRoleMenuBtnService sysRoleMenuBtnService;

    @GetMapping("listPage")
    @ApiOperation("列表分页")
    public IPage<SysRoleListVO> listPage(@ModelAttribute SysRoleListDTO params) {
        return roleService.listPage(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<SysRoleListVO> list(@ModelAttribute SysRoleListDTO params) {
        return roleService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody SysRoleSaveDTO params) {
        return roleService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody SysRoleSaveDTO params) {
        return roleService.addOrUpdateData(params);
    }

    @GetMapping("detail")
    @ApiOperation("详情(角色基本信息+菜单ids)")
    public SysRolePermissionDetailVO detail(@RequestParam Integer roleId) {
        return roleService.detail(roleId);
    }

    @GetMapping("permissionDetail")
    @ApiOperation("详情(带树+按钮+所拥有的权限)")
    public SysRoleAllPermissionDetailVO permissionDetail(@RequestParam Integer roleId,
        @RequestParam(required = false) Integer systemSource) {
        return roleService.permissionDetail(roleId, systemSource);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer roleId) {
        roleService.deleteRoleAndRoleMenu(roleId);
    }

    // ======================== ↓↓↓↓↓↓ 角色关联菜单按钮权限 ↓↓↓↓↓↓ ==========================

    @GetMapping("getBtnsByMenuId")
    @ApiOperation("通过菜单id查询该菜单所有按钮信息")
    public List<SysMenuBtnListVO> getBtnInfoListByMenuId(@RequestParam Integer menuId) {
        return sysMenuBtnService.getBtnInfoListByMenuId(menuId);
    }

    @GetMapping("getPermissionBtnsByRoleIdAndMenuId")
    @ApiOperation("通过角色id和菜单id查询该菜单所拥有的所有按钮")
    public List<Integer> getPermissionBtnsByRoleIdAndMenuId(@RequestParam Integer roleId,
        @RequestParam Integer menuId) {
        return sysRoleMenuBtnService.getPermissionBtnsByRoleIdAndMenuId(roleId, menuId);
    }

    @NoRepeatSubmit
    @PostMapping("saveRoleMenuIds")
    @ApiOperation("保存角色关联菜单ids")
    public void saveRoleMenuIds(@Validated @RequestBody SysRoleMenuSaveDTO params) {
        sysRoleMenuService.saveRoleMenuIds(params);
    }

    @NoRepeatSubmit
    @PostMapping("saveRoleMenuBtnIds")
    @ApiOperation("保存角色关联菜单按钮ids")
    public void saveRoleMenuBtnIds(@Validated @RequestBody SysRoleMenuBtnSaveDTO params) {
        sysRoleMenuBtnService.saveRoleMenuBtnIds(params);
    }

    @NoRepeatSubmit
    @PostMapping("saveRolePermission")
    @ApiOperation("保存角色权限（菜单权限+按钮权限）")
    public void saveRolePermission(@Validated @RequestBody SysRolePermissionSaveDTO params) {
        sysRoleMenuService.saveRolePermission(params);
    }

}
