package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.system.entity.SysDictType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 数据字典类型 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:53
 */
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {

    /**
     * 查询已启用的数据字典类型列表信息
     *
     * @return: 数据字典类型列表信息
     * @author zhengqingya
     * @date 2020/9/12 18:51
     */
    @Select("SELECT id,code,name,status FROM t_sys_dict_type WHERE status=1 AND is_valid=1")
    List<SysDictType> upDictTypeList();

}
