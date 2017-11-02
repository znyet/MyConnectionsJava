package com.yet.dapper.connections;

import com.yet.dapper.DapperPage;
import com.yet.dapper.MyConnection;
import org.apache.commons.dbutils.QueryRunner;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017-10-25.
 */
public class SqlserverConnection extends MyConnection {


    public SqlserverConnection(Connection conn) {
        super.conn = conn;
        super.runner = new QueryRunner();
    }

    @Override
    public BigInteger GetIdentity() throws SQLException {
        return ExecuteScalar("SELECT @@IDENTITY");
    }

    @Override
    public <T> int Insert(T model) throws Exception {
        return 0;
    }

    @Override
    public <T> int InsertIdentity(T model) throws Exception {
        return 0;
    }

    @Override
    public int DeleteById(Class<?> type, Object id) throws SQLException {
        return 0;
    }

    @Override
    public int DeleteByIds(Class<?> type, Object... params) throws SQLException {
        return 0;
    }

    @Override
    public int DeleteAll(Class<?> type) throws SQLException {
        return 0;
    }

    @Override
    public int DeleteByWhere(Class<?> type, String where, Object... params) throws SQLException {
        return 0;
    }

    @Override
    public <T> int Update(T model) throws Exception {
        return 0;
    }

    @Override
    public <T> int Update(T model, String updateFields) throws Exception {
        return 0;
    }

    @Override
    public int UpdateByWhere(Class<?> type, String updateFields, String where, Object... params) throws SQLException {
        return 0;
    }

    @Override
    public <T> List<T> GetAll(Class<T> type) throws SQLException {
        return null;
    }

    @Override
    public <T> List<T> GetAll(Class<T> type, String returnFields) throws SQLException {
        return null;
    }

    @Override
    public <T> T GetById(Class<T> type, Object id) throws SQLException {
        return null;
    }

    @Override
    public <T> T GetById(Class<T> type, Object id, String returnFields) throws SQLException {
        return null;
    }

    @Override
    public <T> List<T> GetByIds(Class<T> type, String returnFields, Object... ids) throws SQLException {
        return null;
    }

    @Override
    public <T> List<T> GetByWhere(Class<T> type, String returnFields, String where, Object... params) throws SQLException {
        return null;
    }

    @Override
    public <T> T GetByWhereFirst(Class<T> type, String returnFields, String where, Object... params) throws SQLException {
        return null;
    }

    @Override
    public <T> int GetTotal(Class<T> type, String where, Object... params) throws SQLException {
        return 0;
    }

    @Override
    public <T> List<T> GetByIdsWhichField(Class<T> type, String whichField, String returnFields, Object... param) throws SQLException {
        return null;
    }

    @Override
    public <T> List<T> GetBySkipTake(Class<T> type, int skip, int take, String returnFields, String orderBy, String where, Object... params) throws SQLException {
        return null;
    }

    @Override
    public <T> List<T> GetByPageIndex(Class<T> type, int pageIndex, int pageSize, String returnFields, String orderBy, String where, Object... params) throws SQLException {
        return null;
    }

    @Override
    public <T> DapperPage<T> GetByPage(Class<T> type, int pageIndex, int pageSize, String returnFields, String orderBy, String where, Object... params) throws SQLException {
        return null;
    }
}
