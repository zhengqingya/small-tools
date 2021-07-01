package com.zhengqing.common.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * <p>
 * 七牛云上传文件工具类$
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/4$ 14:21$
 */
@Slf4j
@Component
public class QiniuFileUtil implements InitializingBean {

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private Auth auth;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.domain}")
    private String domain;

    @Value("${qiniu.expireInSeconds}")
    private long expireInSeconds;

    /**
     * 定义七牛云上传的相关策略
     */
    private StringMap putPolicy;

    /**
     * 上传前台传过来的文件
     *
     * @param file:     文件
     * @param fileName: 文件名
     * @return: 成功则返回下载地址url
     */
    @SneakyThrows(Exception.class)
    public String uploadFile(MultipartFile file, String fileName) {
        // delete(fileName);
        Response response = this.uploadManager.put(file.getInputStream(), fileName, getUploadToken(), null, null);
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(file.getInputStream(), fileName, getUploadToken(), null, null);
            retry++;
        }
        if (response.statusCode == 200) {
            return downloadFile(fileName, expireInSeconds);
        }
        return "上传失败!";
    }

    /**
     * 以文件的形式上传
     *
     * @param file:     文件
     * @param fileName: 文件名
     * @return: 成功则返回下载地址url
     */
    @SneakyThrows(Exception.class)
    public String uploadFile(File file, String fileName) {
        try {
            delete(fileName);
        } catch (Exception e) {
            log.error("七牛云上传文件时删除久文件数据({})时错误： 【{}】", fileName, e.getMessage());
        }
        Response response = this.uploadManager.put(file, fileName, getUploadToken());
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(file, fileName, getUploadToken());
            retry++;
        }
        if (response.statusCode == 200) {
            return downloadFile(fileName, expireInSeconds);
        }
        return "上传失败!";
    }

    /**
     * 以流的形式上传
     *
     * @param inputStream: 流
     * @param fileName:    文件名
     * @return: 成功则返回下载地址url
     */
    @SneakyThrows(Exception.class)
    public String uploadFile(InputStream inputStream, String fileName) {
        // delete(fileName);
        Response response = this.uploadManager.put(inputStream, fileName, getUploadToken(), null, null);
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(inputStream, fileName, getUploadToken(), null, null);
            retry++;
        }
        if (response.statusCode == 200) {
            return downloadFile(fileName, expireInSeconds);
        }
        return "上传失败!";
    }

    /**
     * 下载文件 (参考私有空间下载：https://developer.qiniu.com/kodo/sdk/1239/java#private-get)
     *
     * @param fileName:        文件名
     * @param expireInSeconds: 过期时间(默认1小时)
     * @return: 返回下载地址url
     * @author zhengqingya
     * @date 2020/10/25 18:37
     */
    @SneakyThrows(Exception.class)
    public String downloadFile(String fileName, long expireInSeconds) {
        // 公开空间下载地址方式 "http://" + domain + "/" + fileName;

        // 私有空间下载地址方式
        String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        String publicUrl = String.format("http://%s/%s", domain, encodedFileName);
        String finalDownloadUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        log.debug("【七牛云】私有空间 文件名：【{}】 下载地址url：【{}】", fileName, finalDownloadUrl);
        return finalDownloadUrl;
    }

    /**
     * 删除文件
     *
     * @param key: 即上传文件时的fileName
     * @return: 操作结果
     */
    public String delete(String key) {
        try {
            Response response = bucketManager.delete(this.bucket, key);
            int retry = 0;
            while (response.needRetry() && retry++ < 3) {
                response = bucketManager.delete(bucket, key);
            }
            return response.statusCode == 200 ? "删除成功!" : "删除失败!";
        } catch (QiniuException e) {
            log.error("《七牛云》 删除失败：{}", e.getMessage());
        }
        return "删除失败!";
    }

    @Override
    public void afterPropertiesSet() {
        this.putPolicy = new StringMap();
        putPolicy.put("returnBody",
                "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
    }

    /**
     * 获取上传凭证
     */
    private String getUploadToken() {
        return this.auth.uploadToken(bucket, null, 3600, putPolicy);
    }

}
