package com.zhengqing.system.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.context.SysUserContext;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.model.dto.*;
import com.zhengqing.system.model.vo.SysUserDetailVO;
import com.zhengqing.system.model.vo.SysUserPermVO;
import com.zhengqing.system.service.ISysUserRoleService;
import com.zhengqing.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 系统管理 - 用户管理接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:43
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/api/user")
@Api(tags = "系统管理 - 用户管理接口")
public class SysUserController extends BaseController {

    private final ISysUserService sysUserService;

    private final ISysUserRoleService sysUserRoleService;

    @GetMapping("listPage")
    @ApiOperation("列表分页")
    public IPage<SysUserDetailVO> listPage(@ModelAttribute SysUserListDTO params) {
        return this.sysUserService.listPage(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<SysUserDetailVO> list(@ModelAttribute SysUserListDTO params) {
        return this.sysUserService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody SysUserSaveDTO params) {
        return this.sysUserService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody SysUserSaveDTO params) {
        return this.sysUserService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer userId) {
        this.sysUserService.deleteUser(userId);
    }

    @GetMapping("getUserInfoById")
    @ApiOperation("根据id获取用户信息")
    public SysUserDetailVO getUserInfoById(@RequestParam Integer userId) {
        return this.sysUserService.getUserInfoByUserId(userId);
    }

    @PutMapping("updatePassword")
    @ApiOperation("修改用户密码")
    public void updatePassword(@RequestBody @Valid SysUserUpdatePasswordDTO params) {
        this.sysUserService.updatePassword(params);
    }

    @GetMapping("resetPassword")
    @ApiOperation("重置用户密码")
    public void resetPassword(@RequestParam Integer userId) {
        this.sysUserService.resetPassword(userId);
    }

    @GetMapping("getUserPerm")
    @ApiOperation("获取当前登录用户权限信息")
    public SysUserPermVO getUserPerm(@RequestParam(required = false) Integer userId) {
        SysUserPermVO userPerm = this.sysUserService.getUserPerm(
                SysUserPermDTO.builder()
                        .userId(userId == null ? SysUserContext.getUserId() : userId)
                        .build()
        );
        userPerm.setPassword(null);
        return userPerm;
    }

    @PostMapping("saveRoleIds")
    @ApiOperation("保存用户角色ids")
    public void saveRoleIds(@Validated @RequestBody SysUserRoleSaveDTO params) {
        this.sysUserRoleService.addOrUpdateData(params);
    }

}
