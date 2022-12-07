/*
 Navicat Premium Data Transfer

 Source Server         : localhost_mysql_3306
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 127.0.0.1:3306
 Source Schema         : small-tools

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 07/12/2022 16:58:57
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;


-- 创建数据库
CREATE
database if NOT EXISTS `small-tools`;
-- 使用数据库
use
`small-tools`;

-- ----------------------------
-- Table structure for t_cg_free_marker_template
-- ----------------------------
DROP TABLE IF EXISTS `t_cg_free_marker_template`;
CREATE TABLE `t_cg_free_marker_template`
(
    `free_marker_template_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `template_key`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '键',
    `template_value`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '值',
    `template_re_user_id`     int(11) NOT NULL COMMENT '关联人员id',
    `is_common`               tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否公共使用（1：是 0：否）',
    `create_by`               int(11) NOT NULL COMMENT '创建人',
    `create_time`             datetime                                                      NOT NULL COMMENT '创建时间',
    `update_by`               int(11) NOT NULL COMMENT '修改人',
    `update_time`             datetime                                                      NOT NULL COMMENT '修改时间',
    `is_deleted`              tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
    PRIMARY KEY (`free_marker_template_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成器 - FreeMarker模板数据配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_cg_free_marker_template
-- ----------------------------
INSERT INTO `t_cg_free_marker_template`
VALUES (1, 'test', '测试', 1, 0, 1, '2020-11-02 21:06:47', 1, '2020-11-02 21:27:22', 0);
INSERT INTO `t_cg_free_marker_template`
VALUES (2, 'hello', '你好', 1, 1, 1, '2020-11-02 21:27:37', 1, '2020-11-08 16:59:33', 0);
INSERT INTO `t_cg_free_marker_template`
VALUES (6, 'author', 'zhengqingya', 1, 0, 1, '2020-11-15 21:40:30', 1, '2022-07-15 21:19:00', 0);

-- ----------------------------
-- Table structure for t_cg_project
-- ----------------------------
DROP TABLE IF EXISTS `t_cg_project`;
CREATE TABLE `t_cg_project`
(
    `id`              int(11) NOT NULL AUTO_INCREMENT COMMENT '项目ID',
    `name`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '项目名称',
    `data_re_user_id` int(11) NOT NULL COMMENT '所属用户ID',
    `create_by`       int(11) NOT NULL COMMENT '创建人',
    `create_time`     datetime                                                     NOT NULL COMMENT '创建时间',
    `update_by`       int(11) NOT NULL COMMENT '修改人',
    `update_time`     datetime                                                     NOT NULL COMMENT '修改时间',
    `is_deleted`      tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成器 - 项目管理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_cg_project
-- ----------------------------
INSERT INTO `t_cg_project`
VALUES (1, 'Small Tools', 1, 1, '2020-08-22 15:01:51', 1, '2022-07-15 17:35:52', 0);

-- ----------------------------
-- Table structure for t_cg_project_package
-- ----------------------------
DROP TABLE IF EXISTS `t_cg_project_package`;
CREATE TABLE `t_cg_project_package`
(
    `id`              int(11) NOT NULL AUTO_INCREMENT COMMENT '包ID',
    `name`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '包名',
    `parent_id`       int(11) NULL DEFAULT NULL COMMENT '父包ID',
    `project_id`      int(11) NULL DEFAULT NULL COMMENT '关联项目ID',
    `data_re_user_id` int(11) NULL DEFAULT NULL COMMENT '关联用户id',
    `create_by`       int(11) NOT NULL COMMENT '创建人',
    `create_time`     datetime NOT NULL COMMENT '创建时间',
    `update_by`       int(11) NOT NULL COMMENT '修改人',
    `update_time`     datetime NOT NULL COMMENT '修改时间',
    `is_deleted`      tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成器 - 项目包管理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_cg_project_package
-- ----------------------------
INSERT INTO `t_cg_project_package`
VALUES (1, 'com.zhengqing.modules', 0, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-11-02 19:22:56', 0);
INSERT INTO `t_cg_project_package`
VALUES (2, 'model', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-09-01 21:34:51', 0);
INSERT INTO `t_cg_project_package`
VALUES (3, 'entity', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_cg_project_package`
VALUES (4, 'service', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_cg_project_package`
VALUES (5, 'service.impl', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_cg_project_package`
VALUES (6, 'mapper', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_cg_project_package`
VALUES (7, 'mapper.xml', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_cg_project_package`
VALUES (18, 'api', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_cg_project_package`
VALUES (19, 'js', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_cg_project_package`
VALUES (20, 'vue', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_cg_project_package`
VALUES (21, 'dto', 2, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-09-01 21:34:58', 0);
INSERT INTO `t_cg_project_package`
VALUES (22, 'vo', 2, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-09-01 21:35:20', 0);
INSERT INTO `t_cg_project_package`
VALUES (67, 'bo', 2, 1, 1, 1, '2021-06-13 05:37:08', 1, '2021-06-13 05:37:16', 0);

-- ----------------------------
-- Table structure for t_cg_project_re_db
-- ----------------------------
DROP TABLE IF EXISTS `t_cg_project_re_db`;
CREATE TABLE `t_cg_project_re_db`
(
    `project_re_db_data_source_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `project_id`                   int(11) NOT NULL COMMENT '所属项目ID',
    `db_data_source_id`            int(11) NOT NULL COMMENT '数据源id',
    `db_name`                      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '库名',
    `remark`                       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `data_re_user_id`              int(11) NOT NULL COMMENT '所属用户ID',
    `create_by`                    int(11) NOT NULL COMMENT '创建人',
    `create_time`                  datetime NOT NULL COMMENT '创建时间',
    `update_by`                    int(11) NOT NULL COMMENT '修改人',
    `update_time`                  datetime NOT NULL COMMENT '修改时间',
    `is_deleted`                   tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
    PRIMARY KEY (`project_re_db_data_source_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成器 - 项目关联数据库表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_cg_project_re_db
-- ----------------------------
INSERT INTO `t_cg_project_re_db`
VALUES (1, 1, 19, 'small-tools', '小工具', 1, 1, '2020-11-14 14:58:51', 1, '2021-01-17 03:30:35', 0);

-- ----------------------------
-- Table structure for t_cg_project_template
-- ----------------------------
DROP TABLE IF EXISTS `t_cg_project_template`;
CREATE TABLE `t_cg_project_template`
(
    `project_template_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '模板ID',
    `project_id`          int(11) NOT NULL COMMENT '项目ID',
    `project_package_id`  int(11) NOT NULL COMMENT '包ID',
    `file_name`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '生成文件名',
    `file_suffix`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '生成文件后缀名 .java',
    `content`             mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板内容',
    `is_basic`            tinyint(4) NOT NULL COMMENT '是否作为基础模板使用（1：是 0：否）',
    `data_re_user_id`     int(11) NOT NULL COMMENT '关联用户id',
    `create_by`           int(11) NOT NULL COMMENT '创建人',
    `create_time`         datetime                                                     NOT NULL COMMENT '创建时间',
    `update_by`           int(11) NOT NULL COMMENT '修改人',
    `update_time`         datetime                                                     NOT NULL COMMENT '修改时间',
    `is_deleted`          tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
    PRIMARY KEY (`project_template_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 118 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成器 - 项目代码模板表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_cg_project_template
-- ----------------------------
INSERT INTO `t_cg_project_template`
VALUES (19, 1, 3, '${entity}', '.java',
        'package ${package.entity};\r\n\r\nimport java.util.Date;\r\n\r\nimport com.baomidou.mybatisplus.annotation.IdType;\r\nimport com.baomidou.mybatisplus.annotation.TableId;\r\nimport com.baomidou.mybatisplus.annotation.TableName;\r\nimport com.zhengqing.modules.common.entity.BaseEntity;\r\n\r\nimport io.swagger.annotations.ApiModel;\r\nimport io.swagger.annotations.ApiModelProperty;\r\nimport lombok.AllArgsConstructor;\r\nimport lombok.Builder;\r\nimport lombok.Data;\r\nimport lombok.EqualsAndHashCode;\r\nimport lombok.NoArgsConstructor;\r\n\r\n/**\r\n * <p>  ${tableComment} </p>\r\n *\r\n * @author ${author}\r\n * @description\r\n * @date ${date}\r\n */\r\n@Data\r\n@EqualsAndHashCode(callSuper = true)\r\n@Builder\r\n@AllArgsConstructor\r\n@NoArgsConstructor\r\n@TableName(\"${tableName}\")\r\n@ApiModel(\"${tableComment}\")\r\npublic class ${entity} extends BaseEntity<${entity}> {\r\n\r\n<#list columnInfoList as item>\r\n<#if item.columnNameDb != \"create_by\" && item.columnNameDb != \"create_time\" && item.columnNameDb != \"update_by\" && item.columnNameDb != \"update_time\" && item.columnNameDb != \"is_valid\">\r\n@ApiModelProperty(\"${item.columnComment}\")\r\n<#if item.ifPrimaryKey>\r\n@TableId(value = \"${item.columnNameDb}\", type = IdType.AUTO)\r\n</#if>\r\nprivate ${item.columnTypeJava} ${item.columnNameJavaLower};\r\n\r\n</#if>\r\n</#list>\r\n        }\r\n',
        0, 1, 1, '2020-08-22 15:01:51', 1, '2021-06-13 05:56:38', 0);
INSERT INTO `t_cg_project_template`
VALUES (20, 1, 7, '${entity}Mapper', '.xml',
        '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n<mapper namespace=\"${package.mapper}.${entity}Mapper\">\n\n    <select id=\"selectDataList\" resultType=\"${package.vo}.${entity}ListVO\">\n        SELECT\n        <#list columnInfoList as item>\n            ${tableNameAbbr}.${item.columnNameDb} ${item.columnNameJavaLower},\n        </#list>\n        FROM ${tableName} ${tableNameAbbr}\n        WHERE ${tableNameAbbr}.is_valid = 1\n        <#list queryColumnInfoList as item>\n         <#if item.columnTypeJava = \"Integer\"> \n            <if test=\"filter.${item.columnNameJavaLower}!=null\">\n                AND ${tableNameAbbr}.${item.columnNameDb} = ${r\"#\"}{filter.${item.columnNameJavaLower}}\n            </if>\n        <#else>\n            <if test=\"filter.${item.columnNameJavaLower}!=null and filter.${item.columnNameJavaLower}!=\'\'\">\n                AND ${tableNameAbbr}.${item.columnNameDb} LIKE CONCAT( \'%\', ${r\"#\"}{filter.${item.columnNameJavaLower}} , \'%\' )\n            </if>\n        </#if> \n        </#list>\n        ORDER BY ${tableNameAbbr}.update_time DESC\n    </select>\n\n</mapper>\n',
        0, 1, 1, '2020-08-22 15:01:51', 1, '2021-06-13 05:55:58', 0);
