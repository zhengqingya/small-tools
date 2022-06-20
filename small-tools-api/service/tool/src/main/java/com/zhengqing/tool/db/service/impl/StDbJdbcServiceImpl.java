package com.zhengqing.tool.db.service.impl;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.core.constant.AppConstant;
import com.zhengqing.common.file.util.QiniuFileUtil;
import com.zhengqing.tool.db.enums.StDbDataSourceTypeEnum;
import com.zhengqing.tool.db.enums.StDbOperateSqlEnum;
import com.zhengqing.tool.db.model.bo.StDbTableColumnBO;
import com.zhengqing.tool.db.model.dto.StDbTableColumnSaveDTO;
import com.zhengqing.tool.db.model.vo.StDbDataSourceListVO;
import com.zhengqing.tool.db.model.vo.StDbDatabaseListVO;
import com.zhengqing.tool.db.model.vo.StDbTableColumnListVO;
import com.zhengqing.tool.db.model.vo.StDbTableListVO;
import com.zhengqing.tool.db.service.IStDbDataSourceService;
import com.zhengqing.tool.db.service.IStDbJdbcService;
import com.zhengqing.tool.util.TableToWordUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * <p>
 * JDBC方式服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description TODO 注：后面加上事务回滚
 * @date 2019/7/22 20:48
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class StDbJdbcServiceImpl implements IStDbJdbcService {

    @Resource
    private IStDbDataSourceService stDbDataSourceService;

    @Resource
    private QiniuFileUtil qiniuFileUtil;

    @Override
    public void connectTest(Integer dataSourceId, String dbName) {
        this.getConnection(dataSourceId, dbName);
    }

    @Override
    @SneakyThrows(Exception.class)
    public List<StDbDatabaseListVO> getAllDatabasesByDataSourceId(Integer dataSourceId) {
        // 1、连接数据库
        Connection conn = this.getConnection(dataSourceId);
        List<StDbDatabaseListVO> dbInfoList = Lists.newArrayList();
        // 2、获取sql预编译对象
        Statement stmt = conn.createStatement();
        // 3、执行查询
        ResultSet rs = stmt.executeQuery(StDbOperateSqlEnum.查看显示所有数据库.getSql());
        // 4、展开结果集数据库
        while (rs.next()) {
            dbInfoList.add(StDbDatabaseListVO.builder().name(rs.getString(1)).build());
        }
        // 5、释放资源(注意：关闭资源顺序 -> 先打开后关闭)
        rs.close();
        stmt.close();
        conn.close();
        return dbInfoList;
    }

    @Override
    @SneakyThrows(Exception.class)
    public List<StDbTableListVO> getAllTablesByDataSourceIdAndDbName(Integer dataSourceId, String dbName, String tableName) {
        // 1、连接数据库
        Connection conn = this.getConnection(dataSourceId);
        List<StDbTableListVO> tableInfoList = Lists.newArrayList();
        // 2、获取sql预编译对象
        Statement stmt = conn.createStatement();
        // 3、执行查询
        String sql = String.format(StDbOperateSqlEnum.查看指定库下所有表信息.getSql(), dbName);
        if (StringUtils.isNotBlank(tableName)) {
            sql = sql + " AND table_name LIKE CONCAT( '%" + tableName + "%' )";
        }
        ResultSet rs = stmt.executeQuery(sql);
        // 4、展开结果集数据库
        while (rs.next()) {
            // BeanHandler方式获取的数据不完整
            // BeanHandler<StDbTableVO> bh = new BeanHandler<>(StDbTableVO.class);
            // StDbTableVO tableInfo = bh.handle(rs);
            // 封装表信息
            StDbTableListVO tableInfo = new StDbTableListVO();
            tableInfo.setTableName(rs.getString("tableName"));
            tableInfo.setEngine(rs.getString("engine"));
            tableInfo.setTableRows(rs.getString("tableRows"));
            tableInfo.setDataLength(rs.getString("dataLength"));
            tableInfo.setAutoIncrement(rs.getString("autoIncrement"));
            tableInfo.setCreateTime(rs.getString("createTime"));
            tableInfo.setUpdateTime(rs.getString("updateTime"));
            tableInfo.setTableCollation(rs.getString("tableCollation"));
            tableInfo.setTableComment(rs.getString("tableComment"));
            tableInfoList.add(tableInfo);
        }
        // 5、释放资源(注意：关闭资源顺序 -> 先打开后关闭)
        rs.close();
        stmt.close();
        conn.close();
        return tableInfoList;
    }

    @Override
    @SneakyThrows(Exception.class)
    public StDbTableColumnListVO getAllColumnsByDataSourceIdAndDbNameAndTableName(Integer dataSourceId, String dbName, String tableName) {
        // 获取数据源配置信息
        StDbDataSourceListVO dbConfig = this.stDbDataSourceService.detail(dataSourceId);
        String ipAddress = dbConfig.getIpAddress();
        String port = dbConfig.getPort();
        String username = dbConfig.getUsername();
        String password = dbConfig.getPassword();
        StDbTableColumnListVO columnInfo = this.getAllColumnsByDbInfo(StDbDataSourceTypeEnum.getEnum(dbConfig.getType()), ipAddress, port, username, password, dbName, tableName);
        columnInfo.setDataSourceId(dataSourceId);
        return columnInfo;
    }

    @Override
    @SneakyThrows(Exception.class)
    public StDbTableColumnListVO getAllColumnsByDbInfo(StDbDataSourceTypeEnum dataSourceTypeEnum, String ipAddress, String port, String username, String password, String dbName, String tableName) {
        // 1、连接数据库
        Connection conn = this.getConnectionBase(dataSourceTypeEnum, ipAddress, port, username, password, dbName);

        // 返回结果声明
        StDbTableColumnListVO result = new StDbTableColumnListVO();
        result.setDataSourceId(null);
        result.setDbName(dbName);
        result.setTableName(tableName);
        List<StDbTableColumnListVO.ColumnInfo> columnInfoList = Lists.newArrayList();

        // 2、获取sql预编译对象
        Statement stmt = conn.createStatement();

        // 3、执行查询
        // 3.1、查看指定库下指定表注释
        String selectTableCommentSql = String.format(StDbOperateSqlEnum.查看指定库下指定表注释.getSql(), dbName, tableName);
        ResultSet rsSelectTableComment = stmt.executeQuery(selectTableCommentSql);
        // 3.1.1、展开结果集数据库
        while (rsSelectTableComment.next()) {
            result.setTableComment(rsSelectTableComment.getString("tableComment"));
        }

        // 3.2、查看指定库和表下所有字段信息
        String sql = String.format(StDbOperateSqlEnum.查看指定库和表下所有字段信息.getSql(), dbName, tableName);
        ResultSet rs = stmt.executeQuery(sql);
        // 3.2.1、展开结果集数据库
        while (rs.next()) {
            // BeanHandler方式获取的数据不完整
            // BeanHandler<StDbTableColumnVO.ColumnInfo> bh = new BeanHandler<>(StDbTableColumnVO.ColumnInfo.class);
            // StDbTableColumnVO.ColumnInfo columnInfo = bh.handle(rs);
            // 封装表字段信息
            StDbTableColumnListVO.ColumnInfo columnInfo = new StDbTableColumnListVO.ColumnInfo();
            columnInfo.setColumnName(rs.getString("columnName"));
            columnInfo.setColumnComment(rs.getString("columnComment"));
            String columnType = rs.getString("columnType");
            if (!columnType.contains("(")) {
                columnType = columnType + "(" + 0 + ")";
            }
            columnInfo.setColumnType(columnType);
            columnInfo.setIfNullAble(Boolean.parseBoolean(rs.getString("ifNullAble")));
            columnInfo.setIfPrimaryKey(Boolean.parseBoolean(rs.getString("ifPrimaryKey")));
            columnInfo.setIfAutoIncrement(Boolean.parseBoolean(rs.getString("ifAutoIncrement")));
            columnInfo.setColumnDefaultValue(rs.getString("columnDefaultValue"));
            columnInfoList.add(columnInfo);
        }

        // 4、释放资源(注意：关闭资源顺序 -> 先打开后关闭)
        rsSelectTableComment.close();
        rs.close();
        stmt.close();
        conn.close();
        result.setColumnInfoList(columnInfoList);
        return result;
    }

    @Override
    public void updateColumnInfo(StDbTableColumnSaveDTO params) {
        try {
            // 1、连接数据库
            Connection conn = this.getConnection(params.getDataSourceId());
            // 2、获取sql预编译对象
            Statement stmt = conn.createStatement();

            // 3、获取基本信息
            // 表名
            String dbName = params.getDbName();
            String tableName = params.getTableName();
            String tableComment = params.getTableComment();

            // 4、更新数据
            // 4.1、更新表注释
            String updateTableCommentSql =
                    String.format(StDbOperateSqlEnum.修改指定库下指定表注释.getSql(), dbName, tableName, tableComment);
            log.debug("更新表注释sql语句: 【{}】", updateTableCommentSql);
            stmt.executeUpdate(updateTableCommentSql);

            // 4.2、更新表字段信息
            for (StDbTableColumnSaveDTO.ColumnInfo item : params.getColumnInfoList()) {
                // TODO 暂不支持修改主键与自增长功能，如果需要支持的话，①先删除表字段自增属性(即修改字段语法中去掉`auto_increment`属性)
                // ②删除表原主键，再设置新主键id(StDbOperateSqlEnum.修改指定库下指定表主键ID) ③最后设置新的自增字段(StDbOperateSqlEnum.修改指定库下指定表指定字段自增长)
                // 是否是主键
                String isPrimaryKey = item.isIfPrimaryKey() ? "PRIMARY KEY" : "";
                // 是否自增长
                String isAutoIncrement = item.isIfAutoIncrement() ? "AUTO_INCREMENT" : "";
                // 是否允许空值
                String isNullable = item.isIfNullAble() ? "DEFAULT NULL" : "NOT NULL";
                // 默认值
                String columnDefaultValue = item.getColumnDefaultValue();
                if (StringUtils.isNotBlank(columnDefaultValue)) {
                    columnDefaultValue = "DEFAULT '" + columnDefaultValue + "'";
                } else {
                    columnDefaultValue = "";
                }
                String updateTableColumnInfoSql =
                        String.format(StDbOperateSqlEnum.修改指定库下指定表字段信息.getSql(), dbName, tableName, item.getColumnName(),
                                item.getColumnType(), isNullable, columnDefaultValue, item.getColumnComment());

                // 更新表字段信息
                log.debug("更新表字段信息sql语句: 【{}】", updateTableColumnInfoSql);
                stmt.executeUpdate(updateTableColumnInfoSql);
            }

            // 5、释放资源(注意：关闭资源顺序 -> 先打开后关闭)
            stmt.close();
            conn.close();
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public String tableInfoToWordByDataSourceIdAndDbName(Integer dataSourceId, String dbName) {
        // 1、获取数据库所有表信息
        List<StDbTableColumnBO> tableInfoList = Lists.newArrayList();
        this.getAllTableAndColumnInfoList(tableInfoList, dataSourceId, dbName);

        // 2、调用工具类生成文件
        String dbWordFilePath = AppConstant.FILE_PATH_DB_WORD;
        String[] dbFilePathArray = dbWordFilePath.split(AppConstant.SEPARATOR_SPRIT);
        String fileName = dbFilePathArray[dbFilePathArray.length - 1];
        String title = fileName.split("\\.")[0];
        TableToWordUtil.toWord(tableInfoList, dbWordFilePath, title);

        // 3、返回文件地址
        return this.qiniuFileUtil.uploadFile(FileUtil.newFile(dbWordFilePath), System.currentTimeMillis() + "_" + fileName);
    }

    /**
     * 根据数据源id+库名获取所有表+字段信息
     *
     * @param tableInfoList: 需装数据的表+字段信息
     * @param dataSourceId   数据源id
     * @param dbName:库名
     * @return void
     * @author zhengqingya
     * @date 2020/9/8 19:06
     */
    @SneakyThrows(Exception.class)
    public void getAllTableAndColumnInfoList(List<StDbTableColumnBO> tableInfoList, Integer dataSourceId,
                                             String dbName) {
        // 1、连接数据库
        Connection conn = this.getConnection(dataSourceId);
        // 2、获取sql预编译对象
        Statement stmt = conn.createStatement();
        // 3.1、查询表名+注释信息
        String sql = String.format(StDbOperateSqlEnum.查看指定库下所有表信息.getSql(), dbName);
        ResultSet rs = stmt.executeQuery(sql);
        // 3.1.1、展开结果集数据库
        while (rs.next()) {
            // 封装表名+注释信息
            StDbTableColumnBO tableInfo = new StDbTableColumnBO();
            String tableName = rs.getString("tableName");
            tableInfo.setTableName(tableName);
            tableInfo.setTableComment(rs.getString("tableComment"));
            tableInfoList.add(tableInfo);
        }
        // 3.2、查询表下字段信息
        for (StDbTableColumnBO e : tableInfoList) {
            List<StDbTableColumnBO.ColumnInfo> columnInfoList = Lists.newArrayList();
            ResultSet rsTable =
                    stmt.executeQuery(String.format(StDbOperateSqlEnum.查看指定库和表下所有字段信息.getSql(), dbName, e.getTableName()));
            // 3.2.1、展开结果集数据库
            while (rsTable.next()) {
                // 封装表字段信息
                StDbTableColumnBO.ColumnInfo columnInfo = new StDbTableColumnBO.ColumnInfo();
                columnInfo.setColumnName(rsTable.getString("columnName"));
                columnInfo.setColumnComment(rsTable.getString("columnComment"));
                String columnType = rsTable.getString("columnType");
                if (!columnType.contains("(")) {
                    columnType = columnType + "(" + 0 + ")";
                }
                columnInfo.setColumnType(columnType);
                columnInfo.setIfNullAble(Boolean.parseBoolean(rsTable.getString("ifNullAble")));
                columnInfo.setIfPrimaryKey(Boolean.parseBoolean(rsTable.getString("ifPrimaryKey")));
                columnInfo.setIfAutoIncrement(Boolean.parseBoolean(rsTable.getString("ifAutoIncrement")));
                columnInfo.setColumnDefaultValue(rsTable.getString("columnDefaultValue"));
                columnInfoList.add(columnInfo);
                e.setColumnInfoList(columnInfoList);
            }
            // 3.2.2、释放资源
            rsTable.close();
        }

        // 4、释放资源(注意：关闭资源顺序 -> 先打开后关闭)
        rs.close();
        stmt.close();
        conn.close();
    }

    /**
     * 连接数据库
     *
     * @param dataSourceId: 数据源id
     * @return java.sql.Connection
     */
    private Connection getConnection(Integer dataSourceId) {
        return this.getConnection(dataSourceId, null);
    }

    /**
     * 连接数据库
     *
     * @param dataSourceId 数据源id
     * @param dbName       数据库名称
     * @return java.sql.Connection
     */
    private Connection getConnection(Integer dataSourceId, String dbName) {
        // 获取数据源配置信息
        StDbDataSourceListVO dbConfig = this.stDbDataSourceService.detail(dataSourceId);
        // 连接所需参数【ipAddress：指向要访问的数据库ip地址, username：用户名, password：密码】
        String ipAddress = dbConfig.getIpAddress();
        String port = dbConfig.getPort();
        String username = dbConfig.getUsername();
        String password = dbConfig.getPassword();
        return this.getConnectionBase(StDbDataSourceTypeEnum.getEnum(dbConfig.getType()), ipAddress, port, username, password, dbName);
    }

    /**
     * 连接数据库
     *
     * @param dataSourceTypeEnum 数据源类型枚举
     * @param ipAddress          指向要访问的数据库ip地址
     * @param port               端口
     * @param username           用户名
     * @param password           密码
     * @param dbName             库名
     * @return java.sql.Connection
     * @author zhengqingya
     * @date 2021/8/21 5:11 下午
     */
    private Connection getConnectionBase(StDbDataSourceTypeEnum dataSourceTypeEnum, String ipAddress, String port, String username, String password, String dbName) {
        try {
            if (StringUtils.isBlank(dbName)) {
                dbName = "mysql";
            }
            // 1、创建用于连接数据库的Connection对象
            Connection con = null;
            // 2、加载JDBC驱动
            Class.forName(dataSourceTypeEnum.getDriverClassName());
            // 3、根据数据库类型【mysql or oracle】，获取连接，与数据库建立连接
            switch (dataSourceTypeEnum) {
                case MySQL:
                    con = DriverManager.getConnection(
                            String.format(StDbOperateSqlEnum.连接MySQL数据库.getSql(), ipAddress, port, dbName), username, password);
                    break;
                case Oracle:
                    con = DriverManager.getConnection(
                            String.format(StDbOperateSqlEnum.连接Oracle数据库.getSql(), ipAddress, port), username, password);
                    break;
                default:
                    break;
            }
            // 4、返回所建立的数据库连接信息
            return con;
        } catch (Exception e) {
            throw new MyException("数据库连接失败：" + e.getMessage());
        }
    }

}
