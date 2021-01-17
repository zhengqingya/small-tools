package com.zhengqing.tool.generator.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.exception.MyException;
import com.zhengqing.common.util.MyBeanUtil;
import com.zhengqing.tool.generator.entity.CgProjectPackage;
import com.zhengqing.tool.generator.entity.CgProjectTemplate;
import com.zhengqing.tool.generator.entity.CgProjectVelocityContext;
import com.zhengqing.tool.generator.entity.CgTableConfig;
import com.zhengqing.tool.generator.enums.CgGenerateTemplateDataTypeEnum;
import com.zhengqing.tool.generator.enums.CgProjectTemplateDataTypeEnum;
import com.zhengqing.tool.generator.mapper.CgProjectTemplateMapper;
import com.zhengqing.tool.generator.mapper.CgProjectVelocityContextMapper;
import com.zhengqing.tool.generator.model.dto.CgGenerateCodeDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectTemplateListDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectTemplateSaveDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectTemplateTestDataDTO;
import com.zhengqing.tool.generator.model.dto.CgTableConfigListDTO;
import com.zhengqing.tool.generator.model.vo.CgProjectTemplateListVO;
import com.zhengqing.tool.generator.service.ICgGeneratorCodeService;
import com.zhengqing.tool.generator.service.ICgProjectPackageService;
import com.zhengqing.tool.generator.service.ICgProjectTemplateService;
import com.zhengqing.tool.generator.service.ICgTableConfigService;

