package com.zhengqing.tool.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.tool.generator.entity.CgProjectReDb;
import com.zhengqing.tool.generator.model.dto.CgProjectReDbListDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectReDbSaveDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectReDbTableInfoDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectReDbTableListDTO;
import com.zhengqing.tool.generator.model.vo.CgProjectReDbListVO;
import com.zhengqing.tool.generator.model.vo.CgTableInfoVO;
import com.zhengqing.tool.generator.model.vo.CgTableListVO;

import java.util.List;

/**
 * <p>
 * 代码生成器 - 项目关联数据库表 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-11-14 13:55:47
 */
public interface ICgProjectReDbService extends IService<CgProjectReDb> {

    /**
     * 列表分页
     *
     * @param params: 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2020-11-14 13:55:47
     */
    IPage<CgProjectReDbListVO> listPage(CgProjectReDbListDTO params);

    /**
     * 列表分页
     *
     * @param params: 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2020-11-14 13:55:47
     */
    List<CgProjectReDbListVO> list(CgProjectReDbListDTO params);

    /**
     * 查询表列表信息
     *
     * @param params: 查询参数
     * @return 表列表信息
     * @author zhengqingya
     * @date 2020/11/14 22:43
     */
    List<CgTableListVO> tableList(CgProjectReDbTableListDTO params);

    /**
     * 查询表具体信息
     *
     * @param params: 查询参数
     * @return 表具体信息
     * @author zhengqingya
     * @date 2020/11/14 22:35
     */
    CgTableInfoVO tableInfo(CgProjectReDbTableInfoDTO params);

    /**
     * 新增或更新
     *
     * @param params: 保存参数
     * @return 主键id
     * @author zhengqingya
     * @date 2020-11-14 13:55:47
     */
    Integer addOrUpdateData(CgProjectReDbSaveDTO params);

}
