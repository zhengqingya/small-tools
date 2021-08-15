package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.util.MyBeanUtil;
import com.zhengqing.common.util.RedisUtil;
import com.zhengqing.system.entity.SysDictType;
import com.zhengqing.system.mapper.SysDictTypeMapper;
import com.zhengqing.system.model.dto.SysDictTypeSaveDTO;
import com.zhengqing.system.model.vo.SysDictTypeListVO;
import com.zhengqing.system.service.ISysDictService;
import com.zhengqing.system.service.ISysDictTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 数据字典类型-服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:51
 */
@Slf4j
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

    @Lazy
    @Autowired
    private ISysDictService dictService;

    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;

    @Override
    public List<SysDictTypeListVO> listByOpen() {
        return this.sysDictTypeMapper.selectDictTypeListByOpen();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdateData(SysDictTypeSaveDTO params) {
        SysDictType sysDictType = MyBeanUtil.copyProperties(params, SysDictType.class);
        if (params.getId() == null) {
            this.sysDictTypeMapper.insert(sysDictType);
        } else {
            this.sysDictTypeMapper.updateById(sysDictType);
        }
        this.dictService.updateCache(sysDictType.getCode());
        return sysDictType.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteType(Integer id) {
        SysDictType sysDictType = this.sysDictTypeMapper.selectById(id);
        String key = sysDictType.getCode();
        // 1、 先删除数据字典
        this.dictService.deleteDictByDictTypeId(id);
        // 2、 再删除数据字典类型
        this.sysDictTypeMapper.deleteById(id);
        // 3、 最后删除缓存
        RedisUtil.delete(AppConstant.CACHE_SYS_DICT_PREFIX + key);
        log.info("删除数据字典[{}] & 删除缓存成功", key);
    }

}
