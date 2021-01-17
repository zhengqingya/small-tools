package com.zhengqing.tool.generator.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.tool.generator.entity.CgProject;
import com.zhengqing.tool.generator.mapper.CgProjectMapper;
import com.zhengqing.tool.generator.model.dto.CgProjectListDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectPackageSaveDTO;
import com.zhengqing.tool.generator.model.dto.CgProjectSaveDTO;
import com.zhengqing.tool.generator.model.vo.CgProjectListVO;
import com.zhengqing.tool.generator.service.ICgProjectPackageService;
import com.zhengqing.tool.generator.service.ICgProjectService;
import com.zhengqing.tool.generator.service.ICgProjectTemplateService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 代码生成器 - 项目管理 服务实现类
 * </p>
 *
 * @author: zhengqing
 * @date: 2019-09-09
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CgProjectServiceImpl extends ServiceImpl<CgProjectMapper, CgProject> implements ICgProjectService {

    @Autowired
    private CgProjectMapper cgProjectMapper;

    @Autowired
    private ICgProjectPackageService cgProjectPackageService;

    @Autowired
    private ICgProjectTemplateService cgProjectTemplateService;

    @Override
    public IPage<CgProjectListVO> listPage(CgProjectListDTO filter) {
        IPage<CgProjectListVO> result = cgProjectMapper.selectProjects(new Page(), filter);
        // 根据项目名称去重
        // result = result.stream().collect( Collectors.collectingAndThen( Collectors.toCollection( () -> new TreeSet<>(
        // Comparator.comparing( Project::getName ) ) ), ArrayList::new ) );
        return result;
    }

    @Override
    public List<CgProjectListVO> list(CgProjectListDTO filter) {
        return cgProjectMapper.selectProjects(filter);
    }

    @Override
    public Integer addOrUpdateData(CgProjectSaveDTO params) {
        Integer id = params.getId();
        String name = params.getName();
        Integer currentUserId = params.getCurrentUserId();

        CgProject cgProject = new CgProject();
        cgProject.setId(id);
        cgProject.setName(name);
        cgProject.setDataReUserId(currentUserId);

        if (id == null) {
            cgProject.insert();

            // 新建项目时给一个初始化包
            CgProjectPackageSaveDTO cgProjectPackageSaveDTO = new CgProjectPackageSaveDTO();
            cgProjectPackageSaveDTO.setName(AppConstant.PROJECT_RE_PACKAGE_PARENT_NAME);
            cgProjectPackageSaveDTO.setParentId(AppConstant.PROJECT_RE_PACKAGE_PARENT_ID);
            cgProjectPackageSaveDTO.setProjectId(cgProject.getId());
            cgProjectPackageService.addOrUpdateData(cgProjectPackageSaveDTO);
        } else {
            cgProject.updateById();
        }
        return cgProject.getId();
    }

    @Override
    public void deleteData(Integer projectId) {
        // 一、删除关联的项目模板
        cgProjectTemplateService.deleteDataByProjectId(projectId);
        // 二、删除关联的项目包
        cgProjectPackageService.deleteDataByProjectId(projectId);
        // 三、删除该项目
        removeById(projectId);
    }

    @Override
    public void deleteDataByUserId(Integer userId) {
        CgProjectListDTO projectListDTO = new CgProjectListDTO();
        projectListDTO.setCurrentUserId(userId);
        List<CgProjectListVO> projectList = this.list(projectListDTO);
        projectList.forEach(e -> this.deleteData(e.getId()));
    }

    // private void generate(Database database, CgTableInfoBO cgTableInfoBO, Map<String, String> packageConfig,
    // TableConfig tableConfig) {
    // List<CgProjectTemplate> templateList = cgProjectTemplateMapper.selectList(null);
    // // model -> 表注释
    // String model = cgTableInfoBO.getComments().substring(cgTableInfoBO.getComments().indexOf("#") + 1);
    //
    // // 1、代码生成器
    // MyGenerator mpg = new MyGenerator();
    //
    // // 2、全局配置
    // GlobalConfig gc = new GlobalConfig();
    // // 生成code所在位置
    // gc.setOutputDir(Constants.PROJECT_ROOT_DIRECTORY + "/document/upload/code");
    // // 是否覆盖同名文件，默认是false
    // gc.setFileOverride(true);
    // // 不需要ActiveRecord特性的请改为false
    // gc.setActiveRecord(true);
    // // XML 二级缓存
    // gc.setEnableCache(false);
    // // XML ResultMap
    // gc.setBaseResultMap(true);
    // // XML columList
    // gc.setBaseColumnList(false);
    // gc.setAuthor("zhengqingya");
    // gc.setOpen(false);
    // gc.setSwagger2(true); 实体属性 Swagger2 注解
    /*
    自定义文件命名，注意 %s 会自动填充表实体属性！
    gc.setMapperName("%sDao");
    gc.setXmlName("%sDao");
    gc.setServiceName("MP%sService");
    gc.setServiceImplName("%sServiceDiy");
    gc.setControllerName("%sAction");
    */
    // gc.setIdType(IdType.AUTO);
    // mpg.setGlobalConfig(gc);
    //
    // // 3、数据源配置
    // DataSourceConfig dsc = new DataSourceConfig();
    // CgDatabaseTypeEnum cgDatabaseTypeEnum = CgDatabaseTypeEnum.getEnum(database.getDbType());
    // switch (cgDatabaseTypeEnum) {
    // case MySQL:
    // dsc.setDbType(DbType.MYSQL);
    // break;
    // case Oracle:
    // dsc.setDbType(DbType.ORACLE);
    // break;
    // default:
    // throw new IllegalStateException("Unexpected value: " + cgDatabaseTypeEnum);
    // }
    // com.mysql.jdbc.Driver
    // dsc.setDriverName(database.getDriver());
    // dsc.setUsername(database.getUser());
    // dsc.setPassword(database.getPassword());
    // jdbc:mysql://localhost:3306/ant?useUnicode=true&useSSL=false&characterEncoding=utf8
    // dsc.setUrl(database.getUrl());
    // mpg.setDataSource(dsc);

    // 4、包配置【模块名，包名】
    // mpg.setPackageInfo(packageConfig);

    // 5、策略配置
    // StrategyConfig strategy = new StrategyConfig();
    // String tablePrefix = "";
    // if (cgTableInfoBO.getTableName().contains("T_")) {
    // tablePrefix = cgTableInfoBO.getTableName().replace("T_", "");
    // tablePrefix = "T_" + tablePrefix.substring(0, tablePrefix.indexOf("_"));
    //
    // } else if (cgTableInfoBO.getTableName().contains("_")) {
    // tablePrefix = cgTableInfoBO.getTableName();
    // tablePrefix = tablePrefix.substring(0, tablePrefix.indexOf("_"));
    // }
    // 此处可以修改为您的表前缀 -> 下面代码目的：拿到表前缀并设置 ex:zq_demo -> zq
    // strategy.setTablePrefix(tablePrefix);

    // strategy.entityTableFieldAnnotationEnable(true);
    // 表名生成策略
    // strategy.setNaming(NamingStrategy.underline_to_camel);
    // 需要生成的表
    // strategy.setInclude(cgTableInfoBO.getTableName());
    // 表名和字段名是否使用下划线命名
    // strategy.setDbColumnUnderline(true);

    // strategy.setExclude(new String[]{"test"}); // 排除生成的表
    // 自定义实体父类
    // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
    // 自定义实体，公共字段
    // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
    // 自定义 mapper 父类
    // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
    // 自定义 service 父类
    // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
    // 自定义 service 实现类父类
    // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
    // 自定义 controller 父类
    // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
    // 【实体】是否生成字段常量（默认 false）
    // public static final String ID = "test_id";
    // strategy.setEntityColumnConstant(true);
    // 【实体】是否为构建者模型（默认 false）
    // public User setName(String name) {this.name = name; return this;}
    // strategy.setEntityBuilderModel(true);
    // mpg.setStrategy(strategy);

    // 模板列表
    // mpg.setTemplateList(templateList);
    // 生成页面配置参数
    // mpg.setMyTableConfig(tableConfig);
    // 6、执行生成
    // mpg.execute();
    // }

}
