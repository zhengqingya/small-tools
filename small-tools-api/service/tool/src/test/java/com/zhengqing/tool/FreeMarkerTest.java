package com.zhengqing.tool;

import com.google.common.collect.Maps;
import com.zhengqing.common.core.constant.AppConstant;
import com.zhengqing.common.base.util.FreeMarkerUtil;
import com.zhengqing.common.base.util.MyFileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 1.创建配置实例 2.获得模板 3.创建数据模型[数据模型可以是List、Map对象 注意:Map类型的key必须是String类型] 4.将模板和数据模型合并
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/1 0:53
 */
public class FreeMarkerTest {

    @Test
    public void testGenerateAndCreateFile() throws Exception {
        // 1、创建配置对象 (注:这里需要传递一个版本)
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        File file = MyFileUtil.touch(AppConstant.PROJECT_ROOT_DIRECTORY + "/template");
        // 2、读取模板文件夹 (设置要加载的模板文件的路径)
        cfg.setDirectoryForTemplateLoading(file);
        // 3、设置模板的编码格式
        cfg.setDefaultEncoding("UTF-8");

        // 4、获取模板对象 (test.ftl是模板名称)
        Template template = cfg.getTemplate("/demo/test.ftl");

        // 5、创建数据模型(这里使用map类型) -- [数据模型可以是List、Map对象 注意:Map类型的key必须是String类型]
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", "郑清");
        // map2存储的是a标签的href和显示名字
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("href", "https://www.baidu.com");
        map2.put("name", "百度");
        map.put("a", map2);

        // 6、将模板和数据模型合并 --> 输出模板,生成文件
        File fileFinal = new File(file, "/demo/test.java");
        PrintWriter pw = new PrintWriter(fileFinal);
        // 合并 map:数据模型 pw:输出流对象
        template.process(map, pw);
        // 关闭流
        pw.close();
        System.out.println(MyFileUtil.readFileContent(fileFinal));
    }

    @Test
    public void testGenerate() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("username", "郑清");
        String templateStr = "${username}测试数据...";
        String str = FreeMarkerUtil.generateTemplateData(map, templateStr);
        System.out.println(str);
    }

}