INSERT INTO `t_cg_project_template`
VALUES (21, 1, 6, '${entity}Mapper', '.java',
        'package ${package.mapper};\r\n\r\nimport java.util.List;\r\n\r\nimport org.apache.ibatis.annotations.Param;\r\n\r\nimport com.baomidou.mybatisplus.core.mapper.BaseMapper;\r\nimport com.baomidou.mybatisplus.core.metadata.IPage;\r\n\r\n/**\r\n * <p> ${tableComment} Mapper 接口 </p>\r\n *\r\n * @author ${author}\r\n * @description\r\n * @date ${date}\r\n */\r\npublic interface ${entity}Mapper extends BaseMapper<${entity}> {\r\n\r\n        /**\r\n         * 列表分页\r\n         *\r\n         * @param page: 分页数据\r\n         * @param filter: 查询过滤参数\r\n         * @return 查询结果\r\n         * @author ${author}\r\n         * @date ${date}\r\n         */\r\n        IPage<${entity}ListVO> selectDataList(IPage page, @Param(\"filter\") ${entity}ListDTO filter);\r\n\r\n        /**\r\n         * 列表\r\n         *\r\n         * @param filter: 查询过滤参数\r\n         * @return 查询结果\r\n         * @author ${author}\r\n         * @date ${date}\r\n         */\r\n        List<${entity}ListVO> selectDataList(@Param(\"filter\") ${entity}ListDTO filter);\r\n\r\n        }\r\n',
        0, 1, 1, '2020-08-22 15:01:51', 1, '2021-06-13 05:55:38', 0);
INSERT INTO `t_cg_project_template`
VALUES (22, 1, 4, 'I${entity}Service', '.java',
        'package ${package.service};\r\n\r\nimport java.util.List;\r\n\r\nimport com.baomidou.mybatisplus.core.metadata.IPage;\r\nimport com.baomidou.mybatisplus.extension.service.IService;\r\n\r\n/**\r\n * <p>  ${tableComment} 服务类 </p>\r\n *\r\n * @author ${author}\r\n * @description\r\n * @date ${date}\r\n */\r\npublic interface I${entity}Service extends IService<${entity}> {\r\n\r\n        /**\r\n         * 列表分页\r\n         *\r\n         * @param params: 查询参数\r\n         * @return 查询结果\r\n         * @author ${author}\r\n         * @date ${date}\r\n         */\r\n        IPage<${entity}ListVO> listPage(${entity}ListDTO params);\r\n\r\n        /**\r\n         * 列表\r\n         *\r\n         * @param params: 查询参数\r\n         * @return 查询结果\r\n         * @author ${author}\r\n         * @date ${date}\r\n         */\r\n        List<${entity}ListVO> list(${entity}ListDTO params);\r\n\r\n        /**\r\n         * 新增或更新\r\n         *\r\n         * @param params: 保存参数\r\n         * @return 主键id\r\n         * @author ${author}\r\n         * @date ${date}\r\n         */\r\n        Integer addOrUpdateData(${entity}SaveDTO params);\r\n\r\n        }',
        0, 1, 1, '2020-08-22 15:01:51', 1, '2021-06-13 05:54:49', 0);
INSERT INTO `t_cg_project_template`
VALUES (23, 1, 5, '${entity}ServiceImpl', '.java',
        'package ${package.impl};\r\n\r\nimport java.util.List;\r\nimport org.springframework.util.CollectionUtils;\r\nimport org.springframework.beans.factory.annotation.Autowired;\r\nimport org.springframework.stereotype.Service;\r\nimport org.springframework.transaction.annotation.Transactional;\r\n\r\nimport com.baomidou.mybatisplus.core.metadata.IPage;\r\nimport com.baomidou.mybatisplus.extension.plugins.pagination.Page;\r\nimport com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;\r\nimport com.zhengqing.utils.MyBeanUtils;\r\n\r\nimport lombok.extern.slf4j.Slf4j;\r\n\r\n/**\r\n * <p> ${tableComment} 服务实现类 </p>\r\n *\r\n * @author ${author}\r\n * @description\r\n * @date ${date}\r\n */\r\n@Slf4j\r\n@Service\r\n@Transactional(rollbackFor = Exception.class)\r\npublic class ${entity}ServiceImpl extends ServiceImpl<${entity}Mapper, ${entity}> implements I${entity}Service {\r\n\r\n@Autowired\r\nprivate  ${entity}Mapper ${entityNameLower}Mapper;\r\n\r\n@Override\r\npublic IPage<${entity}ListVO> listPage(${entity}ListDTO params) {\r\n        IPage<${entity}ListVO> result = ${entityNameLower}Mapper.selectDataList(new Page<>(), params);\r\n        List<${entity}ListVO> list = result.getRecords();\r\n        handleResultData(list);\r\n        return result;\r\n        }\r\n\r\n@Override\r\npublic List<${entity}ListVO> list(${entity}ListDTO params) {\r\n        List<${entity}ListVO> list =  ${entityNameLower}Mapper.selectDataList(params);\r\n        handleResultData(list);\r\n        return list;\r\n        }\r\n\r\n/**\r\n * 处理数据\r\n *\r\n * @param list: 数据\r\n * @return void\r\n * @author ${author}\r\n * @date ${date}\r\n */\r\nprivate void handleResultData(List<${entity}ListVO> list) {\r\n\r\n        }\r\n\r\n@Override\r\npublic Integer addOrUpdateData(${entity}SaveDTO params) {\r\n<#list columnInfoList as item>\r\n<#if item.columnNameDb != \"create_by\" && item.columnNameDb != \"create_time\" && item.columnNameDb != \"update_by\" && item.columnNameDb != \"update_time\" && item.columnNameDb != \"is_valid\">\r\n        ${item.columnTypeJava} ${item.columnNameJavaLower} = params.get${item.columnNameJavaUpper}();\r\n</#if>\r\n</#list>\r\n\r\n        ${entity} ${entityNameLower} = new ${entity}();\r\n<#list columnInfoList as item>\r\n<#if item.columnNameDb != \"create_by\" && item.columnNameDb != \"create_time\" && item.columnNameDb != \"update_by\" && item.columnNameDb != \"update_time\" && item.columnNameDb != \"is_valid\">\r\n        ${entityNameLower}.set${item.columnNameJavaUpper}(${item.columnNameJavaLower});\r\n</#if>\r\n</#list>\r\n\r\n        if (${entityNameLower}Id==null) {\r\n        ${entityNameLower}.insert();\r\n        } else {\r\n        ${entityNameLower}.updateById();\r\n        }\r\n        return ${entityNameLower}.getId();\r\n        }\r\n\r\n        }\r\n',
        0, 1, 1, '2020-08-22 15:01:51', 1, '2021-06-13 05:53:53', 0);
INSERT INTO `t_cg_project_template`
VALUES (24, 1, 18, '${entity}Controller', '.java',
        'package ${package.api};\n\nimport java.util.List;\n\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.validation.annotation.Validated;\nimport org.springframework.web.bind.annotation.DeleteMapping;\nimport org.springframework.web.bind.annotation.GetMapping;\nimport org.springframework.web.bind.annotation.ModelAttribute;\nimport org.springframework.web.bind.annotation.PostMapping;\nimport org.springframework.web.bind.annotation.PutMapping;\nimport org.springframework.web.bind.annotation.RequestBody;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RequestParam;\nimport org.springframework.web.bind.annotation.RestController;\n\nimport com.baomidou.mybatisplus.core.metadata.IPage;\nimport com.zhengqing.modules.common.api.BaseController;\nimport com.zhengqing.modules.common.validator.fieldrepeat.Update;\nimport com.zhengqing.modules.common.validator.repeatsubmit.NoRepeatSubmit;\nimport com.zhengqing.modules.smalltools.db.service.IStDbDataSourceService;\n\nimport io.swagger.annotations.Api;\nimport io.swagger.annotations.ApiOperation;\n\n\n/**\n* <p> ${tableComment} 接口 </p>\n*\n* @author ${author}\n* @description\n* @date ${date}\n*/\n@RestController\n@RequestMapping(\"/api/${moduleName}/${entityNameLower}\")\n@Api(tags = {\"${tableComment}接口\"})\npublic class ${entity}Controller extends BaseController {\n\n    @Autowired\n    private  I${entity}Service ${entityNameLower}Service;\n\n    @GetMapping(\"listPage\")\n    @ApiOperation(\"列表分页\")\n    public IPage<${entity}ListVO> listPage(@ModelAttribute ${entity}ListDTO params) {\n        return ${entityNameLower}Service.listPage(params);\n    }\n\n    @GetMapping(\"list\")\n    @ApiOperation(\"列表\")\n    public List<${entity}ListVO> list(@ModelAttribute ${entity}ListDTO params) {\n        return ${entityNameLower}Service.list(params);\n    }\n\n    @NoRepeatSubmit\n    @PostMapping(\"\")\n    @ApiOperation(\"新增\")\n    public Integer add(@Validated @RequestBody ${entity}SaveDTO params) {\n      return ${entityNameLower}Service.addOrUpdateData(params);\n    }\n\n    @NoRepeatSubmit\n    @PutMapping(\"\")\n    @ApiOperation(\"更新\")\n    public Integer update(@Validated(Update.class) @RequestBody ${entity}SaveDTO params) {\n      return ${entityNameLower}Service.addOrUpdateData(params);\n    }\n\n    @DeleteMapping(\"\")\n    @ApiOperation(\"删除\")\n    public void delete(@RequestParam Integer ${entityNameLower}Id) {\n      ${entityNameLower}Service.removeById(${entityNameLower}Id);\n    }\n\n    @GetMapping(\"detail\")\n    @ApiOperation(\"详情\")\n    public ${entity} detail(@RequestParam Integer ${entityNameLower}Id) {\n      return ${entityNameLower}Service.getById(${entityNameLower}Id);\n    }\n\n}\n',
        0, 1, 1, '2020-08-22 15:01:51', 1, '2021-06-13 05:42:49', 0);
INSERT INTO `t_cg_project_template`
VALUES (25, 1, 21, '${entity}ListDTO', '.java',
        'package ${package.dto};\n\nimport com.zhengqing.modules.common.model.dto.BaseDTO;\nimport io.swagger.annotations.ApiModel;\nimport io.swagger.annotations.ApiModelProperty;\nimport lombok.AllArgsConstructor;\nimport lombok.Builder;\nimport lombok.Data;\nimport lombok.NoArgsConstructor;\n\n/**\n* <p> ${tableComment}查询参数 </p>\n*\n* @author ${ author }\n* @description\n* @date ${date}\n*/\n@Data\n@Builder\n@NoArgsConstructor\n@AllArgsConstructor\n@EqualsAndHashCode(callSuper = true)\n@ApiModel(\"${tableComment}查询参数\")\npublic class ${entity}ListDTO extends BaseDTO {\n\n<#list queryColumnInfoList as item>\n    @ApiModelProperty(\"${item.columnComment}\")\n    private ${item.columnTypeJava} ${item.columnNameJavaLower};\n    \n</#list>\n}\n',
        0, 1, 1, '2020-08-22 15:01:51', 1, '2021-06-13 05:42:34', 0);
