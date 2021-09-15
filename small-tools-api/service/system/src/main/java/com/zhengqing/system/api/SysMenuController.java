package com.zhengqing.system.api;

import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.validator.common.UpdateGroup;
import com.zhengqing.common.validator.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.system.model.dto.SysMenuBtnSaveDTO;
import com.zhengqing.system.model.dto.SysMenuSaveDTO;
import com.zhengqing.system.model.vo.SysMenuTreeVO;
import com.zhengqing.system.service.ISysMenuBtnService;
import com.zhengqing.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统管理 - 菜单表接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 18:52
 */
@Slf4j
@RestController
@RequestMapping("/web/api/menu")
@Api(tags = "系统管理 - 菜单表接口")
public class SysMenuController extends BaseController {

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysMenuBtnService sysMenuBtnService;

    // @GetMapping("/listPage")
    // @ApiOperation("列表分页")
    // public IPage<SysMenu> listPage(@ModelAttribute SysMenuDTO params) {
    // return menuService.listPage(params);
    // }
    //
    // @GetMapping("/list")
    // @ApiOperation("列表")
    // public List<SysMenu> list(@ModelAttribute SysMenuDTO params) {
    // return menuService.list(params);
    // }

    @GetMapping("menuTree")
    @ApiOperation("菜单树")
    public List<SysMenuTreeVO> menuTree(@RequestParam(required = false) Integer systemSource) {
        return menuService.tree(systemSource);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody SysMenuSaveDTO params) {
        return menuService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody SysMenuSaveDTO params) {
        return menuService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer menuId) {
        menuService.removeById(menuId);
    }

    // 下：菜单按钮权限(菜单页面中配置页面所属按钮使用)

    @GetMapping("getBtnIdsByMenuId")
    @ApiOperation("通过菜单id获取已经配置的按钮ids")
    public List<Integer> getBtnIdsByMenuId(@RequestParam Integer menuId) {
        return sysMenuBtnService.getBtnIdsByMenuId(menuId);
    }

    @NoRepeatSubmit
    @PostMapping("saveMenuBtnIds")
    @ApiOperation("保存菜单按钮ids")
    public void saveMenuBtnIds(@Validated @RequestBody SysMenuBtnSaveDTO params) {
        sysMenuBtnService.addOrUpdateData(params);
    }

}
