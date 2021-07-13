package com.zhengqing.tool.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.tool.generator.entity.CgTableConfig;
import com.zhengqing.tool.generator.model.dto.CgTableConfigListDTO;
import com.zhengqing.tool.generator.model.dto.CgTableConfigSaveDTO;

import java.util.List;

/**
 * <p>
 * 代码生成器 - 项目数据表配置信息表 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/14 23:12
 */
public interface ICgTableConfigService extends IService<CgTableConfig> {

    /**
     * 列表分页
     *
     * @param params: 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2020/11/14 23:12
     */
    List<CgTableConfig> list(CgTableConfigListDTO params);

    /**
     * 新增或更新数据
     *
     * @param params: 提交参数
     * @return 保存后的数据信息
     * @author zhengqingya
     * @date 2020/11/15 14:14
     */
    CgTableConfig addOrUpdateData(CgTableConfigSaveDTO params);

}
