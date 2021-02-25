package com.zhengqing.common.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import com.zhengqing.common.exception.MyException;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * <p>
 * FreeMarker工具类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/2/25 11:16
 */
public class FreeMarkerUtil {

    public static String generateTemplateData(Map<String, Object> templateDataMap, String templateContent) {
        String templateData = "";
        try {
            StringWriter stringWriter = new StringWriter();
            Template template = new Template("template", new StringReader(templateContent),
                new Configuration(Configuration.VERSION_2_3_28));
            template.process(templateDataMap, stringWriter);
            templateData = stringWriter.toString();
            stringWriter.close();
        } catch (Exception e) {
            throw new MyException("《FreeMarker合并模板》 异常：" + e.getMessage());
        }
        return templateData;
    }

}
