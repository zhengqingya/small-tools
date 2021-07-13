package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysDict;
import com.zhengqing.system.model.dto.SysDictSaveDTO;
import com.zhengqing.system.model.vo.SysDictVO;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>
 * 数据字典-服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:53
 */
public interface ISysDictService extends IService<SysDict> {

    /**
     * 通过类型code获取数据字典列表数据(启用+禁用的数据一起)
     *
     * @param code: 类型编码
     * @return 数据字典列表数据
     * @author zhengqingya
     * @date 2020/9/12 17:38
     */
    List<SysDictVO> getAllDictListByCode(String code);

    /**
     * 通过类型code获取数据字典列表数据 - 数据库方式（只有启用的数据）
     *
     * @param code: 类型编码
     * @return 数据字典列表数据
     * @author zhengqingya
     * @date 2020/9/12 17:38
     */
    List<SysDictVO> getUpDictListFromDbByCode(String code);

    /**
     * 通过类型code获取数据字典列表数据 - 从缓存中取数据（只有启用的数据）
     *
     * @param code: 类型编码
     * @return 数据字典列表数据
     * @author zhengqingya
     * @date 2020/9/12 17:38
     */
    List<SysDictVO> getUpDictListFromCacheByCode(@NotBlank(message = "查询编码不能为空!") String code);

    /**
     * 新增或更新
     *
     * @param params: 提交参数
     * @return java.lang.Integer
     * @author zhengqingya
     * @date 2020/9/12 17:38
     */
    Integer addOrUpdateData(SysDictSaveDTO params);

    /**
     * 根据id删除数据字典
     *
     * @param id: 数据字典id
     * @return void
     * @author zhengqingya
     * @date 2020/9/12 17:37
     */
    void deleteDictById(Integer id);

    /**
     * 根据类型id删除数据字典
     *
     * @param dictTypeId: 数据字典类型id
     * @return void
     * @author zhengqingya
     * @date 2020/9/12 17:36
     */
    void deleteDictByDictTypeId(Integer dictTypeId);

    /**
     * 根据字典类型更新缓存
     *
     * @param code: 字典类型
     * @return void
     * @author zhengqingya
     * @date 2020/9/3 21:48
     */
    void updateCache(String code);

    /**
     * 初始化字典类型缓存
     *
     * @return void
     * @author zhengqingya
     * @date 2020/9/12 17:37
     */
    void initCache();

}
