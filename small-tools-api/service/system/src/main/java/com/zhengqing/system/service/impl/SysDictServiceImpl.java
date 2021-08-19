package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.enums.YesNoEnum;
import com.zhengqing.common.util.RedisUtil;
import com.zhengqing.system.entity.SysDict;
import com.zhengqing.system.entity.SysDictType;
import com.zhengqing.system.mapper.SysDictMapper;
import com.zhengqing.system.model.dto.SysDictSaveDTO;
import com.zhengqing.system.model.vo.SysDictTypeListVO;
import com.zhengqing.system.model.vo.SysDictVO;
import com.zhengqing.system.service.ISysDictService;
import com.zhengqing.system.service.ISysDictTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * <p>
 * 数据字典-服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:51
 */
@Slf4j
@Service
@Validated
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    @Lazy
    @Autowired
    private ISysDictTypeService dictTypeService;

    @Override
    public List<SysDictVO> getAllDictListByCode(String code) {
        return this.sysDictMapper.selectDictListByCode(null, code);
    }

    @Override
    public List<SysDictVO> getUpDictListFromDbByCode(String code) {
        return this.sysDictMapper.selectDictListByCode(YesNoEnum.是.getValue(), code);
    }

    @Override
    public List<SysDictVO> getUpDictListFromCacheByCode(String code) {
        return JSONArray.parseArray(RedisUtil.get(AppConstant.CACHE_SYS_DICT_PREFIX + code), SysDictVO.class);
    }

    @Override
    public SysDict detail(Integer dictId) {
        SysDict sysDict = this.sysDictMapper.selectById(dictId);
        Assert.notNull(sysDict, "字典不存在！");
        return sysDict;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdateData(SysDictSaveDTO params) {
        Integer dictTypeId = params.getDictTypeId();
        SysDictType dictTypeData = this.dictTypeService.detail(dictTypeId);
        Integer id = params.getId();
        String name = params.getName();
        String value = params.getValue();
        Integer sort = params.getSort();
        // 保存数据
        SysDict sysDict = SysDict.builder()
                .id(id)
                .dictTypeId(dictTypeId)
                .name(name)
                .value(value)
                .status(YesNoEnum.是.getValue())
                .sort(sort)
                .build();
        if (params.getId() == null) {
            this.sysDictMapper.insert(sysDict);
        } else {
            // 校验该数据是否存在
            this.detail(id);
            this.sysDictMapper.updateById(sysDict);
        }
        // 更新缓存
        this.updateCache(dictTypeData.getCode());
        return sysDict.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictById(Integer id) {
        SysDict sysDict = this.sysDictMapper.selectById(id);
        if (sysDict == null) {
            return;
        }
        this.sysDictMapper.deleteById(id);
        SysDictType dictTypeData = this.dictTypeService.detail(sysDict.getDictTypeId());
        // 更新缓存
        this.updateCache(dictTypeData.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictByDictTypeId(Integer dictTypeId) {
        this.sysDictMapper.deleteByDictTypeId(dictTypeId);
    }

    @Override
    public void updateCache(String code) {
        String key = AppConstant.CACHE_SYS_DICT_PREFIX + code;
        // 加入||更新 缓存
        if (RedisUtil.hasKey(key)) {
            RedisUtil.delete(key);
            log.info("更新数据字典之前删除缓存`{}`" + key);
        }
        List<SysDictVO> dictList = this.getUpDictListFromDbByCode(code);
        if (!CollectionUtils.isEmpty(dictList)) {
            RedisUtil.set(key, JSON.toJSONString(dictList));
            log.info("加入数据字典缓存`{}`" + key);
        }
    }

    @Override
    public void initCache() {
        List<SysDictTypeListVO> sysDictTypeList = this.dictTypeService.listByOpen();
        sysDictTypeList.forEach(e -> {
            String code = e.getCode();
            RedisUtil.set(AppConstant.CACHE_SYS_DICT_PREFIX + code, JSON.toJSONString(this.getUpDictListFromDbByCode(code)));
        });
        log.info("初始化数据字典缓存成功!");
    }

}
