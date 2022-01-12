package com.zhengqing.common.config.mybatis.data.permission;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;


/**
 * <p> mybatis-plus 数据权限插件 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/1/12 14:36
 */
@Slf4j
@AllArgsConstructor
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataScopeInterceptor implements Interceptor {

    /**
     * 数据源
     */
    private DataSource dataSource;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        // 先判断是不是SELECT操作 不是直接过滤
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        // 执行的SQL语句
        String originalSql = boundSql.getSql();
        // SQL语句的参数
        Object parameterObject = boundSql.getParameterObject();

        // TODO 这里对执行SQL进行自定义处理...

        metaObject.setValue("delegate.boundSql.sql", originalSql);
        return invocation.proceed();
    }

    /**
     * 生成拦截对象的代理
     *
     * @param target 目标对象
     * @return 代理对象
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * mybatis配置的属性
     *
     * @param properties mybatis配置的属性
     */
    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 查找参数是否包括DataScope对象
     *
     * @param parameterObj 参数列表
     * @return DataScope
     */
    //    private DataScope findDataScopeObject(Object parameterObj) {
    //        if (parameterObj instanceof DataScope) {
    //            return (DataScope) parameterObj;
    //        } else if (parameterObj instanceof Map) {
    //            for (Object val : ((Map<?, ?>) parameterObj).values()) {
    //                if (val instanceof DataScope) {
    //                    return (DataScope) val;
    //                }
    //            }
    //        }
    //        return null;
    //    }

}
