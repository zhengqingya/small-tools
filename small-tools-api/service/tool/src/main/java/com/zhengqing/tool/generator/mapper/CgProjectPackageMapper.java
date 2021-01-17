package com.zhengqing.tool.generator.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.tool.generator.entity.CgProjectPackage;
import com.zhengqing.tool.generator.model.dto.CgProjectPackageListDTO;
import com.zhengqing.tool.generator.model.vo.CgProjectPackageListVO;

/**
 * <p>
 * 代码生成器 - 项目包管理表 Mapper 接口
 * </p>
 *
 * @author : zhengqing
 * @date : 2019-09-09
 */
public interface CgProjectPackageMapper extends BaseMapper<CgProjectPackage> {

    /**
     * 列表分页
     *
     * @param page:
     *            分页参数
     * @param filter:
     *            过滤参数
     * @return: 列表
     * @author : zhengqing
     * @date : 2020/11/15 12:02
     */
    List<CgProjectPackageListVO> selectProjectPackages(IPage page, @Param("filter") CgProjectPackageListDTO filter);

    /**
     * 列表
     *
     * @param filter:
     *            过滤参数
     * @return: 列表
     * @author : zhengqing
     * @date : 2020/11/15 12:02
     */
    List<CgProjectPackageListVO> selectProjectPackages(@Param("filter") CgProjectPackageListDTO filter);

    /**
     * 获取项目父包名
     *
     * @param projectId:
     *            项目id
     * @return: 父包名
     * @author : zhengqing
     * @date : 2020/11/15 19:04
     */
    String selectParentPackageName(@Param("projectId") Integer projectId);

}
