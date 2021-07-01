package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.util.MyBeanUtil;
import com.zhengqing.common.util.RedisUtil;
import com.zhengqing.system.entity.SysDictType;
import com.zhengqing.system.mapper.SysDictTypeMapper;
import com.zhengqing.system.model.dto.SysDictTypeSaveDTO;
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
@Transactional(rollbackFor = Exception.class)
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

    @Lazy
    @Autowired
    private ISysDictService dictService;

    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;

    @Override
    public List<SysDictType> upDictTypeList() {
        return sysDictTypeMapper.upDictTypeList();
    }

    @Override
    public Integer addOrUpdateData(SysDictTypeSaveDTO params) {
        SysDictType sysDictType = MyBeanUtil.copyProperties(params, SysDictType.class);
        if (params.getId() == null) {
            sysDictTypeMapper.insert(sysDictType);
        } else {
            sysDictTypeMapper.updateById(sysDictType);
        }
        dictService.updateCache(sysDictType.getCode());
        return sysDictType.getId();
    }

    @Override
    public void deleteType(Integer id) {
        SysDictType sysDictType = sysDictTypeMapper.selectById(id);
        // ① 先删除数据字典
        dictService.deleteDictByDictTypeId(id);
        // ② 再删除数据字典类型
        sysDictTypeMapper.deleteById(id);
        // ③ 最后删除缓存
        String key = sysDictType.getCode();
        RedisUtil.delete(AppConstant.CACHE_SYS_DICT_PREFIX + key);
        log.info("删除数据字典`{}`缓存成功", key);
    }

}