INSERT INTO `t_cg_project_template`
VALUES (26, 1, 20, 'index', '.vue',
        '<template>\n  <cus-wraper>\n    <base-header>\n        <#if queryColumnInfoList??>\n            <#list queryColumnInfoList as item>\n              <el-input v-model=\"listQuery.${item.columnNameJavaLower}\"\n                        placeholder=\"请输入${item.columnComment}\" style=\"width:200px\" clearable\n                        @clear=\"refreshTableData\"></el-input>\n            </#list>\n        </#if>\n      <el-button v-has=\"\'query\'\" type=\"primary\" @click=\"refreshTableData\">查询</el-button>\n      <template #right>\n        <el-button v-has=\"\'add\'\" type=\"primary\" @click=\"handleAdd\">添加</el-button>\n      </template>\n    </base-header>\n\n    <base-table-p ref=\"baseTable\" api=\"${vueApiName}.listPage\" :params=\"listQuery\">\n        <#list columnInfoList as item>\n            <#if item.columnTypeJava == \"Date\">\n              <el-table-column label=\"${item.columnComment}\" align=\"center\">\n                <template slot-scope=\"scope\">\n                  <span>{{scope.row.${item.columnNameJavaLower}|dateTimeFilter}}</span>\n                </template>\n              </el-table-column>\n            <#else>\n              <el-table-column label=\"${item.columnComment}\" prop=\"${item.columnNameJavaLower}\"\n                               align=\"center\"></el-table-column>\n            </#if>\n        </#list>\n      <el-table-column align=\"center\" label=\"操作\">\n        <template slot-scope=\"scope\">\n          <el-button type=\"text\" @click=\"handleUpdate(scope.row)\">编辑</el-button>\n          <cus-del-btn @ok=\"handleDelete(scope.row)\"></cus-del-btn>\n        </template>\n      </el-table-column>\n    </base-table-p>\n\n    <base-dialog :visible.sync=\"dialogVisible\" :title=\"titleMap[dialogStatus]\" width=\"30%\">\n      <el-form ref=\"dataForm\" :model=\"form\" :rules=\"rules\" label-width=\"100px\">\n          <#list columnInfoList as item>\n            <el-form-item label=\"${item.columnComment}:\" prop=\"${item.columnNameJavaLower}\">\n              <el-input v-model=\"form.${item.columnNameJavaLower}\"></el-input>\n            </el-form-item>\n          </#list>\n      </el-form>\n      <div slot=\"footer\">\n        <el-button @click=\"dialogVisible = false\">取 消</el-button>\n        <el-button type=\"primary\" @click=\"submitForm\">确 定</el-button>\n      </div>\n    </base-dialog>\n  </cus-wraper>\n</template>\n\n<script>\n  export default {\n    name: \'${entity}\',\n    data() {\n      return {\n        dialogVisible: false,\n        listQuery: {\n          <#list queryColumnInfoList as item>\n          ${item.columnNameJavaLower}: undefined, // ${item.columnComment}\n          </#list>\n        },\n        form: {\n          <#list columnInfoList as item>\n          ${item.columnNameJavaLower}: undefined, // ${item.columnComment}\n          </#list>\n        },\n        dialogStatus: \"\",\n        titleMap: {\n          add: \"添加\",\n          update: \"编辑\",\n          detail: \"详情\"\n        },\n        rules: {  }\n      }\n    },\n    created() {\n    },\n    methods: {\n      refreshTableData() {\n        this.$refs.baseTable.refresh();\n      },\n      handleAdd() {\n        this.form = Object.assign({}, {});\n        this.dialogStatus = \"add\";\n        this.dialogVisible = true;\n      },\n      handleUpdate(row) {\n        this.form = Object.assign({}, row);\n        this.dialogStatus = \"update\";\n        this.dialogVisible = true;\n      },\n      async handleDelete(row) {\n        let res = await this.$api.${vueApiName}.delete(row.id);\n        this.refreshTableData();\n        this.submitOk(res.message);\n      },\n      submitForm() {\n        this.$refs.dataForm.validate(async valid => {\n          if (valid) {\n            let res = await this.$api.${vueApiName}[this.form.id ? \"update\" : \"add\"](this.form);\n            this.refreshTableData();\n            this.submitOk(res.message);\n            this.dialogVisible = false;\n          }\n        });\n      }\n    }\n  }\n</script>\n<style scoped></style>\n',
        0, 1, 1, '2020-08-22 15:01:51', 1, '2020-11-17 00:17:19', 0);
INSERT INTO `t_cg_project_template`
VALUES (27, 1, 19, '${entity}', '.js',
        'import request from \'@/utils/request\';\n\nconst BASE_API = \"/api/${moduleName}/${entityNameLower}\";\n\nexport default {\n  listPage(query, headers) {\n    return request({\n      url: BASE_API + \"/listPage\",\n      method: \"get\",\n      params: query,\n      headers\n    });\n  },\n  add(form) {\n    return request({\n      url: BASE_API,\n      method: \"post\",\n      data: form\n    });\n  },\n  update(form) {\n    return request({\n      url: BASE_API,\n      method: \"put\",\n      data: form\n    });\n  },\n  delete(id) {\n    return request({\n      url: BASE_API,\n      method: \"delete\",\n      params: { id: id }\n    });\n  }\n};\n',
        0, 1, 1, '2020-08-22 15:01:51', 1, '2020-11-17 00:17:27', 0);
INSERT INTO `t_cg_project_template`
VALUES (57, 1, 22, '${entity}ListVO', '.java',
        'package ${ package.vo };\n\nimport io.swagger.annotations.ApiModel;\nimport io.swagger.annotations.ApiModelProperty;\nimport lombok.Data;\n\n/**\n * <p>${tableComment}展示视图</p>\n *\n * @author ${ author }\n * @description\n * @date ${date}\n */\n@Data\n@ApiModel(\"${tableComment}展示视图\")\npublic class ${entity}ListVO {\n\n<#list columnInfoList as item>\n    <#if item.columnNameDb != \"create_by\" && item.columnNameDb != \"create_time\" && item.columnNameDb != \"update_by\" && item.columnNameDb != \"update_time\" && item.columnNameDb != \"is_valid\"> \n        @ApiModelProperty(\"${item.columnComment}\")\n        private ${item.columnTypeJava} ${item.columnNameJavaLower};\n        \n    </#if> \n</#list>\n}',
        0, 1, 1, '2020-09-01 21:59:47', 1, '2021-06-13 05:40:44', 0);
INSERT INTO `t_cg_project_template`
VALUES (98, 1, 3, '${entity}', '.java',
        'package ${package.Entity};\n\n#foreach($pkg in ${table.importPackages})\nimport ${pkg};\n#end\nimport io.swagger.annotations.ApiModel;\nimport io.swagger.annotations.ApiModelProperty;\nimport lombok.Data;\n\n/**\n * <p>  ${table.comment} </p>\n *\n * @author: ${author}\n * @date: ${date}\n */\n#if(${table.convert})\n@Data\n@ApiModel(description = \"${table.comment}\")\n@TableName(\"${table.name}\")\n#end\n#if(${superEntityClass})\npublic class ${entity} extends ${superEntityClass}#if(${activeRecord})<${entity}>#end {\n#elseif(${activeRecord})\npublic class ${entity} extends Model<${entity}> {\n#else\npublic class ${entity} implements Serializable {\n#end\n\n    private static final long serialVersionUID = 1L;\n\n#foreach($field in ${table.fields})\n#if(${field.keyFlag})\n#set($keyPropertyName=${field.propertyName})\n#end\n#if(\"$!field.comment\" != \"\")\n    /**\n     * ${field.comment}\n     */\n	@ApiModelProperty(value = \"${field.comment}\")\n#end\n#if(${field.keyFlag})\n	@TableId(value=\"${field.name}\", type= IdType.AUTO)\n#else\n	@TableField(\"${field.name}\")\n#end\n	private ${field.propertyType} ${field.propertyName};\n#end\n\n#if(${entityColumnConstant})\n#foreach($field in ${table.fields})\n	public static final String ${field.name.toUpperCase()} = \"${field.name}\";\n\n#end\n#end\n#if(${activeRecord})\n	@Override\n	protected Serializable pkVal() {\n#if(${keyPropertyName})\n		return this.${keyPropertyName};\n#else\n		return this.id;\n#end\n	}\n\n#end\n}\n',
        1, 1, 1, '2020-11-15 15:12:26', 1, '2020-11-15 15:12:26', 0);
INSERT INTO `t_cg_project_template`
VALUES (99, 1, 7, '${entity}Mapper', '.xml',
        '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n<mapper namespace=\"${package.Mapper}.${table.mapperName}\">\n\n#if(${enableCache})\n	<!-- 开启二级缓存 -->\n	<cache type=\"org.mybatis.caches.ehcache.LoggingEhcache\"/>\n\n#end\n#if(${baseResultMap})\n	<!-- 通用查询映射结果 -->\n	<resultMap id=\"BaseResultMap\" type=\"${package.Entity}.${entity}\">\n#foreach($field in ${table.fields})\n#if(${field.keyFlag})##生成主键排在第一位\n		<id column=\"${field.name}\" property=\"${field.propertyName}\" />\n#end\n#end\n#foreach($field in ${table.fields})\n#if(!${field.keyFlag})##生成普通字段\n		<result column=\"${field.name}\" property=\"${field.propertyName}\" />\n#end\n#end\n	</resultMap>\n\n#end\n#if(${baseColumnList})\n    <!-- 通用查询结果列 -->\n    <sql id=\"Base_Column_List\">\n        ${table.fieldNames}\n    </sql>\n\n#end\n\n    <select id=\"select${entity}s\" resultMap=\"BaseResultMap\">\n        SELECT\n             *\n        FROM ${table.name}\n        WHERE\n             1 = 1\n        <if test=\"filter.id!=null and filter.id!=\'\'\">\n            AND	${entity}_ID= #{filter.id}\n        </if>\n        ORDER BY ${entity}_ID DESC\n    </select>\n\n</mapper>\n',
        1, 1, 1, '2020-11-15 15:12:26', 1, '2020-11-15 15:12:26', 0);
INSERT INTO `t_cg_project_template`
VALUES (100, 1, 6, '${entity}Mapper', '.java',
        'package ${package.Mapper};\n\nimport ${package.Entity}.${entity};\nimport ${package.QueryPara}.${formQueryPara};\nimport ${superMapperClassPackage};\nimport com.baomidou.mybatisplus.plugins.pagination.Pagination;\nimport org.apache.ibatis.annotations.Param;\n\nimport java.util.List;\n\n/**\n * <p> ${table.comment} Mapper 接口 </p>\n *\n * @author : zhengqing\n * @date : ${date}\n */\npublic interface ${table.mapperName} extends ${superMapperClass}<${entity}> {\n\n    /**\n     * 列表分页\n     *\n     * @param page\n     * @param filter\n     * @return\n     */\n    List<${entity}> select${entity}s(Pagination page, @Param(\"filter\") ${formQueryPara} filter);\n\n    /**\n     * 列表\n     *\n     * @param filter\n     * @return\n     */\n    List<${entity}> select${entity}s(@Param(\"filter\") ${formQueryPara} filter);\n}',
        1, 1, 1, '2020-11-15 15:12:26', 1, '2020-11-15 15:12:26', 0);
