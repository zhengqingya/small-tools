package com.zhengqing.tool.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.tool.generator.entity.CgProject;
import com.zhengqing.tool.generator.model.dto.CgProjectListDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectSaveDTO;
import com.zhengqing.tool.generator.model.vo.CgProjectListVO;

import java.util.List;

/**
 * <p>
 * 代码生成器 - 项目管理 服务类
 * </p>
 *
 * @author zhengqingya
 * @date 2019-09-09
 */
public interface ICgProjectService extends IService<CgProject> {

    /**
     * 列表分页
     *
     * @param params: 查询参数
     * @return 分页列表
     * @author zhengqingya
     * @date 2020/11/15 13:17
     */
    IPage<CgProjectListVO> listPage(CgProjectListDTO params);

    /**
     * 列表
     *
     * @param params: 查询参数
     * @return 列表
     * @author zhengqingya
     * @date 2020/11/15 13:17
     */
    List<CgProjectListVO> list(CgProjectListDTO params);

    /**
     * 新增或更新数据
     *
     * @param params: 提交参数
     * @return 主键
     * @author zhengqingya
     * @date 2020/11/15 14:14
     */
    Integer addOrUpdateData(CgProjectSaveDTO params);

    /**
     * 根据项目id删除项目以及关联模板等
     *
     * @param projectId: 项目id
     * @return void
     * @author zhengqingya
     * @date 2020/11/15 13:12
     */
    void deleteData(Integer projectId);

    /**
     * 根据用户id删除项目以及关联模板等
     *
     * @param userId: 用户id
     * @return void
     * @author zhengqingya
     * @date 2020/11/15 13:11
     */
    void deleteDataByUserId(Integer userId);

}