/**
 * <p>
 * 项目代码模板表 服务实现类
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2019/8/20 15:22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CgProjectTemplateServiceImpl extends ServiceImpl<CgProjectTemplateMapper, CgProjectTemplate>
    implements ICgProjectTemplateService {

    @Autowired
    private CgProjectTemplateMapper cgProjectTemplateMapper;

    @Autowired
    private ICgProjectPackageService cgProjectPackageService;

    @Autowired
    private CgProjectVelocityContextMapper contextMapper;

    @Autowired
    private ICgTableConfigService cgTableConfigService;

    @Autowired
    private ICgGeneratorCodeService cgGeneratorCodeService;

    @Override
    public IPage<CgProjectTemplateListVO> listPage(CgProjectTemplateListDTO params) {
        IPage<CgProjectTemplateListVO> result = cgProjectTemplateMapper.selectDataList(new Page(), params);
        List<CgProjectTemplateListVO> list = result.getRecords();
        handleResultData(list);
        return result;

    }

    @Override
    public List<CgProjectTemplateListVO> list(CgProjectTemplateListDTO params) {
        List<CgProjectTemplateListVO> list = cgProjectTemplateMapper.selectDataList(params);
        handleResultData(list);
        return list;
    }

    /**
     * 处理数据
     *
     * @param list:
     *            数据
     * @return: void
     * @author : zhengqing
     * @date : 2020/11/15 15:59
     */
    private void handleResultData(List<CgProjectTemplateListVO> list) {}

    @Override
    public Integer addOrUpdateData(CgProjectTemplateSaveDTO params) {
        Integer projectTemplateId = params.getProjectTemplateId();
        Integer projectId = params.getProjectId();
        Integer projectPackageId = params.getProjectPackageId();
        String fileName = params.getFileName();
        String fileSuffix = params.getFileSuffix();
        String content = params.getContent();
        Integer isBasic = params.getIsBasic();
        Integer currentUserId = params.getCurrentUserId();

        CgProjectPackage cgProjectPackage = new CgProjectPackage();
        CgProjectPackage model = cgProjectPackage.selectById(projectPackageId);
        if (model.getParentId().equals(AppConstant.PROJECT_RE_PACKAGE_PARENT_ID)) {
            throw new MyException("Sorry，您无法添加父包模板！");
        }

        CgProjectTemplate projectTemplate = new CgProjectTemplate();
        projectTemplate.setProjectTemplateId(projectTemplateId);
        projectTemplate.setProjectId(projectId);
        projectTemplate.setProjectPackageId(projectPackageId);
        projectTemplate.setFileName(fileName);
        projectTemplate.setFileSuffix(fileSuffix);
        projectTemplate.setContent(content);
        projectTemplate.setIsBasic(isBasic);
        projectTemplate.setDataReUserId(currentUserId);

        if (projectTemplateId == null) {
            projectTemplate.insert();
        } else {
            projectTemplate.updateById();
        }
        return projectTemplate.getProjectTemplateId();
    }

    @Override
    public String testTemplateData(CgProjectTemplateTestDataDTO params) {
        // 这里先保存数据，然后再去生成模板数据 -> 防止部分模板数据不存在！！！
        this.addOrUpdateData(MyBeanUtil.copyProperties(params, CgProjectTemplateSaveDTO.class));
        Integer projectTemplateId = params.getProjectTemplateId();

        CgTableConfigListDTO tableConfigListDTO = new CgTableConfigListDTO();
        tableConfigListDTO.setProjectId(params.getProjectId());
        tableConfigListDTO.setDataType(CgGenerateTemplateDataTypeEnum.测试模板生成配置数据.getType());
        List<CgTableConfig> cgTableConfigList = cgTableConfigService.list(tableConfigListDTO);
        if (CollectionUtils.isEmpty(cgTableConfigList)) {
            throw new MyException("没有初始化模板数据配置，请到《生成代码》页面中进行配置！！！");
        }
        CgTableConfig cgTableConfig = cgTableConfigList.get(0);
        CgGenerateCodeDTO cgGenerateCodeDTO = new CgGenerateCodeDTO();
        cgGenerateCodeDTO.setProjectReDbDataSourceId(cgTableConfig.getProjectReDbDataSourceId());
        cgGenerateCodeDTO.setTableName(cgTableConfig.getTableName());
        cgGenerateCodeDTO.setQueryColumnList(Arrays.asList(cgTableConfig.getQueryColumns().split(",")));
        cgGenerateCodeDTO.setPackageName(cgTableConfig.getPackageName());
        cgGenerateCodeDTO.setModuleName(cgTableConfig.getModuleName());
        cgGenerateCodeDTO.setDataType(CgGenerateTemplateDataTypeEnum.测试模板生成配置数据.getType());
        cgGenerateCodeDTO.setIfTestTemplateData(true);
        cgGenerateCodeDTO.setProjectTemplateId(projectTemplateId);
        return cgGeneratorCodeService.generateCode(cgGenerateCodeDTO);
    }

    @Override
    public void deleteDataByProjectId(Integer projectId) {
        cgProjectTemplateMapper
            .delete(new LambdaQueryWrapper<CgProjectTemplate>().eq(CgProjectTemplate::getProjectId, projectId));
    }

    @Override
    public void checkTemplateData(Integer projectTemplateId) {}

    @Override
    public void generateTemplate(Integer projectId) {
        // 查询指定项目ID的包
        List<CgProjectPackage> packageList = cgProjectPackageService
            .list(new LambdaQueryWrapper<CgProjectPackage>().eq(CgProjectPackage::getProjectId, projectId));

        List<CgProjectTemplate> bsTemplateList = this.list(new LambdaQueryWrapper<CgProjectTemplate>()
            .eq(CgProjectTemplate::getIsBasic, CgProjectTemplateDataTypeEnum.基础模板.getDataType()));
        if (!CollectionUtils.isEmpty(bsTemplateList)) {
            bsTemplateList.forEach(bsTemplate -> packageList.forEach(p -> {
                // TODO (暂不能直接根据id去判断 后期要做的话 根据名称去判断！！！) 如果初始模板的类型id 与 项目包id 一致 则生成项目模板
                if (bsTemplate.getProjectPackageId().equals(p.getId())) {
                    CgProjectTemplate template = new CgProjectTemplate();
                    template.setProjectId(projectId);
                    template.setProjectPackageId(p.getId());
                    template.setFileName(bsTemplate.getFileName());
                    template.setFileSuffix(bsTemplate.getFileSuffix());
                    template.setContent(bsTemplate.getContent());
                    cgProjectTemplateMapper.insert(template);
                }
            }));
        }
    }

    @Override
    public IPage<CgProjectVelocityContext> listPageCodeProjectVelocityContext(CgProjectTemplateListDTO params) {
        if (params.getProjectId() == null) {
            throw new MyException("请先选择项目！（提示：一个项目对应一套数据源哦~）");
        }
        IPage<CgProjectVelocityContext> result = contextMapper.selectCodeProjectVelocityContexts(new Page(), params);
        result.getRecords().forEach(e -> {
            String context = e.getContext();
            if (context.startsWith("{")) {
                // json字符串对象转对象
                JSONObject jsonObject = JSON.parseObject(context);
                e.setContextJsonObject(jsonObject);
            } else if (context.startsWith("[")) {
                // json数组转对象
                JSONArray jsonObject = JSONArray.parseArray(context);
                e.setContextJsonObject(jsonObject);
            } else {
                e.setContextJsonObject(context);
            }
        });
        return result;
    }

}
