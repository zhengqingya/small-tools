package com.zhengqing.common.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.zhengqing.common.config.mybatis.plugins.SqlLogInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * MybatisPlus配置类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/23 9:46
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.zhengqing.*.**.mapper*", "com.zhengqing.*.*.**.mapper*"})
public class MybatisPlusConfig {

    /**
     * mybatis-plus分页插件<br>
     * 文档：https://mp.baomidou.com/guide/page.html <br>
     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        // paginationInterceptor.setLimit(你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);
//        return new PaginationInterceptor();
//    }
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * mybatis-plus SQL性能分析插件【生产环境可以关闭】 性能分析拦截器，用于输出每条 SQL 语句及其执行时间 【注：3.2.0+ 已移除`PerformanceInterceptor`】
     */
    // @Bean
    // @Profile({"dev", "test"}) // 设置 dev test 环境开启
    // public PerformanceInterceptor performanceInterceptor() {
    // SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长
    // performanceInterceptor.setMaxTime(3000);
    // SQL是否格式化 默认false
    // performanceInterceptor.setFormat(true);
    // return new PerformanceInterceptor();
    // }

    /**
     * sql 日志
     * on-off.mybatis-plus-sql-log值 => true:开启 false:关闭
     */
    @Bean
    @ConditionalOnProperty(value = "on-off.mybatis-plus-sql-log", havingValue = "true")
    public SqlLogInterceptor sqlLogInterceptor() {
        return new SqlLogInterceptor();
    }

}
