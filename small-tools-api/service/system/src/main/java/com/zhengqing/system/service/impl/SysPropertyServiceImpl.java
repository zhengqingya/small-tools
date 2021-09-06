package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.constant.MybatisConstant;
import com.zhengqing.common.context.ContextHandler;
import com.zhengqing.system.entity.SysProperty;
import com.zhengqing.system.mapper.SysPropertyMapper;
import com.zhengqing.system.model.dto.SysPropertySaveDTO;
import com.zhengqing.system.model.vo.SysPropertyListVO;
import com.zhengqing.system.service.ISysPropertyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 系统管理-系统属性 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/09/06 22:57
 */
@Slf4j
@Service
public class SysPropertyServiceImpl extends ServiceImpl<SysPropertyMapper, SysProperty> implements ISysPropertyService {

    @Autowired
    private SysPropertyMapper sysPropertyMapper;

    @Override
    public List<SysPropertyListVO> list(List<String> keyList) {
        List<SysPropertyListVO> list = this.sysPropertyMapper.selectDataList(keyList);
        return list;
    }

    @Override
    public SysProperty detail(Integer id) {
        SysProperty detailData = this.sysPropertyMapper.selectById(id);
        Assert.notNull(detailData, "该数据不存在！");
        return detailData;
    }

    @Override
    public SysProperty detailByKey(String key) {
        SysProperty detailData = this.sysPropertyMapper.selectOne(
                new LambdaQueryWrapper<SysProperty>()
                        .eq(SysProperty::getKey, key)
                        .last(MybatisConstant.LIMIT_ONE));
        Assert.notNull(detailData, "该数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(Map<String, List<SysPropertySaveDTO>> dataMap) {
        if (CollectionUtils.isEmpty(dataMap)) {
            return;
        }
        List<SysPropertySaveDTO> saveList = Lists.newArrayList();
        List<String> keyList = Lists.newLinkedList();
        dataMap.forEach((key, itemList) -> keyList.add(key));
        dataMap.forEach((key, itemList) -> {
            Assert.notBlank(key, "属性key不能为空!");

            // 校验属性key是否重复
            List<String> repeatKeyDataList = itemList
                    .stream().map(SysPropertySaveDTO::getKey).collect(Collectors.toList())
                    .stream().collect(Collectors.toMap(e -> e, e -> 1, Integer::sum))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            Assert.isTrue(CollectionUtils.isEmpty(repeatKeyDataList), "属性key重复，请重新输入！");

            itemList.forEach(item -> {
                item.setId(null);
                item.setCurrentUserId(ContextHandler.getUserId());
            });
            saveList.addAll(itemList);
        });

        // 删除旧数据
        this.sysPropertyMapper.deleteByKeyList(keyList);
        // 保存新数据
        if (!CollectionUtils.isEmpty(saveList)) {
            this.sysPropertyMapper.batchInsertOrUpdate(saveList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByKey(String key) {
        this.sysPropertyMapper.deleteByKey(key);
    }

}
