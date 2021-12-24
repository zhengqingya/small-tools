package com.zhengqing.common.config.mybatis.plugins;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 用于输出每条 SQL 语句及其执行时间
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/5/20 17:14
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = Statement.class),
        @Signature(type = StatementHandler.class, method = "batch", args = Statement.class)})
public class SqlLogInterceptor implements Interceptor {

    private static final String DRUID_POOLED_PREPARED_STATEMENT = "com.alibaba.druid.pool.DruidPooledPreparedStatement";
    private static final String T4C_PREPARED_STATEMENT = "oracle.jdbc.driver.T4CPreparedStatement";
    private static final String ORACLE_PREPARED_STATEMENT_WRAPPER = "oracle.jdbc.driver.OraclePreparedStatementWrapper";

    private Method oracleGetOriginalSqlMethod;
    private Method druidGetSqlMethod;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Statement statement;
        Object firstArg = invocation.getArgs()[0];
        if (Proxy.isProxyClass(firstArg.getClass())) {
            statement = (Statement) SystemMetaObject.forObject(firstArg).getValue("h.statement");
        } else {
            statement = (Statement) firstArg;
        }
        MetaObject stmtMetaObj = SystemMetaObject.forObject(statement);
        try {
            statement = (Statement) stmtMetaObj.getValue("stmt.statement");
        } catch (Exception e) {
            // do nothing
        }
        if (stmtMetaObj.hasGetter("delegate")) {
            // Hikari
            try {
                statement = (Statement) stmtMetaObj.getValue("delegate");
            } catch (Exception ignored) {

            }
        }

        String originalSql = null;
        String stmtClassName = statement.getClass().getName();
        if (DRUID_POOLED_PREPARED_STATEMENT.equals(stmtClassName)) {
            try {
                if (druidGetSqlMethod == null) {
                    Class<?> clazz = Class.forName(DRUID_POOLED_PREPARED_STATEMENT);
                    druidGetSqlMethod = clazz.getMethod("getSql");
                }
                Object stmtSql = druidGetSqlMethod.invoke(statement);
                if (stmtSql instanceof String) {
                    originalSql = (String) stmtSql;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (T4C_PREPARED_STATEMENT.equals(stmtClassName)
                || ORACLE_PREPARED_STATEMENT_WRAPPER.equals(stmtClassName)) {
            try {
                if (oracleGetOriginalSqlMethod != null) {
                    Object stmtSql = oracleGetOriginalSqlMethod.invoke(statement);
                    if (stmtSql instanceof String) {
                        originalSql = (String) stmtSql;
                    }
                } else {
                    Class<?> clazz = Class.forName(stmtClassName);
                    oracleGetOriginalSqlMethod = getMethodRegular(clazz, "getOriginalSql");
                    if (oracleGetOriginalSqlMethod != null) {
                        // OraclePreparedStatementWrapper is not a public class, need set this.
                        oracleGetOriginalSqlMethod.setAccessible(true);
                        if (null != oracleGetOriginalSqlMethod) {
                            Object stmtSql = oracleGetOriginalSqlMethod.invoke(statement);
                            if (stmtSql instanceof String) {
                                originalSql = (String) stmtSql;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                // ignore
            }
        }
        if (originalSql == null) {
            originalSql = statement.toString();
        }
        originalSql = originalSql.replaceAll("[\\s]+", StringPool.SPACE);
        int index = indexOfSqlStart(originalSql);
        if (index > 0) {
            // 这里拿到执行sql
            originalSql = originalSql.substring(index);
        }

        // 计算执行 SQL 耗时
        long start = SystemClock.now();
        Object result = invocation.proceed();
        long timing = SystemClock.now() - start;

        // SQL 打印执行结果
        Object target = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(target);
        MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        // 打印 sql
        System.err.println(String.format(
                "\n==============  Sql Start  ==============" + "\nExecute ID  ：%s" + "\nExecute SQL ：%s"
                        + "\nExecute Time：%s ms" + "\n==============  Sql  End   ==============\n",
                ms.getId(), originalSql, timing));
        return result;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * 获取此方法名的具体 Method
     *
     * @param clazz      class 对象
     * @param methodName 方法名
     * @return 方法
     */
    private Method getMethodRegular(Class<?> clazz, String methodName) {
        if (Object.class.equals(clazz)) {
            return null;
        }
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return getMethodRegular(clazz.getSuperclass(), methodName);
    }

    /**
     * 获取sql语句开头部分
     *
     * @param sql ignore
     * @return ignore
     */
    private int indexOfSqlStart(String sql) {
        String upperCaseSql = sql.toUpperCase();
        Set<Integer> set = new HashSet<>();
        set.add(upperCaseSql.indexOf("SELECT "));
        set.add(upperCaseSql.indexOf("UPDATE "));
        set.add(upperCaseSql.indexOf("INSERT "));
        set.add(upperCaseSql.indexOf("DELETE "));
        set.remove(-1);
        if (CollectionUtils.isEmpty(set)) {
            return -1;
        }
        List<Integer> list = new ArrayList<>(set);
        list.sort(Comparator.naturalOrder());
        return list.get(0);
    }

    /**
     * 匹配sql
     *
     * @param sql
     * @return 匹配后的sql
     * @author zhengqingya
     * @date 2020/12/2 17:14
     */
    private String matchSql(String sql) {
        Matcher matcher = null;
        // SELECT 列名称 FROM 表名称
        // SELECT * FROM 表名称
        if (sql.startsWith("SELECT")) {
            matcher = Pattern.compile("SELECT\\s.+FROM\\s(.+)WHERE\\s(.*)").matcher(sql);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        // INSERT INTO 表名称 VALUES (值1, 值2,....)
        // INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
        if (sql.startsWith("INSERT")) {
            matcher = Pattern.compile("INSERT\\sINTO\\s(.+)\\(.*\\)\\s.*").matcher(sql);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        // UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
        if (sql.startsWith("UPDATE")) {
            matcher = Pattern.compile("UPDATE\\s(.+)SET\\s.*").matcher(sql);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        // DELETE FROM 表名称 WHERE 列名称 = 值
        if (sql.startsWith("DELETE")) {
            matcher = Pattern.compile("DELETE\\sFROM\\s(.+)WHERE\\s(.*)").matcher(sql);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

}
