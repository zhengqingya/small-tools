package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.system.entity.SysRoleMenuBtn;
import com.zhengqing.system.mapper.SysRoleMenuBtnMapper;
import com.zhengqing.system.model.dto.SysRoleMenuBtnSaveDTO;
import com.zhengqing.system.model.vo.SysRoleMenuBtnListVO;
import com.zhengqing.system.service.ISysRoleMenuBtnService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * <p>
 * 系统管理-菜单按钮权限 服务实现类
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/4/15 20:31
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleMenuBtnServiceImpl extends ServiceImpl<SysRoleMenuBtnMapper, SysRoleMenuBtn>
    implements ISysRoleMenuBtnService {

    @Autowired
    private SysRoleMenuBtnMapper sysRoleMenuBtnMapper;

    @Override
    public List<SysRoleMenuBtnListVO> getRoleMenuBtns() {
        return sysRoleMenuBtnMapper.selectRoleMenuBtns();
    }

    @Override
    public List<Integer> getPermissionBtnsByRoleIdAndMenuId(Integer roleId, Integer menuId) {
        return sysRoleMenuBtnMapper.selectBtnsByRoleIdAndMenuId(roleId, menuId);
    }

    @Override
    public List<SysRoleMenuBtnListVO> getAllRoleMenuBtns() {
        return getRoleMenuBtns();
    }

    @Override
    public void deleteBtnsByRoleId(Integer roleId) {
        sysRoleMenuBtnMapper.deleteBtnsByRoleId(roleId);
    }

    @Override
    public void deleteBtnsByRoleIdAndMenuId(Integer roleId, Integer menuId) {
        sysRoleMenuBtnMapper.deleteBtnsByRoleIdAndMenuId(roleId, menuId);
    }

    @Override
    public void saveRoleMenuBtnIds(SysRoleMenuBtnSaveDTO params) {
        Integer roleId = params.getRoleId();
        Integer menuId = params.getMenuId();
        List<Integer> btnIdList = params.getBtnIdList();
        // ① 先删除
        deleteBtnsByRoleIdAndMenuId(roleId, menuId);
        // ② 再保存
        List<SysRoleMenuBtn> itemList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(btnIdList)) {
            btnIdList.forEach(btnId -> {
                SysRoleMenuBtn item = new SysRoleMenuBtn();
                item.setRoleId(roleId);
                item.setMenuId(menuId);
                item.setBtnId(btnId);
                itemList.add(item);
            });
            saveBatch(itemList);
        }
    }

}
