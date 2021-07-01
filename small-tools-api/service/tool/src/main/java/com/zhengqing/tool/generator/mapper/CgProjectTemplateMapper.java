package com.zhengqing.tool.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.tool.generator.entity.CgProjectTemplate;
import com.zhengqing.tool.generator.model.dto.CgProjectTemplateListDTO;
import com.zhengqing.tool.generator.model.vo.CgProjectTemplateListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 项目代码模板表 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/20 15:23
 */
public interface CgProjectTemplateMapper extends BaseMapper<CgProjectTemplate> {

    /**
     * 列表分页
     *
     * @param page:   分页参数
     * @param filter: 过滤参数
     * @return: 分页列表
     * @author zhengqingya
     * @date 2020/11/15 15:28
     */
    IPage<CgProjectTemplateListVO> selectDataList(IPage page, @Param("filter") CgProjectTemplateListDTO filter);

    /**
     * 列表
     *
     * @param filter: 过滤参数
     * @return: 列表
     * @author zhengqingya
     * @date 2020/11/15 15:28
     */
    List<CgProjectTemplateListVO> selectDataList(@Param("filter") CgProjectTemplateListDTO filter);

}