INSERT INTO `t_cg_project_template`
VALUES (101, 1, 4, 'I${entity}Service', '.java',
        'package ${package.Service};\n\nimport com.baomidou.mybatisplus.plugins.Page;\nimport ${superServiceClassPackage};\nimport ${package.Entity}.${entity};\nimport ${package.QueryPara}.${formQueryPara};\n\nimport java.util.List;\n\n/**\n * <p>  ${table.comment} 服务类 </p>\n *\n * @author: ${author}\n * @date: ${date}\n */\npublic interface ${table.serviceName} extends ${superServiceClass}<${entity}> {\n\n    /**\n     * ${table.comment}列表分页\n     *\n     * @param page\n     * @param filter\n     * @return\n     */\n    void listPage(Page<${entity}> page, ${formQueryPara} filter);\n\n    /**\n     * 保存${table.comment}\n     *\n     * @param input\n     */\n    Integer save(${entity} input);\n\n    /**\n     * ${table.comment}列表\n     *\n     * @param filter\n     * @return\n     */\n    List<${entity}> list(${formQueryPara} filter);\n}\n',
        1, 1, 1, '2020-11-15 15:12:26', 1, '2020-11-15 15:12:26', 0);
INSERT INTO `t_cg_project_template`
VALUES (102, 1, 5, '${entity}ServiceImpl', '.java',
        'package ${package.ServiceImpl};\n\nimport ${package.Entity}.${entity};\nimport ${package.QueryPara}.${formQueryPara};\nimport ${package.Mapper}.${table.mapperName};\nimport ${package.Service}.${table.serviceName};\nimport ${superServiceImplClassPackage};\nimport com.baomidou.mybatisplus.plugins.Page;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.stereotype.Service;\nimport org.springframework.transaction.annotation.Transactional;\n\nimport java.util.List;\n\n/**\n * <p> ${table.comment} 服务实现类 </p>\n *\n * @author: ${author}\n * @date: ${date}\n */\n@Service\n@Transactional\npublic class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {\n\n    @Autowired\n    ${table.mapperName} ${entityPropertyName}Mapper;\n\n    @Override\n    public void listPage(Page<${entity}> page, ${formQueryPara} filter) {\n        page.setRecords(${entityPropertyName}Mapper.select${entity}s(page, filter));\n    }\n\n    @Override\n    public List<${entity}> list(${formQueryPara} filter) {\n        return ${entityPropertyName}Mapper.select${entity}s(filter);\n    }\n\n    @Override\n    public Integer save(${entity} para) {\n        if (para.get${entity}Id()!=null) {\n            ${entityPropertyName}Mapper.updateById(para);\n        } else {\n            ${entityPropertyName}Mapper.insert(para);\n        }\n        return para.get${entity}Id();\n    }\n}\n',
        1, 1, 1, '2020-11-15 15:12:26', 1, '2020-11-15 15:12:26', 0);
INSERT INTO `t_cg_project_template`
VALUES (103, 1, 18, '${entity}Controller', '.java',
        'package ${package.Controller};\n\nimport com.zhengqing.modules.common.api.BaseController;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.web.bind.annotation.*;\n\nimport java.util.List;\n\nimport com.baomidou.mybatisplus.plugins.Page;\nimport com.zhengqing.modules.common.dto.output.ApiResult;\nimport ${package.Entity}.${entity};\nimport ${package.QueryPara}.${formQueryPara};\nimport ${package.Service}.${table.serviceName};\nimport io.swagger.annotations.Api;\nimport io.swagger.annotations.ApiOperation;\n\n\n/**\n * <p> ${table.comment} 接口 </p>\n *\n * @author: zhengqing\n * @description:\n * @date: ${date}\n *\n */\n@RestController\n@RequestMapping(\"/api#if(${package.ModuleName})/${package.ModuleName}#end/${table.entityPath}\")\n@Api(description = \"${table.comment}接口\")\npublic class ${table.controllerName} extends BaseController {\n\n    @Autowired\n    ${table.serviceName} ${entityPropertyName}Service;\n\n    @PostMapping(value = \"/listPage\", produces = \"application/json;charset=utf-8\")\n    @ApiOperation(value = \"获取${table.comment}列表分页\", httpMethod = \"POST\", response = ApiResult.class)\n    public ApiResult listPage(@RequestBody ${formQueryPara} filter) {\n        Page<${entity}> page = new Page<>(filter.getPage(),filter.getLimit());\n        ${entityPropertyName}Service.listPage(page, filter);\n        return ApiResult.ok(\"获取${table.comment}列表分页成功\", page);\n    }\n\n    @PostMapping(value = \"/list\", produces = \"application/json;charset=utf-8\")\n    @ApiOperation(value = \"获取${table.comment}列表\", httpMethod = \"POST\", response = ApiResult.class)\n    public ApiResult list(@RequestBody ${formQueryPara} filter) {\n        List<${entity}> result = ${entityPropertyName}Service.list(filter);\n        return ApiResult.ok(\"获取${table.comment}列表成功\",result);\n    }\n\n    @PostMapping(value = \"/save\", produces = \"application/json;charset=utf-8\")\n    @ApiOperation(value = \"保存${table.comment}\", httpMethod = \"POST\", response = ApiResult.class)\n    public ApiResult save(@RequestBody ${entity} input) {\n        Integer id = ${entityPropertyName}Service.save(input);\n        return ApiResult.ok(\"保存${table.comment}成功\", id);\n    }\n\n    @PostMapping(value = \"/delete\", produces = \"application/json;charset=utf-8\")\n    @ApiOperation(value = \"删除${table.comment}\", httpMethod = \"POST\", response = ApiResult.class)\n    public ApiResult delete(@RequestBody ${formQueryPara} input) {\n        ${entityPropertyName}Service.deleteById(input.getId());\n        return ApiResult.ok(\"删除${table.comment}成功\");\n    }\n\n    @PostMapping(value = \"/getById\", produces = \"application/json;charset=utf-8\")\n    @ApiOperation(value = \"获取${table.comment}信息\", httpMethod = \"POST\", response = ApiResult.class)\n    public ApiResult getById(@RequestBody ${formQueryPara} input) {\n        ${entity} entity = ${entityPropertyName}Service.selectById(input.getId());\n        return ApiResult.ok(\"获取${table.comment}信息成功\", entity);\n    }\n\n}',
        1, 1, 1, '2020-11-15 15:12:26', 1, '2020-11-15 15:12:26', 0);
INSERT INTO `t_cg_project_template`
VALUES (104, 1, 20, 'index', '.vue',
        '<template>\n  <div class=\"app-container\">\n    <cus-wraper>\n      <cus-filter-wraper>\n        #if(${queryFields})\n        #foreach($field in ${queryFields})\n        <el-input v-model=\"listQuery.${field.propertyName}\" placeholder=\"请输入${field.comment}\" style=\"width:200px\" clearable></el-input>\n        #end\n        <el-button type=\"primary\" @click=\"getList\" icon=\"el-icon-search\" v-waves>查询</el-button>\n        <el-button type=\"primary\" @click=\"handleCreate\" icon=\"el-icon-plus\" v-waves>添加</el-button>        \n        #end\n      </cus-filter-wraper>\n      <div class=\"table-container\">\n        <el-table v-loading=\"listLoading\" :data=\"list\" size=\"mini\" element-loading-text=\"Loading\" fit border highlight-current-row>\n	        #foreach($field in ${table.fields})\n	        #if(${field.propertyType.equals(\"Date\")})\n	        <el-table-column label=\"${field.comment}\" align=\"center\">\n	            <template slot-scope=\"scope\">\n	                <span>{{scope.row.${field.propertyName}|dateTimeFilter}}</span>\n	            </template>\n	        </el-table-column>\n	        #else\n	        <el-table-column label=\"${field.comment}\" prop=\"${field.propertyName}\" align=\"center\"></el-table-column>\n	        #end\n	        #end\n          <el-table-column align=\"center\" label=\"操作\">\n            <template slot-scope=\"scope\">\n              <el-button size=\"mini\" type=\"primary\" @click=\"handleUpdate(scope.row)\" icon=\"el-icon-edit\" plain v-waves>编辑</el-button>\n              <cus-del-btn @ok=\"handleDelete(scope.row)\"></cus-del-btn>\n            </template>\n          </el-table-column>\n        </el-table>\n        <!-- 分页 -->\n        <cus-pagination v-show=\"total>0\" :total=\"total\" :page.sync=\"listQuery.page\" :limit.sync=\"listQuery.limit\" @pagination=\"getList\"/>\n      </div>\n\n      <el-dialog :title=\"titleMap[dialogStatus]\" :visible.sync=\"dialogVisible\" width=\"40%\" @close=\"handleDialogClose\">\n        <el-form ref=\"dataForm\" :model=\"form\" :rules=\"rules\" label-width=\"100px\" class=\"demo-ruleForm\">\n        #foreach($field in ${table.fields})\n        <el-form-item label=\"${field.comment}:\" prop=\"${field.propertyName}\">\n            <el-input v-model=\"form.${field.propertyName}\"></el-input>\n        </el-form-item>\n        #end\n        </el-form>\n        <span slot=\"footer\" class=\"dialog-footer\">\n          <el-button @click=\"dialogVisible = false\" v-waves>取 消</el-button>\n          <el-button type=\"primary\" @click=\"submitForm\" v-waves>确 定</el-button>\n        </span>\n      </el-dialog>\n    </cus-wraper>\n  </div>\n</template>\n\n<script>\n import { get${entity}Page, save${entity}, delete${entity} } from \"@/api/${package.ModuleName}/${entityPropertyName}\";\n\nexport default {\n  data() {\n    return {\n      dialogVisible: false,\n      list: [],\n      listLoading: true,\n      total: 0,\n      listQuery: {\n        page: 1,\n        limit: 10,\n	    #if(${queryFields})\n	    #foreach($field in ${queryFields})\n	    ${field.propertyName}:undefined,\n	    #end\n	    #end\n      },\n      input: \'\',\n      form: {\n	     #foreach($field in ${table.fields})\n	     ${field.propertyName}: undefined, //${field.comment}\n	     #end\n      },\n     dialogStatus: \"\",\n     titleMap: {\n        update: \"编辑\",\n        create: \"创建\"\n     },\n     rules: {\n         name: [\n            { required: true, message: \' 请输入项目名称\', trigger: \'blur\' }\n         ]\n      }\n    }\n  },\n  created() {\n    this.getList();\n  },\n  methods: {\n    getList() {\n      this.listLoading = true;\n      get${entity}Page(this.listQuery).then(response => {\n        this.list = response.data.records;\n    	this.total = response.data.total;\n    	this.listLoading = false;\n		});\n    },\n    handleCreate() {\n        this.resetForm();\n        this.dialogStatus = \"create\";\n        this.dialogVisible = true;\n    },\n    handleUpdate(row) {\n        this.form = Object.assign({}, row);\n        this.dialogStatus = \"update\";\n        this.dialogVisible = true;\n    },\n    handleDelete(row) {\n      #foreach($field in ${table.fields})\n		#if(${field.keyFlag})\n		 let id = row.${field.propertyName};\n		#end\n	  #end\n      delete${entity}(id).then(response => {\n            if (response.code == 200) {\n            this.getList();\n            this.submitOk(response.message);\n        } else {\n            this.submitFail(response.message);\n        }\n    });\n    },\n    submitForm() {\n    this.#[[$refs]]#.[\'dataForm\'].validate(valid => {\n        if (valid) {\n            save${entity}(this.form).then(response => {\n                if (response.code == 200) {\n                    this.getList();\n                    this.submitOk(response.message);\n                    this.dialogVisible = false;\n                } else {\n                     this.submitFail(response.message);\n                }\n        }).catch(err => { console.log(err);  });\n            }\n        });\n    },\n    resetForm() {\n        this.form = {\n            #foreach($field in ${table.fields})\n                ${field.propertyName}: undefined, //${field.comment}\n            #end\n        };\n    },\n    // 监听dialog关闭时的处理事件\n    handleDialogClose(){\n        if(this.$refs[\'dataForm\']){\n             this.$refs[\'dataForm\'].clearValidate(); // 清除整个表单的校验\n        }\n    }\n  }\n}\n</script>',
        1, 1, 1, '2020-11-15 15:12:26', 1, '2020-11-15 15:12:26', 0);
