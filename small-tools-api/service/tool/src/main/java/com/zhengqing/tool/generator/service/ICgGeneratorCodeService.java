package com.zhengqing.tool.generator.service;

import com.zhengqing.tool.generator.model.dto.CgGenerateCodeDTO;

import java.util.Map;

/**
 * <p>
 * 代码生成器 - 生成代码 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/14 23:58
 */
public interface ICgGeneratorCodeService {

    /**
     * 处理模板数据(将模板和数据模型合并) ex：`${entity}VO.java` -> `Test.java`
     *
     * @param templateDataMap: 数据模型
     * @param templateContent: 模板内容
     * @return 模板数据内容
     * @author zhengqingya
     * @date 2020/11/17 20:36
     */
    String generateTemplateData(Map<String, Object> templateDataMap, String templateContent);

    /**
     * 生成代码
     *
     * @param params: 提交参数
     * @return 下载地址url
     * @author zhengqingya
     * @date 2020/11/14 23:58
     */
    String generateCode(CgGenerateCodeDTO params);

}
