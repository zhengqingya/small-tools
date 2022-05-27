package com.zhengqing.common.base.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * <p>
 * Jsoup工具类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/10/31 20:18
 */
public class JsoupUtil {

    /**
     * 获取 document
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static Document getDocument(String url) throws IOException {
        Document document = Jsoup.connect(url).timeout(100000).ignoreContentType(true).userAgent(
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                .get();
        return document;
    }

    /**
     * 获取域名
     *
     * @param url
     * @return
     */
    public static String getDomain(String url) {
        return url.split("/")[0] + "//" + url.split("/")[2];
    }
}
