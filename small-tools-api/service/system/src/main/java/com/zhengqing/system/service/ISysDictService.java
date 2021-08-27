package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysDict;
import com.zhengqing.system.model.dto.SysDictSaveBatchDTO;
import com.zhengqing.system.model.dto.SysDictSaveDTO;
import com.zhengqing.system.model.vo.SysDictVO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

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
     * @param code 类型编码
     * @return 数据字典列表数据
     * @author zhengqingya
     * @date 2020/9/12 17:38
     */
    List<SysDictVO> listByCode(@NotBlank(message = "查询编码不能为空!") String code);

    /**
     * 通过类型code获取数据字典列表数据(启用数据)
     *
     * @param codeList 类型编码
     * @return 字典类型 -> 字典列表数据
     * @author zhengqingya
     * @date 2020/9/12 17:38
     */
    Map<String, List<SysDictVO>> listByOpenCode(@NotEmpty(message = "查询编码不能为空!") List<String> codeList);

    /**
     * 通过类型code获取数据字典列表数据 - 数据库方式（只有启用的数据）
     *
     * @param codeList 类型编码
     * @return 字典类型 -> 字典列表数据
     * @author zhengqingya
     * @date 2020/9/12 17:38
     */
    Map<String, List<SysDictVO>> listFromDbByOpenCode(@NotEmpty(message = "查询编码不能为空!") List<String> codeList);

    /**
     * 通过类型code获取数据字典列表数据 - 从缓存中取数据（只有启用的数据）
     *
     * @param codeList 类型编码
     * @return 字典类型 -> 字典列表数据
     * @author zhengqingya
     * @date 2020/9/12 17:38
     */
    Map<String, List<SysDictVO>> listFromCacheByCode(@NotEmpty(message = "查询编码不能为空!") List<String> codeList);

    /**
     * 详情
     *
     * @param dictId 字典id
     * @return 字典
     * @author zhengqingya
     * @date 2021/8/19 11:11
     */
    SysDict detail(Integer dictId);

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
     * 批量更新
     *
     * @param dictDataMap 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/8/27 11:24 下午
     */
    void updateBatch(Map<String, List<SysDictSaveBatchDTO>> dictDataMap);

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
     * 根据类型编码删除数据字典
     *
     * @param code 数据字典类型编码
     * @return void
     * @author zhengqingya
     * @date 2020/9/12 17:36
     */
    void deleteDictByCode(String code);

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
