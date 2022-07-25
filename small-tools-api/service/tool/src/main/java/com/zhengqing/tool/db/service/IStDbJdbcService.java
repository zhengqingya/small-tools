package com.zhengqing.tool.db.service;

import com.zhengqing.tool.db.enums.StDbDataSourceTypeEnum;
import com.zhengqing.tool.db.model.dto.StDbTableColumnSaveDTO;
import com.zhengqing.tool.db.model.vo.StDbDatabaseListVO;
import com.zhengqing.tool.db.model.vo.StDbTableColumnListVO;
import com.zhengqing.tool.db.model.vo.StDbTableListVO;

import java.util.List;

/**
 * <p>
 * JDBC方式服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/9/13 0013 19:46
 */
public interface IStDbJdbcService {

    /**
     * 连接测试
     *
     * @param dataSourceId 数据源id
     * @param dbName       数据库名称
     * @return void
     * @author zhengqingya
     * @date 2020/9/4 15:01
     */
    void connectTest(Integer dataSourceId, String dbName);

    /**
     * 根据数据源id查询所有数据库信息
     *
     * @param dataSourceId 数据源id
     * @return 所有数据库名列表
     * @author zhengqingya
     * @date 2020/9/4 14:07
     */
    List<StDbDatabaseListVO> getAllDatabasesByDataSourceId(Integer dataSourceId);

    /**
     * 根据数据源id和库名查询所有表信息
     *
     * @param dataSourceId 数据源id
     * @param dbName       数据库名
     * @param tableName    表名
     * @return 所有表名列表
     * @author zhengqingya
     * @date 2020/9/6 3:21
     */
    List<StDbTableListVO> getAllTablesByDataSourceIdAndDbName(Integer dataSourceId, String dbName, String tableName);

    /**
     * 根据数据源id+库名+表名查询具体表字段信息
     *
     * @param dataSourceId 数据源id
     * @param dbName       数据库名
     * @param tableName    表名
     * @return 表字段列表
     * @author zhengqingya
     * @date 2020/9/6 13:21
     */
    StDbTableColumnListVO getAllColumnsByDataSourceIdAndDbNameAndTableName(Integer dataSourceId, String dbName, String tableName);

    /**
     * 根据数据库连接信息+库名+表名查询具体表字段信息
     *
     * @param dataSourceTypeEnum 数据源类型枚举
     * @param ipAddress          指向要访问的数据库ip地址
     * @param port               端口
     * @param username           用户名
     * @param password           密码
     * @param dbName             库名
     * @param tableName          表名
     * @return 表字段列表
     * @author zhengqingya
     * @date 2021/8/21 5:15 下午
     */
    StDbTableColumnListVO getAllColumnsByDbInfo(StDbDataSourceTypeEnum dataSourceTypeEnum, String ipAddress, String port, String username, String password, String dbName, String tableName);

    /**
     * 根据数据源id+库名+表名更新具体表字段信息
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2020/9/6 20:01
     */
    void updateColumnInfo(StDbTableColumnSaveDTO params);

    /**
     * 根据数据源id+数据库名导出数据库表信息生成Word文档
     *
     * @param dataSourceId 数据源id
     * @param dbName       数据库名
     * @return word下载地址
     * @author zhengqingya
     * @date 2020/9/8 16:40
     */
    String tableInfoToWordByDataSourceIdAndDbName(Integer dataSourceId, String dbName);

    /**
     * 执行sql
     *
     * @param sql sql
     * @return void
     * @author zhengqingya
     * @date 2022/7/22 16:46
     */
    void execSql(String sql);

}
