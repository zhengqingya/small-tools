package com.zhengqing.ums.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.ums.entity.UmsUser;
import com.zhengqing.ums.mapper.UmsUserMapper;
import com.zhengqing.ums.model.dto.UmsUserDTO;
import com.zhengqing.ums.model.vo.UmsUserVO;
import com.zhengqing.ums.service.IUmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 用户管理 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:33
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsUser> implements IUmsUserService {

    @Resource
    private UmsUserMapper umsUserMapper;

    @Override
    public UmsUser detail(Long id) {
        UmsUser umsUser = this.umsUserMapper.selectById(id);
        Assert.notNull(umsUser, "用户不存在！");
        return umsUser;
    }

    @Override
    public UmsUserVO getUser(Long id) {
        return this.getUserInfo(UmsUserDTO.builder().userId(id).build());
    }

    @Override
    public UmsUserVO getUserInfo(UmsUserDTO params) {
        UmsUserVO umsUserVO = this.umsUserMapper.selectUserInfo(params);
        Assert.notNull(umsUserVO, "用户不存在！");
        return umsUserVO;
    }

}