INSERT INTO `t_cg_project_template`
VALUES (105, 1, 19, '${entity}', '.js',
        'import request from \'@/utils/request\';\n\nexport function get${entity}Page(query) {\n    return request({\n        url: \'/api/${package.ModuleName}/${entityPropertyName}/listPage\',\n        method: \'post\',\n        data: query\n    });\n}\n\nexport function save${entity}(form) {\n    return request({\n        url: \'/api/${package.ModuleName}/${entityPropertyName}/save\',\n        method: \'post\',\n        data: form\n    });\n}\n\nexport function delete${entity}(id) {\n    return request({\n        url: \'/api/${package.ModuleName}/${entityPropertyName}/delete\',\n        method: \'post\',\n        data: { \'id\': id }\n    });\n}\n\nexport function get${entity}ById(id) {\n    return request({\n        url: \'/api/${package.ModuleName}/${entityPropertyName}/getById\',\n        method: \'post\',\n        data: { \'id\': id }\n    });\n}',
        1, 1, 1, '2020-11-15 15:12:26', 1, '2020-11-15 15:12:26', 0);
INSERT INTO `t_cg_project_template`
VALUES (117, 1, 21, '${entity}SaveDTO', '.java',
        'package ${package.dto};\n\nimport io.swagger.annotations.ApiModel;\nimport io.swagger.annotations.ApiModelProperty;\nimport lombok.AllArgsConstructor;\nimport lombok.Builder;\nimport lombok.Data;\nimport lombok.NoArgsConstructor;\n\n/**\n* <p> ${tableComment}保存提交参数 </p>\n*\n* @author ${ author }\n* @description\n* @date ${date}\n*/\n@Data\n@Builder\n@NoArgsConstructor\n@AllArgsConstructor\n@EqualsAndHashCode(callSuper = true)\n@ApiModel(\"${tableComment}保存提交参数\")\npublic class ${entity}SaveDTO extends BaseDTO {\n\n<#list columnInfoList as item>\n    <#if item.columnNameDb != \"create_by\" && item.columnNameDb != \"create_time\" && item.columnNameDb != \"update_by\" && item.columnNameDb != \"update_time\" && item.columnNameDb != \"is_valid\"> \n        @ApiModelProperty(\"${item.columnComment}\")\n        <#if item.ifPrimaryKey>\n        @NotNull(groups = {Update.class}, message = \"${item.columnComment}不能为空!\")\n        </#if>\n        private ${item.columnTypeJava} ${item.columnNameJavaLower};\n        \n    </#if> \n</#list>\n\n}\n',
        0, 1, 1, '2020-12-12 14:50:34', 1, '2021-07-24 03:35:17', 0);

-- ----------------------------
-- Table structure for t_cg_project_velocity_context
-- ----------------------------
DROP TABLE IF EXISTS `t_cg_project_velocity_context`;
CREATE TABLE `t_cg_project_velocity_context`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `velocity`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板数据',
    `context`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
    `project_id`  int(11) NULL DEFAULT NULL COMMENT '所属项目',
    `create_by`   int(11) NOT NULL COMMENT '创建人',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_by`   int(11) NOT NULL COMMENT '修改人',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    `is_deleted`  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成器 - 项目 - 模板数据源' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_cg_project_velocity_context
-- ----------------------------

-- ----------------------------
-- Table structure for t_cg_table_config
-- ----------------------------
DROP TABLE IF EXISTS `t_cg_table_config`;
CREATE TABLE `t_cg_table_config`
(
    `id`                           int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `project_id`                   int(11) NOT NULL COMMENT '项目ID',
    `table_name`                   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '表名',
    `query_columns`                varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用于检索字段',
    `project_re_db_data_source_id` int(11) NOT NULL COMMENT '数据源ID',
    `package_name`                 varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '包名',
    `module_name`                  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板名',
    `data_type`                    tinyint(4) NOT NULL DEFAULT 1 COMMENT '数据类型（1：默认数据 2：测试模板生成配置数据）',
    `data_re_user_id`              int(11) NOT NULL COMMENT '所属用户ID',
    `create_by`                    int(11) NOT NULL COMMENT '创建人',
    `create_time`                  datetime                                                      NOT NULL COMMENT '创建时间',
    `update_by`                    int(11) NOT NULL COMMENT '修改人',
    `update_time`                  datetime                                                      NOT NULL COMMENT '修改时间',
    `is_deleted`                   tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成器 - 项目数据表配置表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_cg_table_config
-- ----------------------------

-- ----------------------------
-- Table structure for t_demo
-- ----------------------------
DROP TABLE IF EXISTS `t_demo`;
CREATE TABLE `t_demo`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`    varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
    `password`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
    `sex`         tinyint(4) NULL DEFAULT NULL COMMENT '性别',
    `start_time`  datetime NULL DEFAULT NULL COMMENT '开始时间',
    `end_time`    datetime NULL DEFAULT NULL COMMENT '结束时间',
    `remark`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `tenant_id`   int(11) NULL DEFAULT NULL COMMENT '租户ID',
    `num`         int(11) NULL DEFAULT 0 COMMENT '数量',
    `create_by`   int(11) NULL DEFAULT NULL COMMENT '创建人',
    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   int(11) NULL DEFAULT NULL COMMENT '修改人',
    `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `is_deleted`  tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
    `demo_json`   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'json',
    `num_json`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'json',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX         `idx_remark`(`remark`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '测试demo' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_demo
-- ----------------------------
INSERT INTO `t_demo`
VALUES (1, 'admin', '123', 0, '2021-08-25 00:00:00', '2021-10-25 23:59:59', NULL, 1, 79, 1, '2021-12-06 17:35:50', 1,
        '2022-10-19 10:10:05', 0, NULL, '');
INSERT INTO `t_demo`
VALUES (2, 'hello', '666', 0, NULL, NULL, NULL, 1, 0, 1, '2021-12-06 17:35:50', 1, '2022-09-05 10:49:32', 0, NULL,
        NULL);
INSERT INTO `t_demo`
VALUES (3, 'test', '123456', 1, NULL, NULL, NULL, 1, 0, 1, '2021-12-06 17:35:50', 1, '2022-09-05 10:49:16', 0, NULL,
        NULL);

-- ----------------------------
-- Table structure for t_st_crawler_article_info
-- ----------------------------
DROP TABLE IF EXISTS `t_st_crawler_article_info`;
CREATE TABLE `t_st_crawler_article_info`
(
    `article_info_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `website_id`      int(11) NULL DEFAULT NULL COMMENT '网站id(关联表`t_st_crawler_website`字段`website_id`)',
    `article_id`      int(11) NULL DEFAULT NULL COMMENT '文章id',
    `title`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
    `category`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章所属分类',
    `content`         mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文章内容',
    `url`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章url地址',
    `publish_time`    datetime NULL DEFAULT NULL COMMENT '发布时间',
    `create_by`       int(11) NOT NULL COMMENT '创建人',
    `create_time`     datetime NOT NULL COMMENT '创建时间',
    `update_by`       int(11) NOT NULL COMMENT '修改人',
    `update_time`     datetime NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`article_info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '小工具 - 爬虫 - 文章信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_st_crawler_article_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_st_crawler_website
-- ----------------------------
DROP TABLE IF EXISTS `t_st_crawler_website`;
CREATE TABLE `t_st_crawler_website`
(
    `website_id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '名称',
    `url`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'url地址',
    `create_by`   int(11) NOT NULL COMMENT '创建人',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_by`   int(11) NOT NULL COMMENT '修改人',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`website_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '小工具 - 爬虫 - 网站管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_st_crawler_website
-- ----------------------------
INSERT INTO `t_st_crawler_website`
VALUES (1, '郑清个人博客', 'https://zhengqing.blog.csdn.net/article/list/1', 1, '2020-08-22 15:01:51', 1,
        '2022-07-20 17:46:43');

