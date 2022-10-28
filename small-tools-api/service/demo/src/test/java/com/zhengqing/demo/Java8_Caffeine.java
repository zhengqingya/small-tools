package com.zhengqing.demo;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * <p> Caffeine </p>
 *
 * @author zhengqingya
 * @description 文档见： https://github.com/ben-manes/caffeine/wiki/Population-zh-CN
 * @date 2022/10/28 14:02
 */
@Slf4j
public class Java8_Caffeine {

    @Test
    public void test01() throws Exception {
        // 构建cache对象
        Cache<String, Object> cache = Caffeine.newBuilder().build();

        // 存数据
        cache.put("name", "zhengqingya");

        // 取数据
        Object name = cache.getIfPresent("name");
        System.out.println(name);


        // 取数据，如果未命中则查询db
        Object age = cache.get("age", key -> {
            log.info("查db...");
            return 18;
        });
        System.out.println(age);
    }

    @Test
    public void test_maximumSize() throws Exception {
        // 创建缓存对象
        Cache<String, String> cache = Caffeine.newBuilder()
                // 设置缓存大小上限为1
                .maximumSize(1)
                .build();
        // 存数据
        cache.put("name", "zhengqingya");
        cache.put("age", "18");
        TimeUnit.SECONDS.sleep(1);
        // 取数据
        System.out.println(cache.getIfPresent("name"));
        System.out.println(cache.getIfPresent("age"));
    }

    @Test
    public void test_expireAfterWrite() throws Exception {
        // 创建缓存对象
        Cache<String, String> cache = Caffeine.newBuilder()
                // 设置缓存有效期为3秒，从最后一次写入开始计时
                .expireAfterWrite(Duration.ofSeconds(3))
                .build();
        // 存数据
        cache.put("name", "zhengqingya");
        TimeUnit.SECONDS.sleep(3);
        // 取数据
        Object name = cache.getIfPresent("name");
        System.out.println(name);
    }

}
