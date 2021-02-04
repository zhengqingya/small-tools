package com.zhengqing.common.util;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * RestTemplate工具类 $
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/7/7$ 14:10$
 */
@Slf4j
@Component
public class RestTemplateUtil {

    private static RestTemplate restTemplate;

    @Autowired
    public RestTemplateUtil(RestTemplate restTemplate) {
        // 复杂构造函数的使用
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        // 设置超时
        requestFactory.setConnectTimeout(300000);
        requestFactory.setReadTimeout(300000);
        restTemplate.setRequestFactory(requestFactory);
        RestTemplateUtil.restTemplate = restTemplate;
    }

    /**
     * get请求
     *
     * @param url:
     *            请求地址
     * @param params:
     *            请求参数
     * @return: 响应数据
     * @author : zhengqing
     * @date : 2020/7/7 14:22
     */
    public static String getForMap(String url, Map<String, String> params) {
        StringBuffer stringBuffer = new StringBuffer(url);
        Iterator iterator = params.entrySet().iterator();
        if (iterator.hasNext()) {
            stringBuffer.append("?");
            Object element;
            while (iterator.hasNext()) {
                element = iterator.next();
                Map.Entry<String, Object> entry = (Map.Entry)element;
                // 过滤value为null，value为null时进行拼接字符串会变成 "null"字符串
                if (entry.getValue() != null) {
                    stringBuffer.append(element).append("&");
                }
                url = stringBuffer.substring(0, stringBuffer.length() - 1);
            }
        }
        String dataStr = restTemplate.getForObject(url, String.class);
        log.debug("url请求: 【{}】 响应数据：【{}】", url, dataStr);
        return dataStr;
    }

    /**
     * post请求
     *
     * @param url:
     *            请求地址
     * @param params:
     *            请求参数
     * @return: 响应数据
     * @author : zhengqing
     * @date : 2020/7/7 14:30
     */
    public static String postForMap(String url, Map<String, String> params) {
        String dataStr = restTemplate.postForEntity(url, params, String.class).getBody();
        log.debug("url请求: 【{}】 响应数据：【{}】", url, dataStr);
        return dataStr;
    }

    /**
     * post请求
     *
     * @param uploadUrl:
     *            上传文件url
     * @param file:
     *            文件信息
     * @return: 响应数据
     * @author : zhengqing
     * @date : 2020/12/2 10:24
     */
    public static String post(String uploadUrl, File file) {
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);

        // 设置请求体，注意是LinkedMultiValueMap
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("file", fileSystemResource);
        form.add("filename", file.getName());

        // 用HttpEntity封装整个请求报文
        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);

        String dataStr = restTemplate.postForObject(uploadUrl, files, String.class);
        log.debug("url请求: 【{}】 响应数据：【{}】", uploadUrl, dataStr);
        return dataStr;
    }

}
