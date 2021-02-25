package com.zhengqing.tool;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;
import com.zhengqing.common.constant.AppConstant;
import com.zhengqing.common.util.DocUtil;
import com.zhengqing.common.util.MyFileUtil;

/**
 * <p>
 * word替换测试类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/2/25 16:37
 */
public class WordTest {

    private final String srcPath = AppConstant.PROJECT_ROOT_DIRECTORY + "/template/word-replace-test.docx";
    private final String destPath = AppConstant.PROJECT_ROOT_DIRECTORY + "/template/word-replace-test-generate.docx";

    @Test
    public void testReplaceWord() throws Exception {
        Map<String, String> templateDataMap = Maps.newHashMap();
        templateDataMap.put("name", "张三");
        DocUtil.replaceWordContent(srcPath, destPath, templateDataMap);
    }

    @Test
    public void testReplaceWord2() throws Exception {
        Map<String, String> templateDataMap = Maps.newHashMap();
        templateDataMap.put("name", "李四");
        byte[] wordBytes = DocUtil.replaceWordContent(MyFileUtil.readBytes(srcPath), templateDataMap);
        MyFileUtil.writeFileContent(wordBytes, destPath);
    }

}
