package com.zhengqing.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysDictType;
import com.zhengqing.system.model.dto.SysDictTypeSaveDTO;

/**
 * <p>
 * 数据字典类型-服务类
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/4/15 20:53
 */
public interface ISysDictTypeService extends IService<SysDictType> {

    /**
     * 查询已启用的数据字典类型列表信息
     *
     * @return: 数据字典类型列表信息
     * @author : zhengqing
     * @date : 2020/9/12 18:51
     */
    List<SysDictType> upDictTypeList();

    /**
     * 新增或更新
     *
     * @param params:
     * @return: java.lang.Integer
     * @author : zhengqing
     * @date : 2020/9/12 17:28
     */
    Integer addOrUpdateData(SysDictTypeSaveDTO params);

    /**
     * 根据id删除数据字典类型及其数据字典
     *
     * @param id:
     *            数据字典id
     * @return: void
     * @author : zhengqing
     * @date : 2020/9/12 17:26
     */
    void deleteType(Integer id);

}
