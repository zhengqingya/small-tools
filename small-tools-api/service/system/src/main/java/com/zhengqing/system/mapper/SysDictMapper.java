package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.system.entity.SysDict;
import com.zhengqing.system.model.vo.SysDictVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 数据字典-Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:53
 */
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * 根据数据字典编码获取数据字典列表
     *
     * @param status   状态 1：启用 0：禁用
     * @param codeList 数据字典编码
     * @return 数据字典列表信息
     * @author zhengqingya
     * @date 2020/9/12 17:58
     */
    List<SysDictVO> selectDictListByCode(@Param("status") Integer status, @Param("codeList") List<String> codeList);

    /**
     * 根据类型编码删除关联数据字典
     *
     * @param code 数据字典类型编码
     * @return void
     * @author zhengqingya
     * @date 2020/9/12 17:41
     */
    @Delete("DELETE FROM t_sys_dict WHERE code = #{code}")
    void deleteByCode(@Param("code") String code);

}