-- ----------------------------
-- Table structure for t_st_db_data_source
-- ----------------------------
DROP TABLE IF EXISTS `t_st_db_data_source`;
CREATE TABLE `t_st_db_data_source`
(
    `id`                int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`              varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库名称',
    `ip_address`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
    `port`              varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '端口',
    `username`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
    `password`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
    `type`              varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '数据库类型(关联表`t_sys_dict`字段`st_db_data_source_type`分类value值)',
    `driver_class_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '驱动程序',
    `remark`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `create_by`         int(11) NOT NULL COMMENT '创建人',
    `create_time`       datetime NOT NULL COMMENT '创建时间',
    `update_by`         int(11) NOT NULL COMMENT '修改人',
    `update_time`       datetime NOT NULL COMMENT '修改时间',
    `is_deleted`        tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '小工具 - 数据库 - 数据源配置信息表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_st_db_data_source
-- ----------------------------
INSERT INTO `t_st_db_data_source`
VALUES (19, '本地mysql', '127.0.0.1', '3306', 'root', 'root', '1', 'com.mysql.cj.jdbc.Driver', '本地数据库...', 1,
        '2020-09-02 23:51:22', 1, '2022-07-25 11:34:06', 0);

-- ----------------------------
-- Table structure for t_st_other_anonymity
-- ----------------------------
DROP TABLE IF EXISTS `t_st_other_anonymity`;
CREATE TABLE `t_st_other_anonymity`
(
    `id`                     int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `content`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
    `anonymous_user_name`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '匿名用户名',
    `anonymous_handler_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '匿名处理人',
    `status`                 tinyint(1) NULL DEFAULT NULL COMMENT '状态：是否处理 （0:未处理  1:已处理）',
    `remark`                 varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `create_time`            datetime NOT NULL COMMENT '创建时间',
    `update_time`            datetime NOT NULL COMMENT '修改时间',
    `handle_time`            datetime NULL DEFAULT NULL COMMENT '处理时间',
    `is_deleted`             tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '小工具 - 其它 - 匿名事件表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_st_other_anonymity
-- ----------------------------
INSERT INTO `t_st_other_anonymity`
VALUES (1, 'hi', '11', 'fs', 1, 'fds', '2022-07-16 00:47:39', '2022-07-16 01:13:05', '2022-07-16 01:13:05', 0);

-- ----------------------------
-- Table structure for t_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict`;
CREATE TABLE `t_sys_dict`
(
    `id`           int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `dict_type_id` int(11) UNSIGNED NOT NULL COMMENT '字典类型id',
    `code`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型编码',
    `name`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典名',
    `value`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典值',
    `status`       tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态(0->停用 1->正常)',
    `sort`         int(11) NOT NULL COMMENT '排序',
    `remark`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `create_by`    int(11) UNSIGNED NOT NULL COMMENT '创建人',
    `create_time`  datetime                                                     NOT NULL COMMENT '创建时间',
    `update_by`    int(11) UNSIGNED NOT NULL COMMENT '修改人',
    `update_time`  datetime                                                     NOT NULL COMMENT '修改时间',
    `is_deleted`   tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 184 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '基础模块-数据字典' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_dict
-- ----------------------------
INSERT INTO `t_sys_dict`
VALUES (75, 1, 'permission_btn', '添加', 'add', 1, 1, '', 1, '2020-08-22 15:01:51', 1, '2021-08-28 00:31:47', 0);
INSERT INTO `t_sys_dict`
VALUES (76, 1, 'permission_btn', '删除', 'delete', 1, 2, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:00', 0);
INSERT INTO `t_sys_dict`
VALUES (77, 1, 'permission_btn', '编辑', 'edit', 1, 3, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:00', 0);
INSERT INTO `t_sys_dict`
VALUES (78, 1, 'permission_btn', '查询', 'query', 1, 4, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:00', 0);
INSERT INTO `t_sys_dict`
VALUES (79, 1, 'permission_btn', '重置', 'reset', 1, 5, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:00', 0);
INSERT INTO `t_sys_dict`
VALUES (80, 1, 'permission_btn', '详情', 'detail', 1, 6, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:00', 0);
INSERT INTO `t_sys_dict`
VALUES (81, 1, 'permission_btn', '保存', 'save', 1, 7, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:00', 0);
INSERT INTO `t_sys_dict`
VALUES (98, 1, 'permission_btn', '权限', 'permission', 1, 24, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01',
        0);
INSERT INTO `t_sys_dict`
VALUES (117, 1, 'permission_btn', '开启授权', 'open_authorization', 1, 27, NULL, 1, '2020-08-22 15:01:51', 0,
        '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict`
VALUES (141, 1, 'permission_btn', '刷新', 'refresh', 1, 28, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01',
        0);
INSERT INTO `t_sys_dict`
VALUES (153, 1, 'permission_btn', '导出', 'export', 1, 29, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict`
VALUES (154, 1, 'permission_btn', '表格列过滤', 'column_filter', 1, 30, NULL, 1, '2020-08-22 15:01:51', 0,
        '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict`
VALUES (155, 2, 'file_suffix', '.java', '.java', 1, 1, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict`
VALUES (156, 2, 'file_suffix', '.xml', '.xml', 1, 2, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict`
VALUES (157, 2, 'file_suffix', '.py', '.py', 1, 3, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict`
VALUES (158, 2, 'file_suffix', '.vue', '.vue', 1, 4, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict`
VALUES (159, 2, 'file_suffix', '.md', '.md', 1, 5, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict`
VALUES (160, 2, 'file_suffix', '.php', '.php', 1, 6, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict`
VALUES (161, 2, 'file_suffix', '.html', '.html', 1, 7, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict`
VALUES (162, 2, 'file_suffix', '.js', '.js', 1, 8, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict`
VALUES (163, 2, 'file_suffix', '.jsp', '.jsp', 1, 9, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict`
VALUES (165, 3, 'element_icon', 'AddLocation', 'AddLocation', 1, 1, '', 1, '2020-08-30 03:05:05', 1,
        '2022-07-22 09:57:53', 0);
INSERT INTO `t_sys_dict`
VALUES (166, 3, 'element_icon', 'Aim', 'Aim', 1, 2, '', 1, '2020-08-30 03:05:24', 1, '2022-07-22 09:58:01', 0);
INSERT INTO `t_sys_dict`
VALUES (167, 3, 'element_icon', 'AlarmClock', 'AlarmClock', 1, 3, '', 1, '2020-08-30 03:05:31', 1,
        '2022-07-22 09:58:07', 0);
INSERT INTO `t_sys_dict`
VALUES (168, 3, 'element_icon', 'Apple', 'Apple', 1, 4, '', 1, '2020-08-30 03:12:30', 1, '2022-07-22 09:58:15', 0);
INSERT INTO `t_sys_dict`
VALUES (169, 3, 'element_icon', 'ArrowDown', 'ArrowDown', 1, 5, '', 1, '2020-08-30 03:14:05', 1, '2022-07-22 09:58:38',
        0);
INSERT INTO `t_sys_dict`
VALUES (170, 3, 'element_icon', 'ArrowDownBold', 'ArrowDownBold', 1, 6, '', 1, '2020-08-30 03:14:28', 1,
        '2022-07-22 09:58:45', 0);
INSERT INTO `t_sys_dict`
VALUES (171, 3, 'element_icon', 'ArrowLeft', 'ArrowLeft', 1, 7, '', 1, '2020-08-30 03:14:56', 1, '2022-07-22 09:58:51',
        0);
INSERT INTO `t_sys_dict`
VALUES (172, 3, 'element_icon', 'ArrowRight', 'ArrowRight', 1, 8, '', 1, '2020-08-30 03:15:49', 1,
        '2022-07-22 09:59:05', 0);
INSERT INTO `t_sys_dict`
VALUES (173, 3, 'element_icon', 'ArrowRightBold', 'ArrowRightBold', 1, 9, '', 1, '2020-08-30 03:16:51', 1,
        '2022-07-22 09:59:13', 0);
INSERT INTO `t_sys_dict`
VALUES (174, 3, 'element_icon', 'ArrowUp', 'ArrowUp', 1, 10, '', 1, '2020-08-30 03:18:32', 1, '2022-07-22 09:59:19', 0);
INSERT INTO `t_sys_dict`
VALUES (175, 4, 'st_crawler_csdn_export_data_type', 'HTML', '1', 1, 1, NULL, 1, '2020-08-30 16:58:01', 0,
        '2021-08-28 00:03:02', 0);
INSERT INTO `t_sys_dict`
VALUES (176, 4, 'st_crawler_csdn_export_data_type', 'MARKDOWN', '2', 1, 2, NULL, 1, '2020-08-30 16:58:09', 0,
        '2021-08-28 00:03:02', 0);
INSERT INTO `t_sys_dict`
VALUES (177, 4, 'st_crawler_csdn_export_data_type', 'WORD', '3', 1, 3, NULL, 1, '2020-08-30 16:58:18', 0,
        '2021-08-28 00:03:02', 0);
INSERT INTO `t_sys_dict`
VALUES (178, 5, 'st_db_data_source_type', 'MySQL', '1', 1, 1, NULL, 1, '2020-09-02 20:56:22', 0, '2021-08-28 00:03:02',
        0);
INSERT INTO `t_sys_dict`
VALUES (179, 5, 'st_db_data_source_type', 'Oracle', '2', 1, 2, NULL, 1, '2020-09-02 20:56:22', 0, '2021-08-28 00:03:02',
        0);
INSERT INTO `t_sys_dict`
VALUES (180, 1, 'permission_btn', '设计表', 'design_table', 1, 31, NULL, 1, '2020-09-06 19:09:40', 0,
        '2021-08-28 00:03:02', 0);
INSERT INTO `t_sys_dict`
VALUES (181, 6, 'oauth_type', 'gitee', '1', 1, 1, NULL, 1, '2020-12-06 13:16:39', 0, '2021-08-28 00:03:02', 0);
INSERT INTO `t_sys_dict`
VALUES (182, 6, 'oauth_type', 'github', '2', 1, 2, NULL, 1, '2020-12-06 13:16:54', 0, '2021-08-28 00:03:02', 0);
INSERT INTO `t_sys_dict`
VALUES (183, 6, 'oauth_type', 'qq', '3', 1, 3, NULL, 1, '2020-12-06 13:17:03', 0, '2021-08-28 00:03:02', 0);

-- ----------------------------
-- Table structure for t_sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict_type`;
CREATE TABLE `t_sys_dict_type`
(
    `id`          int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `code`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型编码',
    `name`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型名称(展示用)',
    `status`      tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态(0->停用 1->正常)',
    `sort`        int(10) UNSIGNED NOT NULL DEFAULT 1 COMMENT '排序',
    `is_fixed`    tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否固定(0->否 1->是)',
    `create_by`   int(11) UNSIGNED NOT NULL COMMENT '创建人',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_by`   int(11) UNSIGNED NOT NULL COMMENT '修改人',
    `update_time` datetime                                                     NOT NULL COMMENT '修改时间',
    `is_deleted`  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '基础模块-数据字典类型' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_dict_type
-- ----------------------------
INSERT INTO `t_sys_dict_type`
VALUES (1, 'permission_btn', '权限按钮', 1, 1, 0, 1, '2020-08-22 15:01:51', 1, '2021-08-28 23:00:04', 0);
INSERT INTO `t_sys_dict_type`
VALUES (2, 'file_suffix', '文件后缀名', 1, 4, 0, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_dict_type`
VALUES (3, 'element_icon', 'Element-Icon图标', 1, 2, 0, 1, '2020-08-30 02:52:36', 1, '2020-08-30 02:52:38', 0);
INSERT INTO `t_sys_dict_type`
VALUES (4, 'st_crawler_csdn_export_data_type', '小工具_爬虫_CSDN文章导出数据类型', 1, 6, 0, 1, '2020-08-30 16:46:07', 1,
        '2020-08-30 16:46:11', 0);
INSERT INTO `t_sys_dict_type`
VALUES (5, 'st_db_data_source_type', '小工具_数据库_数据源类型', 1, 5, 0, 1, '2020-09-02 20:41:27', 1,
        '2020-09-12 19:27:47', 0);
INSERT INTO `t_sys_dict_type`
VALUES (6, 'oauth_type', '第三方帐号授权类型', 1, 3, 0, 1, '2020-12-06 13:11:27', 1, '2020-12-06 13:21:45', 0);

-- ----------------------------
-- Table structure for t_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu`
(
    `menu_id`     int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `title`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称 - 英文',
    `icon`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
    `path`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单链接url',
    `parent_id`   int(11) NULL DEFAULT 0 COMMENT '父类菜单ID',
    `sort`        int(11) NULL DEFAULT NULL COMMENT '菜单排序',
    `component`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件名',
    `hidden`      tinyint(1) NULL DEFAULT 1 COMMENT '是否隐藏 1:隐藏 0:显示',
    `redirect`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '重定向路径',
    `status`      tinyint(1) NULL DEFAULT 1 COMMENT '菜单状态 1：启用  0：禁用',
    `type`        tinyint(4) NULL DEFAULT 0 COMMENT '菜单类型 0菜单 1按钮',
    `always_show` tinyint(1) NULL DEFAULT 1 COMMENT '是否总是显示 0:不显示 1:显示',
    `breadcrumb`  tinyint(1) NULL DEFAULT 1 COMMENT '面包屑 0 false 1 true',
    `create_by`   int(11) NOT NULL COMMENT '创建人',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_by`   int(11) NOT NULL COMMENT '修改人',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    `is_deleted`  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
    PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-菜单表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_menu
-- ----------------------------
INSERT INTO `t_sys_menu`
VALUES (1, '代码生成器', 'codeGenerator', 'Opportunity', '/codeGenerator', 0, 3, 'Layout', 0, '', 1, 0, 1, 1, 1,
        '2020-08-22 15:01:51', 1, '2020-08-30 03:18:52', 0);
INSERT INTO `t_sys_menu`
VALUES (2, '系统管理', 'system', 'Setting', '/system', 0, 2, 'Layout', 0, NULL, 1, 0, 1, 1, 1, '2020-08-22 15:01:51', 1,
        '2020-08-30 03:17:44', 0);
INSERT INTO `t_sys_menu`
VALUES (3, '菜单管理', 'menu', '', 'menu', 2, 9, 'system/menu/index', 0, NULL, 1, 0, 0, 1, 1, '2020-08-22 15:01:51', 1,
        '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu`
VALUES (4, '用户管理', 'user', '', 'user', 2, 1, 'system/user/index', 0, '', 1, 0, 0, 1, 1, '2020-08-22 15:01:51', 1,
        '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu`
VALUES (5, '角色管理', 'role', NULL, 'role', 2, 3, 'system/role/list', 0, NULL, 1, 0, 0, 1, 1, '2020-08-22 15:01:51', 1,
        '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu`
VALUES (6, '角色权限', 'roleForm', NULL, 'roleForm', 2, 8, 'system/role/form', 1, NULL, 1, 0, 1, 1, 1,
        '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu`
VALUES (8, '个人中心', 'personal-center', NULL, 'personal-center', 2, 2, 'system/personal-center/index', 0, NULL, 1, 0,
        0, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu`
VALUES (10, '数据字典', 'dict', NULL, 'dict', 2, 10, 'system/dict/index', 0, NULL, 1, 0, 0, 1, 1, '2020-08-22 15:01:51',
        1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu`
VALUES (11, '首页1', 'dashboard', 'Loading', '/', 0, 0, 'Layout', 0, '/dashboard', 1, 0, 0, 0, 1, '2020-08-22 15:01:51',
        1, '2020-09-09 17:35:09', 0);
INSERT INTO `t_sys_menu`
VALUES (12, '首页', 'Dashboard', '', '/dashboard', 11, 1, 'dashboard/index', 0, '', 1, 0, 0, 0, 1,
        '2020-08-22 15:01:51', 1, '2022-07-15 16:53:46', 0);
INSERT INTO `t_sys_menu`
VALUES (13, '项目管理', 'project', NULL, 'project', 1, 1, 'code-generator/project/index', 0, NULL, 1, 0, 0, 1, 1,
        '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu`
VALUES (15, '项目模板管理', 'project_template', NULL, 'project_template', 1, 3, 'code-generator/project-template/index',
        0, NULL, 1, 0, 0, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu`
VALUES (27, '小工具', 'small-tools', 'Sunny', '/small-tools', 0, 5, 'Layout', 0, NULL, 1, 0, 1, 1, 1,
        '2020-08-22 15:01:51', 1, '2020-08-30 03:18:44', 0);
INSERT INTO `t_sys_menu`
VALUES (28, '爬虫', 'crawler', 'MagicStick', '/crawler', 27, 1, 'parentView', 0, NULL, 1, 0, 1, 1, 1,
        '2020-08-22 15:01:51', 1, '2020-08-30 03:19:17', 0);
INSERT INTO `t_sys_menu`
VALUES (29, '网站列表', 'website', NULL, 'website', 28, 1, 'small-tools/crawler/website/list', 0, NULL, 1, 0, 0, 1, 1,
        '2020-08-22 15:01:51', 1, '2020-09-10 21:02:30', 0);
INSERT INTO `t_sys_menu`
VALUES (30, '文章信息', 'article-info', NULL, 'article-info', 28, 2, 'small-tools/crawler/article-info/list', 0, NULL,
        1, 0, 0, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu`
VALUES (31, '数据库', 'db', 'MostlyCloudy', '/db', 27, 3, 'parentView', 0, NULL, 1, 0, 1, 1, 1, '2020-08-22 15:01:51',
        1, '2020-08-30 03:19:17', 0);
INSERT INTO `t_sys_menu`
VALUES (32, '数据源管理', 'data-source', NULL, 'data-source', 31, 1, 'small-tools/db/data-source/index', 0, NULL, 1, 0,
        0, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu`
VALUES (33, '数据库操作', 'databases', NULL, 'databases', 31, 2, 'small-tools/db/databases/index', 0, NULL, 1, 0, 0, 1,
        1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu`
VALUES (34, '修改表字段信息', 'update-column-info', NULL, 'update-column-info', 31, 3,
        'small-tools/db/databases/update-column-info', 1, NULL, 1, 0, 0, 1, 1, '2020-08-22 15:01:51', 1,
        '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu`
VALUES (35, '其它', 'other', 'More', '/other', 27, 4, 'parentView', 0, NULL, 1, 0, 1, 1, 1, '2020-08-22 15:01:51', 1,
        '2020-08-30 03:19:17', 0);
INSERT INTO `t_sys_menu`
VALUES (36, '匿名反馈箱', 'anonymity', NULL, 'anonymity', 35, 1, 'small-tools/other/anonymity/index', 0, NULL, 1, 0, 0,
        1, 1, '2020-08-22 15:01:51', 1, '2021-01-13 07:38:37', 0);
INSERT INTO `t_sys_menu`
VALUES (37, 'freemaker模板数据配置', 'free-marker-template', NULL, 'free-marker-template', 1, 5,
        'code-generator/free-marker-template/index', 0, NULL, 1, 0, 0, 1, 1, '2020-08-22 15:01:51', 1,
        '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu`
VALUES (38, '生成代码', 'project-re-db', NULL, 'project-re-db', 1, 6, 'code-generator/project-re-db/index', 0, NULL, 1,
        0, 0, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu`
VALUES (39, '数据库表', 'table', NULL, 'project-re-db/table', 1, 7, 'code-generator/project-re-db/table', 1, NULL, 1, 0,
        1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu`
VALUES (40, '数据表字段', 'column', NULL, 'project-re-db/column', 1, 8, 'code-generator/project-re-db/column', 1, NULL,
        1, 0, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);

-- ----------------------------
-- Table structure for t_sys_oauth_client
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_oauth_client`;
CREATE TABLE `t_sys_oauth_client`
(
    `client_id`               varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端ID，唯一标识',
    `client_secret`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端访问秘钥，BCryptPasswordEncoder加密算法加密',
    `resource_ids`            varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '可访问资源id(英文逗号分隔)',
    `scope`                   varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '授权范围(英文逗号分隔)',
    `authorized_grant_types`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '授权类型(英文逗号分隔)',
    `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '重定向uri',
    `authorities`             varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '@PreAuthorize(\"hasAuthority(\' admin \')\")可以在方法上标志 用户或者说client 需要说明样的权限\r\n\n\n指定客户端所拥有的Spring Security的权限值\r\n(英文逗号分隔)',
    `access_token_validity`   int(11) NOT NULL COMMENT '令牌有效期(单位:秒)',
    `refresh_token_validity`  int(11) NOT NULL COMMENT '刷新令牌有效期(单位:秒)',
    `additional_information`  varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段,在Oauth的流程中没有实际的使用(JSON格式数据)',
    `autoapprove`             varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设置用户是否自动Approval操作, 默认值为 \' false \'\r\n可选值包括 \' true \',\' false \', \' read \',\' write \'.\r\n该字段只适用于grant_type=\"authorization_code\"的情况,当用户登录成功后,若该值为\' true \'或支持的scope值,则会跳过用户Approve的页面, 直接授权',
    `create_time`             datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`             datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统管理-oauth2授权客户端' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_oauth_client
-- ----------------------------
INSERT INTO `t_sys_oauth_client`
VALUES ('app', '123456', '', 'all', 'password,refresh_token', '', NULL, 3600, 259200, NULL, 'true',
        '2022-04-02 09:31:10', '2022-06-16 12:25:03');
INSERT INTO `t_sys_oauth_client`
VALUES ('client', '123456', '', 'all', 'password,refresh_token', '', NULL, 3600, 259200, NULL, 'true',
        '2022-04-02 09:31:10', '2022-06-16 12:25:03');
INSERT INTO `t_sys_oauth_client`
VALUES ('mini', '123456', '', 'all', 'password,refresh_token', '', NULL, 3600, 259200, NULL, 'true',
        '2022-04-02 09:31:10', '2022-06-16 12:25:03');
INSERT INTO `t_sys_oauth_client`
VALUES ('web', '123456', '', 'all', 'password,refresh_token,captcha', '', NULL, 3600, 259200, NULL, NULL,
        '2022-04-02 09:31:10', '2022-06-16 12:39:37');
INSERT INTO `t_sys_oauth_client`
VALUES ('zq_app_id', '123456', 'res1', 'all', 'authorization_code,refresh_token', 'http://127.0.0.1:10020/index.html',
        NULL, 3600, 259200, NULL, 'true', '2022-04-02 09:31:10', '2022-06-16 12:25:03');

-- ----------------------------
-- Table structure for t_sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_permission`;
CREATE TABLE `t_sys_permission`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `name`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '名称',
    `menu_id`     int(11) NOT NULL COMMENT '菜单ID',
    `btn_id`      int(11) NULL DEFAULT NULL COMMENT '按钮ID',
    `btn_perm`    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '按钮权限标识',
    `url_perm`    varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'URL权限标识',
    `create_by`   int(11) NOT NULL COMMENT '创建人',
    `create_time` datetime                                                NOT NULL COMMENT '创建时间',
    `update_by`   int(11) NOT NULL COMMENT '修改人',
    `update_time` datetime                                                NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-菜单关联权限表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_permission
-- ----------------------------
INSERT INTO `t_sys_permission`
VALUES (1, '查看用户', 4, 78, 'sys:user:view', 'GET:/web/api/user/*', 1, '2020-08-22 15:01:51', 1,
        '2020-08-22 15:01:51');
INSERT INTO `t_sys_permission`
VALUES (2, '编辑用户', 4, 75, 'sys:user:edit', 'PUT:/web/api/user/*', 1, '2020-08-22 15:01:51', 1,
        '2020-08-22 15:01:51');
INSERT INTO `t_sys_permission`
VALUES (3, '新增用户', 4, 76, 'sys:user:add', 'POST:/web/api/user', 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51');
INSERT INTO `t_sys_permission`
VALUES (4, '删除用户', 4, 77, 'sys:user:delete', 'DELETE:/web/api/user/*', 1, '2020-08-22 15:01:51', 1,
        '2020-08-22 15:01:51');
INSERT INTO `t_sys_permission`
VALUES (5, 'demo测试', 4, 77, 'sys:demo:test', 'GET:/demo/web/api/demo/demo/*', 1, '2020-08-22 15:01:51', 1,
        '2020-08-22 15:01:51');

-- ----------------------------
-- Table structure for t_sys_property
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_property`;
CREATE TABLE `t_sys_property`
(
    `id`          int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `key`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '属性key',
    `value`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性value',
    `remark`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
    `create_by`   int(10) UNSIGNED NOT NULL COMMENT '创建人',
    `create_time` datetime                                                      NOT NULL COMMENT '创建时间',
    `update_by`   int(10) UNSIGNED NOT NULL COMMENT '修改人',
    `update_time` datetime                                                      NOT NULL COMMENT '修改时间',
    `is_deleted`  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-系统属性' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_property
-- ----------------------------
INSERT INTO `t_sys_property`
VALUES (9, 'test', '测试', 'this is test data.', 0, '2021-09-07 10:43:26', 0, '2021-09-07 10:43:26', 0);
INSERT INTO `t_sys_property`
VALUES (10, 'hello', 'world', 'hello world !', 0, '2021-09-07 10:45:45', 0, '2021-09-07 10:45:45', 0);

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role`
(
    `role_id`     int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
    `code`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编号',
    `status`      tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态(1:开启(默认) 0:禁用)',
    `create_by`   int(11) NOT NULL COMMENT '创建人',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_by`   int(11) NOT NULL COMMENT '修改人',
    `update_time` datetime                                                     NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-角色管理表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role`
VALUES (1, '普通用户', 'persion', 1, 1, '2020-08-22 15:01:51', 1, '2022-08-05 20:00:30');
INSERT INTO `t_sys_role`
VALUES (9, '超级管理员', 'super_admin', 1, 1, '2020-08-22 15:01:51', 1, '2022-07-15 11:36:42');
INSERT INTO `t_sys_role`
VALUES (10, '测试用户', 'test', 1, 1, '2022-08-05 20:00:36', 1, '2022-08-06 15:59:10');

-- ----------------------------
-- Table structure for t_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_menu`;
CREATE TABLE `t_sys_role_menu`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id`     int(11) NOT NULL COMMENT '角色ID',
    `menu_id`     int(11) NOT NULL COMMENT '菜单ID',
    `create_by`   int(11) NOT NULL COMMENT '创建人',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   int(11) NOT NULL COMMENT '修改人',
    `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-角色菜单关联表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_role_menu
-- ----------------------------
INSERT INTO `t_sys_role_menu`
VALUES (5, 9, 11, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:01');
INSERT INTO `t_sys_role_menu`
VALUES (6, 9, 12, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:01');
INSERT INTO `t_sys_role_menu`
VALUES (7, 9, 1, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:02');
INSERT INTO `t_sys_role_menu`
VALUES (8, 9, 13, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:03');
INSERT INTO `t_sys_role_menu`
VALUES (9, 9, 15, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:04');
INSERT INTO `t_sys_role_menu`
VALUES (10, 9, 37, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:08');
INSERT INTO `t_sys_role_menu`
VALUES (11, 9, 38, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:09');
INSERT INTO `t_sys_role_menu`
VALUES (12, 9, 39, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:10');
INSERT INTO `t_sys_role_menu`
VALUES (13, 9, 40, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:11');
INSERT INTO `t_sys_role_menu`
VALUES (14, 9, 2, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:13');
INSERT INTO `t_sys_role_menu`
VALUES (15, 9, 4, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:14');
INSERT INTO `t_sys_role_menu`
VALUES (16, 9, 8, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:16');
INSERT INTO `t_sys_role_menu`
VALUES (17, 9, 5, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:17');
INSERT INTO `t_sys_role_menu`
VALUES (18, 9, 6, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:19');
INSERT INTO `t_sys_role_menu`
VALUES (19, 9, 3, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:20');
INSERT INTO `t_sys_role_menu`
VALUES (20, 9, 10, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:22');
INSERT INTO `t_sys_role_menu`
VALUES (21, 9, 27, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:24');
INSERT INTO `t_sys_role_menu`
VALUES (22, 9, 28, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:27');
INSERT INTO `t_sys_role_menu`
VALUES (23, 9, 29, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:29');
INSERT INTO `t_sys_role_menu`
VALUES (24, 9, 30, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:31');
INSERT INTO `t_sys_role_menu`
VALUES (25, 9, 20, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:32');
INSERT INTO `t_sys_role_menu`
VALUES (26, 9, 21, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:39');
INSERT INTO `t_sys_role_menu`
VALUES (27, 9, 22, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:50');
INSERT INTO `t_sys_role_menu`
VALUES (28, 9, 23, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:47');
INSERT INTO `t_sys_role_menu`
VALUES (31, 9, 31, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:44');
INSERT INTO `t_sys_role_menu`
VALUES (32, 9, 32, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:44');
INSERT INTO `t_sys_role_menu`
VALUES (33, 9, 33, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:44');
INSERT INTO `t_sys_role_menu`
VALUES (34, 9, 34, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:44');
INSERT INTO `t_sys_role_menu`
VALUES (35, 9, 35, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:44');
INSERT INTO `t_sys_role_menu`
VALUES (36, 9, 36, 1, '2020-12-06 01:06:26', 1, '2022-06-14 11:23:44');
INSERT INTO `t_sys_role_menu`
VALUES (49, 1, 11, 1, '2022-07-16 00:55:54', 1, '2022-07-16 00:55:54');
INSERT INTO `t_sys_role_menu`
VALUES (50, 1, 12, 1, '2022-07-16 00:55:54', 1, '2022-07-16 00:55:54');
INSERT INTO `t_sys_role_menu`
VALUES (51, 1, 37, 1, '2022-07-16 00:55:54', 1, '2022-07-16 00:55:54');
INSERT INTO `t_sys_role_menu`
VALUES (52, 1, 1, 1, '2022-07-16 00:55:54', 1, '2022-07-16 00:55:54');

-- ----------------------------
-- Table structure for t_sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_permission`;
CREATE TABLE `t_sys_role_permission`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id`       int(11) NOT NULL COMMENT '角色ID',
    `permission_id` int(11) NOT NULL COMMENT '权限ID',
    `create_by`     int(11) NOT NULL COMMENT '创建人',
    `create_time`   datetime NOT NULL COMMENT '创建时间',
    `update_by`     int(11) NOT NULL COMMENT '修改人',
    `update_time`   datetime NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 836 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-角色关联权限表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_role_permission
-- ----------------------------
INSERT INTO `t_sys_role_permission`
VALUES (4, 1, 4, 1, '2022-06-14 15:29:57', 1, '2022-06-14 15:30:04');
INSERT INTO `t_sys_role_permission`
VALUES (824, 1, 411, 1, '2022-07-21 18:07:56', 1, '2022-07-21 18:07:56');
INSERT INTO `t_sys_role_permission`
VALUES (825, 1, 412, 1, '2022-07-21 18:07:56', 1, '2022-07-21 18:07:56');
INSERT INTO `t_sys_role_permission`
VALUES (826, 9, 1, 1, '2022-07-21 18:08:26', 1, '2022-07-21 18:08:26');
INSERT INTO `t_sys_role_permission`
VALUES (827, 9, 2, 1, '2022-07-21 18:08:26', 1, '2022-07-21 18:08:26');
INSERT INTO `t_sys_role_permission`
VALUES (828, 9, 3, 1, '2022-07-21 18:08:26', 1, '2022-07-21 18:08:26');
INSERT INTO `t_sys_role_permission`
VALUES (829, 9, 4, 1, '2022-07-21 18:08:26', 1, '2022-07-21 18:08:26');
INSERT INTO `t_sys_role_permission`
VALUES (830, 9, 5, 1, '2022-07-21 18:08:26', 1, '2022-07-21 18:08:26');
INSERT INTO `t_sys_role_permission`
VALUES (834, 1, 421, 1, '2022-07-22 14:38:12', 1, '2022-07-22 14:38:12');
INSERT INTO `t_sys_role_permission`
VALUES (835, 1, 424, 1, '2022-07-22 14:38:12', 1, '2022-07-22 14:38:12');

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user`
(
    `user_id`     int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
    `password`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码',
    `nickname`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
    `sex`         tinyint(2) NULL DEFAULT 0 COMMENT '性别(0:未知 1:男  2:女)',
    `phone`       varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
    `email`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    `avatar_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
    `create_by`   int(11) NOT NULL COMMENT '创建人',
    `create_time` datetime                                                      NOT NULL COMMENT '创建时间',
    `update_by`   int(11) NOT NULL COMMENT '修改人',
    `update_time` datetime                                                      NOT NULL COMMENT '修改时间',
    `is_deleted`  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理 - 用户基础信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user`
VALUES (1, 'admin', '$2a$10$ixRnaJGhG6Sr1d3XHwrBzO6ZcZGDrEwY.KQX3MFGllS4AwKPwkr7K', '郑清', 1, '15183303003',
        '10000@qq.com',
        'http://oss.zhengqingya.com/%E7%BE%8E%E5%9B%BE13.png?e=1657869600&token=1v94f3iDKR4xON6gz6V2yFZ_tkG0Ujs6stsadNSg:HuQBLQG_QbJQJYrNOaM1r7dYPbU=',
        1, '2020-08-22 15:01:51', 1, '2022-10-08 17:22:37', 0);
INSERT INTO `t_sys_user`
VALUES (2, 'test', '$2a$10$CH9IX8jARPy0.f9.uy8uAeXCAzbZGxgR.ES4JBZMMxtKmGZAQPepq', '测试号', 1, '', '',
        'http://oss.zhengqingya.com/%E7%BE%8E%E5%9B%BE13.png?e=1657685387&token=1v94f3iDKR4xON6gz6V2yFZ_tkG0Ujs6stsadNSg:MHEirqtjFqFzhU9HJ0498mJw5Oc=',
        1, '2020-08-22 15:01:51', 1, '2022-10-08 17:22:33', 0);

-- ----------------------------
-- Table structure for t_sys_user_re_oauth
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_re_oauth`;
CREATE TABLE `t_sys_user_re_oauth`
(
    `user_re_oauth_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`          int(11) NOT NULL COMMENT '用户id（关联表`t_sys_user`字段`user_id`）',
    `open_id`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '三方id',
    `oauth_type`       tinyint(4) NOT NULL COMMENT '第三方授权类型',
    `create_by`        int(11) NOT NULL COMMENT '创建人',
    `create_time`      datetime                                                      NOT NULL COMMENT '创建时间',
    `update_by`        int(11) NOT NULL COMMENT '修改人',
    `update_time`      datetime                                                      NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`user_re_oauth_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理 - 用户三方授权表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_user_re_oauth
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`     int(11) NOT NULL COMMENT '用户ID',
    `role_id`     int(11) NOT NULL COMMENT '角色ID',
    `create_by`   int(11) NOT NULL COMMENT '创建人',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_by`   int(11) NOT NULL COMMENT '修改人',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-用户角色关联表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_user_role
-- ----------------------------
INSERT INTO `t_sys_user_role`
VALUES (1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51');
INSERT INTO `t_sys_user_role`
VALUES (4, 1, 9, 1, '2022-06-14 09:53:59', 1, '2022-06-14 09:54:01');
INSERT INTO `t_sys_user_role`
VALUES (5, 3, 1, 1, '2022-07-15 16:03:01', 1, '2022-07-15 16:03:01');
INSERT INTO `t_sys_user_role`
VALUES (12, 2, 1, 1, '2022-07-15 17:34:06', 1, '2022-07-15 17:34:06');

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint(20) NOT NULL,
    `xid`           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `context`       varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `rollback_info` longblob                                                NOT NULL,
    `log_status`    int(11) NOT NULL,
    `log_created`   datetime                                                NOT NULL,
    `log_modified`  datetime                                                NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET
FOREIGN_KEY_CHECKS = 1;
