package com.zhengqing.tool.generator.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.tool.generator.entity.CgProjectVelocityContext;
import com.zhengqing.tool.generator.model.dto.CgProjectTemplateListDTO;

/**
 * <p>
 * 代码生成器 - 项目 - 模板数据源 Mapper 接口
 * </p>
 *
 * @author : zhengqing
 * @date : 2019-09-17 14:34:18
 */
public interface CgProjectVelocityContextMapper extends BaseMapper<CgProjectVelocityContext> {

    /**
     * 列表分页
     *
     * @param page
     * @param filter
     * @return
     */
    IPage<CgProjectVelocityContext> selectCodeProjectVelocityContexts(IPage page,
        @Param("filter") CgProjectTemplateListDTO filter);

    /**
     * 列表
     *
     * @param filter
     * @return
     */
    List<CgProjectVelocityContext> selectCodeProjectVelocityContexts(@Param("filter") CgProjectTemplateListDTO filter);

}
