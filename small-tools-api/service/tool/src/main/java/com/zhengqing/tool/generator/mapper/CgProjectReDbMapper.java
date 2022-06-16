package com.zhengqing.tool.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.tool.generator.entity.CgProjectReDb;
import com.zhengqing.tool.generator.model.dto.CgProjectReDbListDTO;
import com.zhengqing.tool.generator.model.vo.CgProjectReDbListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 代码生成器 - 项目关联数据库表 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-11-14 13:55:47
 */
public interface CgProjectReDbMapper extends BaseMapper<CgProjectReDb> {

    /**
     * 列表分页
     *
     * @param page    分页数据
     * @param filter: 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2020-11-14 13:55:47
     */
    IPage<CgProjectReDbListVO> selectCodeProjectReDbs(IPage page, @Param("filter") CgProjectReDbListDTO filter);

    /**
     * 列表
     *
     * @param filter: 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2020-11-14 13:55:47
     */
    List<CgProjectReDbListVO> selectCodeProjectReDbs(@Param("filter") CgProjectReDbListDTO filter);

}
