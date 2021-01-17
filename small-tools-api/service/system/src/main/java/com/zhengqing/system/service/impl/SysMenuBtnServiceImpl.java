package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.system.entity.SysMenuBtn;
import com.zhengqing.system.mapper.SysMenuBtnMapper;
import com.zhengqing.system.model.dto.SysMenuBtnSaveDTO;
import com.zhengqing.system.model.vo.SysMenuBtnListVO;
import com.zhengqing.system.service.ISysMenuBtnService;
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
public class SysMenuBtnServiceImpl extends ServiceImpl<SysMenuBtnMapper, SysMenuBtn> implements ISysMenuBtnService {

    @Autowired
    private SysMenuBtnMapper sysMenuBtnMapper;

    @Override
    public List<Integer> getBtnIdsByMenuId(Integer menuId) {
        return sysMenuBtnMapper.getBtnIdsByMenuId(menuId);
    }

    @Override
    public void addOrUpdateData(SysMenuBtnSaveDTO params) {
        Integer menuId = params.getMenuId();
        // ① 先删除
        sysMenuBtnMapper.deleteByMenuId(menuId);
        List<Integer> btnIdList = params.getBtnIdList();

        List<SysMenuBtn> sysMenuBtnList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(btnIdList)) {
            btnIdList.forEach(btnId -> {
                SysMenuBtn sysMenuBtn = new SysMenuBtn();
                sysMenuBtn.setMenuId(menuId);
                sysMenuBtn.setBtnId(btnId);
                sysMenuBtnList.add(sysMenuBtn);
            });
            // ② 再保存
            // sysMenuBtnMapper.insertBatchBtns(menuId, btnIdList);
            this.saveBatch(sysMenuBtnList);
        }
    }

    @Override
    public List<SysMenuBtnListVO> getBtnInfoListByMenuId(Integer menuId) {
        return sysMenuBtnMapper.selectBtnInfoListByMenuId(menuId);
    }

}
