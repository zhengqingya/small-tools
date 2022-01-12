package com.zhengqing.common.config.mybatis;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.zhengqing.common.config.mybatis.data.permission.DataScopeInterceptor;
import com.zhengqing.common.config.mybatis.data.permission.MyDataPermissionHandler;
import com.zhengqing.common.config.mybatis.plugins.SqlLogInterceptor;
import com.zhengqing.common.context.TenantIdContext;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

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
     * 需要设置租户ID的表
     */
    private static final Set<String> TENANT_ID_TABLE = new HashSet<>();

    static {
        TENANT_ID_TABLE.add("t_demo2");
    }


    /**
     * mybatis-plus分页插件
     * 文档：https://baomidou.com/pages/2976a3/#spring-boot
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        /**
         * 1、mybatis-plus多租户插件
         * 文档：https://baomidou.com/pages/aef2f2/#tenantlineinnerinterceptor
         */
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                return new LongValue(TenantIdContext.getTenantId());
            }

            @Override
            public String getTenantIdColumn() {
                return "tenant_id";
            }

            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
            @Override
            public boolean ignoreTable(String tableName) {
                if (!TENANT_ID_TABLE.contains(tableName)) {
                    // 不需要租户id
                    return true;
                }
                Boolean tenantIdFlag = TenantIdContext.getFlag();
                Assert.notNull(tenantIdFlag, "租户id不能为空！");
                return !tenantIdFlag;
            }
        }));

        // tips: 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor


        /**
         * 2、添加数据权限插件 {@link com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor}
         */
        DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor();
        // 添加自定义的数据权限处理器
        dataPermissionInterceptor.setDataPermissionHandler(new MyDataPermissionHandler());
        interceptor.addInnerInterceptor(dataPermissionInterceptor);

        /**
         * 3、mybatis-plus分页插件
         * 文档：https://baomidou.com/pages/2976a3/#spring-boot
         */
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

    /**
     * 数据权限插件
     *
     * @return DataScopeInterceptor
     */
    @Bean
    @ConditionalOnMissingBean
    public DataScopeInterceptor dataScopeInterceptor(DataSource dataSource) {
        return new DataScopeInterceptor(dataSource);
    }

}
