package com.zhengqing.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.enums.YesNoEnum;
import com.zhengqing.common.exception.MyException;
import com.zhengqing.common.util.MyBeanUtil;
import com.zhengqing.common.util.RedisUtil;
import com.zhengqing.system.entity.SysDict;
import com.zhengqing.system.entity.SysDictType;
import com.zhengqing.system.mapper.SysDictMapper;
import com.zhengqing.system.model.dto.SysDictSaveDTO;
import com.zhengqing.system.model.vo.SysDictVO;
import com.zhengqing.system.service.ISysDictService;
import com.zhengqing.system.service.ISysDictTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
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
@Transactional(rollbackFor = Exception.class)
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    @Lazy
    @Autowired
    private ISysDictTypeService dictTypeService;

    @Override
    public List<SysDictVO> getAllDictListByCode(String code) {
        if (StringUtils.isBlank(code)) {
            throw new MyException("查询编码不能为空!");
        }
        return sysDictMapper.selectDictListByCode(null, code);
    }

    @Override
    public List<SysDictVO> getUpDictListFromDbByCode(String code) {
        if (StringUtils.isBlank(code)) {
            throw new MyException("查询编码不能为空!");
        }
        return sysDictMapper.selectDictListByCode(YesNoEnum.是.getValue(), code);
    }

    @Override
    public List<SysDictVO> getUpDictListFromCacheByCode(@NotBlank(message = "查询编码不能为空!") String code) {
        return JSONArray.parseArray(RedisUtil.get(AppConstant.CACHE_SYS_DICT_PREFIX + code), SysDictVO.class);
    }

    @Override
    public Integer addOrUpdateData(SysDictSaveDTO params) {
        SysDict sysDict = MyBeanUtil.copyProperties(params, SysDict.class);
        if (params.getId() == null) {
            sysDictMapper.insert(sysDict);
        } else {
            sysDictMapper.updateById(sysDict);
        }
        updateCache(dictTypeService.getById(sysDict.getDictTypeId()).getCode());
        return sysDict.getId();
    }

    @Override
    public void deleteDictById(Integer id) {
        SysDict sysDict = sysDictMapper.selectById(id);
        sysDictMapper.deleteById(id);
        updateCache(dictTypeService.getById(sysDict.getDictTypeId()).getCode());
    }

    @Override
    public void deleteDictByDictTypeId(Integer dictTypeId) {
        sysDictMapper.deleteByDictTypeId(dictTypeId);
    }

    @Override
    public void updateCache(String code) {
        String key = AppConstant.CACHE_SYS_DICT_PREFIX + code;
        // 加入||更新 缓存
        if (RedisUtil.hasKey(key)) {
            RedisUtil.delete(key);
            log.info("更新数据字典之前删除缓存`{}`" + key);
        }
        List<SysDictVO> dictList = getUpDictListFromDbByCode(code);
        if (!CollectionUtils.isEmpty(dictList)) {
            RedisUtil.set(key, JSON.toJSONString(dictList));
            log.info("加入数据字典缓存`{}`" + key);
        }
    }

    @Override
    public void initCache() {
        List<SysDictType> sysDictTypeList = dictTypeService.upDictTypeList();
        sysDictTypeList.forEach(e -> {
            String code = e.getCode();
            RedisUtil.set(AppConstant.CACHE_SYS_DICT_PREFIX + code, JSON.toJSONString(getUpDictListFromDbByCode(code)));
        });
        log.info("初始化数据字典缓存成功!");
    }

}
