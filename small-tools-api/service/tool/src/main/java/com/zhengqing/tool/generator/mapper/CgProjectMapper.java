package com.zhengqing.tool.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.tool.generator.entity.CgProject;
import com.zhengqing.tool.generator.model.dto.CgProjectListDTO;
import com.zhengqing.tool.generator.model.vo.CgProjectListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 代码生成器 - 项目管理 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @date 2019-09-09
 */
public interface CgProjectMapper extends BaseMapper<CgProject> {

    /**
     * 列表分页
     *
     * @param page
     * @param filter
     * @return
     */
    IPage<CgProjectListVO> selectProjects(IPage page, @Param("filter") CgProjectListDTO filter);

    /**
     * 列表
     *
     * @param filter
     * @return
     */
    List<CgProjectListVO> selectProjects(@Param("filter") CgProjectListDTO filter);
}
