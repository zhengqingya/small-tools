package com.zhengqing.tool;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.util.MyFileUtil;
import com.zhengqing.tool.db.enums.StDbDataSourceTypeEnum;
import com.zhengqing.tool.db.model.vo.StDbTableColumnListVO;
import com.zhengqing.tool.db.service.impl.StDbJdbcServiceImpl;
import com.zhengqing.tool.generator.model.bo.CgGeneratorCodeTemplateFileBO;
import com.zhengqing.tool.util.GenerateCodeUtil;
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
        // 基本数据字段
        String ipAddress = "127.0.0.1";
        String port = "3306";
        String username = "root";
        String password = "root";
        String dbName = "demo";
        String tableName = "t_test";
        String parentPackageName = "com.zhengqingya.demo";
        String moduleName = "xxx";
        String tplRootPath = AppConstant.PROJECT_ROOT_DIRECTORY + "/template/small-tools";

        // 查询字段数据
        List<String> queryColumnList = Lists.newArrayList("username");

        // 包名信息
        Map<String, String> packageNameInfoMap = Maps.newHashMap();

        // 生成文件信息
        List<CgGeneratorCodeTemplateFileBO> tplFileInfoList = Lists.newArrayList();
        this.handleTplContentData(tplFileInfoList, tplRootPath, "", parentPackageName + AppConstant.SEPARATOR_SPOT + moduleName, packageNameInfoMap);

        // 查询表字段信息
        StDbTableColumnListVO columnInfo = new StDbJdbcServiceImpl().getAllColumnsByDbInfo(StDbDataSourceTypeEnum.MySQL, ipAddress, port, username, password, dbName, tableName);

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
                    String packageNameItem = packageName + tplRootFile.getPath().replaceFirst(tplRootPath, "").replaceAll(AppConstant.SEPARATOR_SPRIT, AppConstant.SEPARATOR_SPOT);
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

}
