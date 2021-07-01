package com.zhengqing.tool.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.tool.generator.entity.CgProjectTemplate;
import com.zhengqing.tool.generator.entity.CgProjectVelocityContext;
import com.zhengqing.tool.generator.model.dto.CgProjectTemplateListDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectTemplateSaveDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectTemplateTestDataDTO;
import com.zhengqing.tool.generator.model.vo.CgProjectTemplateListVO;

import java.util.List;

/**
 * <p>
 * 项目代码模板表 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/20 16:07
 */
public interface ICgProjectTemplateService extends IService<CgProjectTemplate> {

    /**
     * 列表分页
     *
     * @param params: 查询参数
     * @return: 分页列表
     * @author zhengqingya
     * @date 2020/11/15 14:32
     */
    IPage<CgProjectTemplateListVO> listPage(CgProjectTemplateListDTO params);

    /**
     * 列表
     *
     * @param params: 查询参数
     * @return: 列表
     * @author zhengqingya
     * @date 2020/11/15 14:32
     */
    List<CgProjectTemplateListVO> list(CgProjectTemplateListDTO params);

    /**
     * 新增或更新
     *
     * @param params: 提交参数
     * @return: 主键id
     * @author zhengqingya
     * @date 2020/11/15 14:33
     */
    Integer addOrUpdateData(CgProjectTemplateSaveDTO params);

    /**
     * 测试模板数据
     *
     * @param params: 提交参数
     * @return: 根据模板生成的数据
     * @author zhengqingya
     * @date 2020/12/3 21:26
     */
    String testTemplateData(CgProjectTemplateTestDataDTO params);

    /**
     * 根据项目id删除数据
     *
     * @param projectId: 项目id
     * @return: void
     * @author zhengqingya
     * @date 2020/11/15 14:33
     */
    void deleteDataByProjectId(Integer projectId);

    /**
     * 校验模板数据是否正确
     *
     * @param projectTemplateId: 项目模板id
     * @return: void
     * @author zhengqingya
     * @date 2020/11/15 20:36
     */
    void checkTemplateData(Integer projectTemplateId);

    /**
     * 生成项目代码模板
     *
     * @param projectId: 项目id
     * @return: void
     * @author zhengqingya
     * @date 2020/11/15 14:33
     */
    void generateTemplate(Integer projectId);

    /**
     * 根据项目ID获取模板数据源配置信息
     *
     * @param params: 查询参数
     * @return: 模板数据源配置信息
     * @author zhengqingya
     * @date 2020/11/15 14:33
     */
    IPage<CgProjectVelocityContext> listPageCodeProjectVelocityContext(CgProjectTemplateListDTO params);

}
