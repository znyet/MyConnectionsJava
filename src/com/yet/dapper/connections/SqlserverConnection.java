package com.yet.dapper.connections;

import com.yet.dapper.DapperPage;
import com.yet.dapper.MyConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017-10-25.
 */
public class SqlserverConnection extends MyConnection {


    public SqlserverConnection(Connection _conn, boolean _autoCommit) {
        super(_conn, _autoCommit);
    }

    @Override
    public <T> T getIdentity() throws SQLException {
        return executeScalar("SELECT @@IDENTITY");
    }

    @Override
    public <T> int insert(T model) throws Exception {
        return 0;
    }

    @Override
    public <T> int insertIdentity(T model) throws Exception {
        return 0;
    }

    @Override
    public int deleteById(Class<?> type, Object id) throws SQLException {
        return 0;
    }

    @Override
    public int deleteByIds(Class<?> type, Object... params) throws SQLException {
        return 0;
    }

    @Override
    public int deleteAll(Class<?> type) throws SQLException {
        return 0;
    }

    @Override
    public int deleteByWhere(Class<?> type, String where, Object... params) throws SQLException {
        return 0;
    }

    @Override
    public <T> int update(T model) throws Exception {
        return 0;
    }

    @Override
    public <T> int update(T model, String updateFields) throws Exception {
        return 0;
    }

    @Override
    public int updateByWhere(Class<?> type, String updateFields, String where, Object... params) throws SQLException {
        return 0;
    }

    @Override
    public <T> List<T> getAll(Class<T> type) throws SQLException {
        return null;
    }

    @Override
    public <T> List<T> getAll(Class<T> type, String returnFields) throws SQLException {
        return null;
    }

    @Override
    public <T> T getById(Class<T> type, Object id) throws SQLException {
        return null;
    }

    @Override
    public <T> T getById(Class<T> type, Object id, String returnFields) throws SQLException {
        return null;
    }

    @Override
    public <T> List<T> getByIds(Class<T> type, String returnFields, Object... ids) throws SQLException {
        return null;
    }

    @Override
    public <T> List<T> getByWhere(Class<T> type, String returnFields, String where, Object... params) throws SQLException {
        return null;
    }

    @Override
    public <T> T getByWhereFirst(Class<T> type, String returnFields, String where, Object... params) throws SQLException {
        return null;
    }

    @Override
    public <T> int getTotal(Class<T> type, String where, Object... params) throws SQLException {
        return 0;
    }

    @Override
    public <T> List<T> getByIdsWhichField(Class<T> type, String whichField, String returnFields, Object... param) throws SQLException {
        return null;
    }

    @Override
    public <T> List<T> getBySkipTake(Class<T> type, int skip, int take, String returnFields, String orderBy, String where, Object... params) throws SQLException {
        return null;
    }

    @Override
    public <T> List<T> getByPageIndex(Class<T> type, int pageIndex, int pageSize, String returnFields, String orderBy, String where, Object... params) throws SQLException {
        return null;
    }

    @Override
    public <T> DapperPage<T> getByPage(Class<T> type, int pageIndex, int pageSize, String returnFields, String orderBy, String where, Object... params) throws SQLException {
        return null;
    }
}
