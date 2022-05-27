package com.zhengqing.tool.generator.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.core.constant.AppConstant;
import com.zhengqing.common.base.context.ContextHandler;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.util.MyBeanUtil;
import com.zhengqing.tool.generator.entity.CgFreeMarkerTemplate;
import com.zhengqing.tool.generator.enums.CgFreeMarkerTemplateCommonTypeEnum;
import com.zhengqing.tool.generator.mapper.CgFreeMarkerTemplateMapper;
import com.zhengqing.tool.generator.model.dto.CgFreeMarkerTemplateListDTO;
import com.zhengqing.tool.generator.model.dto.CgFreeMarkerTemplateSaveDTO;
import com.zhengqing.tool.generator.model.dto.CgFreeMarkerTemplateTestDataDTO;
import com.zhengqing.tool.generator.model.vo.CgFreeMarkerTemplateListVO;
import com.zhengqing.tool.generator.service.ICgFreeMarkerTemplateService;
import com.zhengqing.tool.generator.service.ICgGeneratorCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 代码生成器 - FreeMarker模板数据配置表 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-11-02 19:23:15
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CgFreeMarkerTemplateServiceImpl extends ServiceImpl<CgFreeMarkerTemplateMapper, CgFreeMarkerTemplate>
        implements ICgFreeMarkerTemplateService {

    @Autowired
    private CgFreeMarkerTemplateMapper cgFreeMarkerTemplateMapper;

    @Autowired
    private ICgGeneratorCodeService cgGeneratorCodeService;

    @Override
    public IPage<CgFreeMarkerTemplateListVO> listPage(CgFreeMarkerTemplateListDTO params) {
        IPage<CgFreeMarkerTemplateListVO> result = this.cgFreeMarkerTemplateMapper.selectTemplates(new Page<>(), params);
        List<CgFreeMarkerTemplateListVO> list = result.getRecords();
        this.handleResultData(list);
        return result;
    }

    @Override
    public List<CgFreeMarkerTemplateListVO> list(CgFreeMarkerTemplateListDTO params) {
        List<CgFreeMarkerTemplateListVO> list = this.cgFreeMarkerTemplateMapper.selectTemplates(params);
        this.handleResultData(list);
        return list;
    }

    /**
     * 处理数据
     *
     * @param list: 数据
     * @return void
     * @author zhengqingya
     * @date 2020-11-02 19:23:15
     */
    private void handleResultData(List<CgFreeMarkerTemplateListVO> list) {
        list.forEach(e -> e.setIsCommonName(CgFreeMarkerTemplateCommonTypeEnum.getEnum(e.getIsCommon()).getDesc()));
    }

    @Override
    public Integer addOrUpdateData(CgFreeMarkerTemplateSaveDTO params) {
        Integer freeMarkerTemplateId = params.getFreeMarkerTemplateId();
        CgFreeMarkerTemplate cgFreeMarkerTemplate = MyBeanUtil.copyProperties(params, CgFreeMarkerTemplate.class);

        Integer currentUserId = params.getCurrentUserId();
        cgFreeMarkerTemplate.setTemplateReUserId(currentUserId);

        if (freeMarkerTemplateId == null) {
            // 这里判断如果不是管理员则无法操作共用数据,直接设置为`未公用`数据类型
            if (!currentUserId.equals(AppConstant.SYSTEM_SUPER_ADMIN_USER_ID)) {
                cgFreeMarkerTemplate.setIsCommon(CgFreeMarkerTemplateCommonTypeEnum.未公用.getType());
            }
            cgFreeMarkerTemplate.insert();
        } else {
            // 这里判断如果不是管理员则无法操作共用数据
            if (!currentUserId.equals(AppConstant.SYSTEM_SUPER_ADMIN_USER_ID)) {
                CgFreeMarkerTemplate template = this.getById(freeMarkerTemplateId);
                if (template.getIsCommon().equals(CgFreeMarkerTemplateCommonTypeEnum.公用.getType())) {
                    throw new MyException("您没有权限修改共有数据！");
                }
            }
            cgFreeMarkerTemplate.updateById();
        }
        return cgFreeMarkerTemplate.getFreeMarkerTemplateId();
    }

    @Override
    public void deleteData(Integer freeMarkerTemplateId) {
        // 这里判断如果不是管理员则无法操作共用数据
        if (!ContextHandler.getUserId().equals(AppConstant.SYSTEM_SUPER_ADMIN_USER_ID)) {
            CgFreeMarkerTemplate template = this.getById(freeMarkerTemplateId);
            if (template.getIsCommon().equals(CgFreeMarkerTemplateCommonTypeEnum.公用.getType())) {
                throw new MyException("您没有权限删除共有数据！");
            }
        }
        this.removeById(freeMarkerTemplateId);
    }

    @Override
    public String testTemplateData(CgFreeMarkerTemplateTestDataDTO params) {
        String templateContent = params.getTemplateContent();
        Integer currentUserId = params.getCurrentUserId();
        String templateData = "";
        CgFreeMarkerTemplateListDTO templateListDTO = new CgFreeMarkerTemplateListDTO();
        templateListDTO.setCurrentUserId(currentUserId);
        List<CgFreeMarkerTemplateListVO> templateList = this.list(templateListDTO);
        if (!CollectionUtils.isEmpty(templateList)) {
            Map<String, Object> templateDataMap = templateList.stream().collect(Collectors
                    .toMap(CgFreeMarkerTemplateListVO::getTemplateKey, CgFreeMarkerTemplateListVO::getTemplateValue));
            templateData = this.cgGeneratorCodeService.generateTemplateData(templateDataMap, templateContent);
        }
        return templateData;
    }

}
