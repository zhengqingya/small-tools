package com.zhengqing.tool;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.util.MyFileUtil;
import com.zhengqing.common.util.YmlUtil;
import com.zhengqing.tool.db.enums.StDbDataSourceTypeEnum;
import com.zhengqing.tool.db.model.vo.StDbTableColumnListVO;
import com.zhengqing.tool.db.service.impl.StDbJdbcServiceImpl;
import com.zhengqing.tool.generator.model.bo.CgGeneratorCodeTemplateFileBO;
import com.zhengqing.tool.util.GenerateCodeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p> 代码生成器测试 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/7/29 11:23
 */
@Slf4j
public class GenerateTest {

    @Test
    public void testGenerateCodeAndCreateFile() throws Exception {
        // 读取基本生成配置信息（mysql连接配置等...）
        final String GENERATE_CONFIG_PATH = AppConstant.PROJECT_ROOT_DIRECTORY + "/src/main/resources/generate-config.yml";
        final GenerateConfig generateConfig = YmlUtil.getYml(GENERATE_CONFIG_PATH, GenerateConfig.class);
        final String ip = generateConfig.getIp();
        final String port = generateConfig.getPort();
        final String username = generateConfig.getUsername();
        final String password = generateConfig.getPassword();
        final String dbName = generateConfig.getDbName();
        final String tableName = generateConfig.getTableName();
        final String parentPackageName = generateConfig.getParentPackageName();
        final String moduleName = generateConfig.getModuleName();
        final String tplRootPath = AppConstant.PROJECT_ROOT_DIRECTORY + "/template/small-tools";

        // 查询字段数据
        List<String> queryColumnList = Lists.newArrayList("username");

        // 包名信息
        Map<String, String> packageNameInfoMap = Maps.newHashMap();

        // 生成文件信息
        List<CgGeneratorCodeTemplateFileBO> tplFileInfoList = Lists.newArrayList();
        this.handleTplContentData(tplFileInfoList, tplRootPath, "", parentPackageName + AppConstant.SEPARATOR_SPOT + moduleName, packageNameInfoMap);

        // 查询表字段信息
        StDbTableColumnListVO columnInfo = new StDbJdbcServiceImpl().getAllColumnsByDbInfo(StDbDataSourceTypeEnum.MySQL, ip, port, username, password, dbName, tableName);

        // 模板数据处理
        Map<String, Object> templateDataMap = GenerateCodeUtil.handleTplData(moduleName, columnInfo, packageNameInfoMap, queryColumnList);
        templateDataMap.put("author", "zhengqingya");

        // 先删除旧数据
        MyFileUtil.deleteFileOrFolder(AppConstant.FILE_PATH_CODE_GENERATOR_DATA_PATH);

        // 模板数据生成
        GenerateCodeUtil.generateTplFileData(tplFileInfoList, templateDataMap);
        log.info("=== FINISH ===");
    }

    /**
     * 递归获取模板文件数据
     *
     * @param tplFileInfoList    总模板文件数据
     * @param tplRootPath        模板根目录
     * @param tplChildDir        模板子文件夹
     * @param packageName        包名
     * @param packageNameInfoMap 包名信息
     * @return 模板文件数据
     * @author zhengqingya
     * @date 2021/8/21 6:41 下午
     */
    private void handleTplContentData(List<CgGeneratorCodeTemplateFileBO> tplFileInfoList, String tplRootPath, String tplChildDir, String packageName, Map<String, String> packageNameInfoMap) {
        File tplRootFile = new File(tplRootPath + tplChildDir);
        if (tplRootFile.exists() && tplRootFile.isDirectory()) {
            File[] tplFileArray = tplRootFile.listFiles();
            for (File tplFileItem : tplFileArray) {
                String tplFileItemName = tplFileItem.getName();
                if (tplFileItem.isDirectory()) {
                    this.handleTplContentData(tplFileInfoList, tplRootPath, tplChildDir + AppConstant.SEPARATOR_SPRIT + tplFileItemName, packageName, packageNameInfoMap);
                } else if (tplFileItem.isFile()) {
                    String packageNameItem = packageName + tplRootFile.getPath().replaceAll(AppConstant.SEPARATOR_BACKSLASH, AppConstant.SEPARATOR_SPRIT).replaceFirst(tplRootPath.replaceAll(AppConstant.SEPARATOR_BACKSLASH, AppConstant.SEPARATOR_SPRIT), "").replaceAll(AppConstant.SEPARATOR_SPRIT, AppConstant.SEPARATOR_SPOT);
                    packageNameInfoMap.put(UUID.randomUUID().toString(), packageNameItem);
                    String[] tplFileNameArray = tplFileItemName.split("\\" + AppConstant.SEPARATOR_SPOT);
                    tplFileInfoList.add(CgGeneratorCodeTemplateFileBO.builder()
                            .fileName(tplFileNameArray[0])
                            .generateFileSuffix(AppConstant.SEPARATOR_SPOT + tplFileNameArray[1])
                            .templateContent(MyFileUtil.readFileContent(tplFileItem))
                            .templateRePackage(packageNameItem)
                            .build());
                }
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("代码生成配置参数")
    public static class GenerateConfig {
        @ApiModelProperty("mysql-ip地址")
        private String ip;

        @ApiModelProperty("端口")
        private String port;

        @ApiModelProperty("用户名")
        private String username;

        @ApiModelProperty("密码")
        private String password;

        @ApiModelProperty("库名")
        private String dbName;

        @ApiModelProperty("表名")
        private String tableName;

        @ApiModelProperty("父包名")
        private String parentPackageName;

        @ApiModelProperty("模块名")
        private String moduleName;
    }

}
