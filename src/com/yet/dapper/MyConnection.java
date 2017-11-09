package com.yet.dapper;

import com.yet.dapper.connections.MySqlConnection;
import com.yet.dapper.connections.SqlserverConnection;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class MyConnection implements AutoCloseable {

    protected Connection conn;
    protected QueryRunner runner = new QueryRunner();

    public static MyConnection createMySql(Connection _conn, boolean _autoCommit) {
        return new MySqlConnection(_conn, _autoCommit);
    }

    public static MyConnection createSqlserver(Connection _conn, boolean _autoCommit) {
        return new SqlserverConnection(_conn, _autoCommit);
    }

    //查询单行单列数据
    public <T> T executeScalar(String sql) throws SQLException {
        return runner.query(conn, sql, new ScalarHandler<T>());
    }

    public <T> T executeScalar(String sql, Object... params) throws SQLException {
        return runner.query(conn, sql, new ScalarHandler<T>(), params);
    }

    //查询一行数据，转化成实体
    public <T> T queryFirstOrDefault(String sql, Class<T> type) throws SQLException {
        BeanHandler<T> rsh = new BeanHandler<T>(type);
        return runner.query(conn, sql, rsh);
    }

    public <T> T queryFirstOrDefault(String sql, Class<T> type, Object... params) throws SQLException {
        BeanHandler<T> rsh = new BeanHandler<T>(type);
        return runner.query(conn, sql, rsh, params);
    }

    //查询实体列表
    public <T> List<T> query(String sql, Class<T> type) throws SQLException {
        BeanListHandler<T> rsh = new BeanListHandler<T>(type);
        return runner.query(conn, sql, rsh);
    }

    public <T> List<T> query(String sql, Class<T> type, Object... params) throws SQLException {
        BeanListHandler<T> rsh = new BeanListHandler<T>(type);
        return runner.query(conn, sql, rsh, params);
    }

    //执行语句，返回受影响行数
    public int execute(String sql) throws SQLException {
        return runner.update(conn, sql);
    }

    public int execute(String sql, Object... params) throws SQLException {
        return runner.update(conn, sql, params);
    }

    //返回一个List的Map列表
    public List<Map<String, Object>> queryMapList(String sql) throws SQLException {
        return runner.query(conn, sql, new MapListHandler());
    }

    //返回一个List的Map列表，参数化
    public List<Map<String, Object>> queryMapList(String sql, Object... parmas) throws SQLException {
        return runner.query(conn, sql, new MapListHandler(), parmas);
    }

    //查询返回Map数据
    public Map<String, Object> queryMap(String sql) throws SQLException {
        return runner.query(conn, sql, new MapHandler());
    }

    //查询返回Map数据，参数化
    public Map<String, Object> queryMap(String sql, Object... params) throws SQLException {
        return runner.query(conn, sql, new MapHandler(), params);
    }

    //提交事物
    public void commitTran() throws SQLException {
        conn.commit();
        conn.setAutoCommit(true);
    }

    //回滚事物
    public void rollbackTran() throws SQLException {
        conn.rollback();
        conn.setAutoCommit(true);
    }

    public void close() throws SQLException {
        boolean autoCommit = conn.getAutoCommit();
        if (!autoCommit) {
            conn.rollback();
            conn.setAutoCommit(true);
        }
        conn.close();
    }

    //===============================
    //以下为抽象方法
    //===============================

    //获取自增
    public abstract <T> T getIdentity() throws SQLException;

    //添加(使用反射)
    public abstract <T> int insert(T model) throws Exception;

    //添加有自增的数据(使用反射)
    public abstract <T> int insertIdentity(T model) throws Exception;

    //删除
    public abstract int deleteById(Class<?> type, Object id) throws SQLException;

    //根据ids删除
    public abstract int deleteByIds(Class<?> type, Object... params) throws SQLException;

    //删除整张表数据
    public abstract int deleteAll(Class<?> type) throws SQLException;

    //根据条件删除
    public abstract int deleteByWhere(Class<?> type, String where, Object... params) throws SQLException;

    //修改数据
    public abstract <T> int update(T model) throws Exception;

    //修改数据(可选择要修改的列)
    public abstract <T> int update(T model, String updateFields) throws Exception;

    //根据where条件修改数据
    public abstract int updateByWhere(Class<?> type, String updateFields, String where, Object... params) throws SQLException;

    //获取所有数据
    public abstract <T> List<T> getAll(Class<T> type) throws SQLException;

    //获取所有数据（要返回哪些列）
    public abstract <T> List<T> getAll(Class<T> type, String returnFields) throws SQLException;

    //根据Id获取一条数据
    public abstract <T> T getById(Class<T> type, Object id) throws SQLException;

    //根据Id获取一条数据(要返回的列)
    public abstract <T> T getById(Class<T> type, Object id, String returnFields) throws SQLException;

    //根据Ids获取数据
    public abstract <T> List<T> getByIds(Class<T> type, String returnFields, Object... ids) throws SQLException;

    //根据查询条件获取数据
    public abstract <T> List<T> getByWhere(Class<T> type, String returnFields, String where, Object... params) throws SQLException;

    //根据查询条件获取一条记录
    public abstract <T> T getByWhereFirst(Class<T> type, String returnFields, String where, Object... params) throws SQLException;

    //根据查询条件获取总数
    public abstract <T> int getTotal(Class<T> type, String where, Object... params) throws SQLException;

    //根据某个字段的ids获取数据
    public abstract <T> List<T> getByIdsWhichField(Class<T> type, String whichField, String returnFields, Object... param) throws SQLException;

    //根据skip几天数据take获取几条数据
    public abstract <T> List<T> getBySkipTake(Class<T> type, int skip, int take, String returnFields, String orderBy, String where, Object... params) throws SQLException;

    //根据页码获取数据
    public abstract <T> List<T> getByPageIndex(Class<T> type, int pageIndex, int pageSize, String returnFields, String orderBy, String where, Object... params) throws SQLException;

    //分页方法
    public abstract <T> DapperPage<T> getByPage(Class<T> type, int pageIndex, int pageSize, String returnFields, String orderBy, String where, Object... params) throws SQLException;
}
