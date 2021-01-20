package com.zhengqing.system.api;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import com.google.common.collect.Maps;
import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.validator.fieldrepeat.UpdateGroup;
import com.zhengqing.common.validator.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.system.model.dto.SysUserListDTO;
import com.zhengqing.system.model.dto.SysUserLoginDTO;
import com.zhengqing.system.model.dto.SysUserRoleSaveDTO;
import com.zhengqing.system.model.dto.SysUserSaveDTO;
import com.zhengqing.system.model.dto.SysUserUpdatePasswordDTO;
import com.zhengqing.system.model.vo.SysUserDetailVO;
import com.zhengqing.system.model.vo.SysUserVO;
import com.zhengqing.system.service.ISysPermissionService;
import com.zhengqing.system.service.ISysUserRoleService;
import com.zhengqing.system.service.ISysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 系统管理 - 用户管理接口
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/4/15 11:43
 */
@Slf4j
@RestController
@RequestMapping("/web/api/user")
@Api(tags = "系统管理 - 用户管理接口")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysPermissionService sysPermissionService;

    @GetMapping("listPage")
    @ApiOperation("列表分页")
    public IPage<SysUserDetailVO> listPage(@ModelAttribute SysUserListDTO params) {
        return sysUserService.listPage(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<SysUserDetailVO> list(@ModelAttribute SysUserListDTO params) {
        return sysUserService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody SysUserSaveDTO params) {
        return sysUserService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody SysUserSaveDTO params) {
        return sysUserService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer userId) {
        sysUserService.deleteUser(userId);
        // RedisUtil.delete(Constants.CACHE_SYS_USER_INFO_PREFIX + userId);
    }

    @GetMapping("getUserInfoById")
    @ApiOperation("根据id获取用户信息")
    public SysUserDetailVO getUserInfoById(@RequestParam Integer userId) {
        return sysUserService.getUserInfoByUserId(userId);
    }

    @PutMapping("updatePassword")
    @ApiOperation("修改用户密码")
    public void updatePassword(@RequestBody @Valid SysUserUpdatePasswordDTO params) {
        sysUserService.updatePassword(params);
    }

    @GetMapping("resetPassword")
    @ApiOperation("重置用户密码")
    public void resetPassword(@RequestParam Integer userId) {
        sysUserService.resetPassword(userId);
    }

    @PostMapping("login")
    @ApiOperation("登录")
    public Map<String, String> login(@RequestBody SysUserLoginDTO params) {
        String token = sysUserService.login(params);
        Map<String, String> map = Maps.newHashMap();
        map.put("token", token);
        return map;
    }

    @GetMapping("logout")
    @ApiOperation("登出")
    public void logout() {
        log.debug("登出:{}", appGetCurrentUserId());
        // TODO 退出登录后清空缓存用户信息...
        // RedisUtil.delete(Constants.CACHE_SYS_USER_INFO_PREFIX + appGetUserId());
    }

    @GetMapping("getCurrentUserInfoAndPermission")
    @ApiOperation("获取当前登录用户信息")
    public SysUserVO getCurrentUserInfoAndPermission(@RequestParam(required = false) Integer systemSource) {
        return sysPermissionService.appGetCurrentUserInfoAndPermission(systemSource);
    }

    @PostMapping("saveRoleIds")
    @ApiOperation("保存用户角色ids")
    public void saveRoleIds(@Validated @RequestBody SysUserRoleSaveDTO params) {
        sysUserRoleService.addOrUpdateData(params);
        // Integer userId = roleInfo.getUserId();
        // if (userId.equals(appGetUserId())) {
        // // 更新缓存
        // sysPermissionService.updateCacheSysPermission(EnumCacheType.个人所有信息, userId);
        // }
    }

}
