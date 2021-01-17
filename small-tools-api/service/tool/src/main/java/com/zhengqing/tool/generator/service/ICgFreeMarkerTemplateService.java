package com.zhengqing.tool.generator.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.tool.generator.entity.CgFreeMarkerTemplate;
import com.zhengqing.tool.generator.model.dto.CgFreeMarkerTemplateListDTO;
import com.zhengqing.tool.generator.model.dto.CgFreeMarkerTemplateSaveDTO;
import com.zhengqing.tool.generator.model.dto.CgFreeMarkerTemplateTestDataDTO;
import com.zhengqing.tool.generator.model.vo.CgFreeMarkerTemplateListVO;

/**
 * <p>
 * 代码生成器 - FreeMarker模板数据配置表 服务类
 * </p>
 *
 * @author: zhengqing
 * @description:
 * @date: 2020-11-02 19:23:15
 */
public interface ICgFreeMarkerTemplateService extends IService<CgFreeMarkerTemplate> {

    /**
     * 列表分页
     *
     * @param params:
     *            查询参数
     * @return: 查询结果
     * @author : zhengqing
     * @date : 2020-11-02 19:23:15
     */
    IPage<CgFreeMarkerTemplateListVO> listPage(CgFreeMarkerTemplateListDTO params);

    /**
     * 列表分页
     *
     * @param params:
     *            查询参数
     * @return: 查询结果
     * @author : zhengqing
     * @date : 2020-11-02 19:23:15
     */
    List<CgFreeMarkerTemplateListVO> list(CgFreeMarkerTemplateListDTO params);

    /**
     * 新增或更新
     *
     * @param params:
     *            保存参数
     * @return: 主键id
     * @author : zhengqing
     * @date : 2020-11-02 19:23:15
     */
    Integer addOrUpdateData(CgFreeMarkerTemplateSaveDTO params);

    /**
     * 删除数据
     *
     * @param freeMarkerTemplateId:
     *            主键id
     * @return: void
     * @author : zhengqing
     * @date : 2020/11/8 16:50
     */
    void deleteData(Integer freeMarkerTemplateId);

    /**
     * 测试模板数据
     *
     * @param params:
     *            提交参数
     * @return: 根据模板生成的数据
     * @author : zhengqing
     * @date : 2020/11/17 22:08
     */
    String testTemplateData(CgFreeMarkerTemplateTestDataDTO params);

}
