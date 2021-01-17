package com.zhengqing.tool.generator.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.tool.generator.entity.CgFreeMarkerTemplate;
import com.zhengqing.tool.generator.model.dto.CgFreeMarkerTemplateListDTO;
import com.zhengqing.tool.generator.model.vo.CgFreeMarkerTemplateListVO;

/**
 * <p>
 * 代码生成器 - FreeMarker模板数据配置表 Mapper 接口
 * </p>
 *
 * @author : zhengqing
 * @description:
 * @date : 2020-11-02 19:23:15
 */
public interface CgFreeMarkerTemplateMapper extends BaseMapper<CgFreeMarkerTemplate> {

    /**
     * 列表分页
     *
     * @param page:
     *            分页数据
     * @param filter:
     *            查询过滤参数
     * @return: 查询结果
     * @author : zhengqing
     * @date : 2020-11-02 19:23:15
     */
    IPage<CgFreeMarkerTemplateListVO> selectTemplates(IPage page, @Param("filter") CgFreeMarkerTemplateListDTO filter);

    /**
     * 列表
     *
     * @param filter:
     *            查询过滤参数
     * @return: 查询结果
     * @author : zhengqing
     * @date : 2020-11-02 19:23:15
     */
    List<CgFreeMarkerTemplateListVO> selectTemplates(@Param("filter") CgFreeMarkerTemplateListDTO filter);

}
