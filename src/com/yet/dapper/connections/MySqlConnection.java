package com.yet.dapper.connections;

import com.yet.dapper.DapperPage;
import com.yet.dapper.MyConnection;
import com.yet.dapper.common.DapperCommon;
import com.yet.dapper.common.DapperSqls;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySqlConnection extends MyConnection {

    private static HashMap<String, DapperSqls> map = new HashMap<>();
    private static Object _locker = new Object();

    private static DapperSqls GetSqls(Class<?> t) {
        if (map.containsKey(t.getName())) {
            return map.get(t.getName());
        } else {
            synchronized (_locker) { //锁防止多线程并发
                if (!map.containsKey(t.getName())) {
                    DapperSqls sqls = DapperCommon.CreateSqls(t);
                    String Fields = DapperCommon.GetFieldsStr(sqls.AllFieldList, "`", "`");
                    String FieldsAt = DapperCommon.GetFieldsAtStr(sqls.AllFieldList);
                    String FieldsEq = DapperCommon.GetFieldsEqStr(sqls.AllFieldList, "`", "`");

                    String FieldsExtKey = DapperCommon.GetFieldsStr(sqls.ExceptKeyFieldList, "`", "`");
                    String FieldsAtExtKey = DapperCommon.GetFieldsAtStr(sqls.ExceptKeyFieldList);
                    String FieldsEqExtKey = DapperCommon.GetFieldsEqStr(sqls.ExceptKeyFieldList, "`", "`");

                    if (sqls.KeyName != "" && sqls.IsIdentity) //有主键并且是自增
                    {
                        sqls.InsertSql = MessageFormat.format("INSERT INTO `{0}`({1})VALUES({2})", sqls.TableName, FieldsExtKey, FieldsAtExtKey);
                        sqls.InsertIdentitySql = MessageFormat.format("INSERT INTO `{0}`({1})VALUES({2})", sqls.TableName, Fields, FieldsAt);
                    } else {
                        sqls.InsertSql = MessageFormat.format("INSERT INTO `{0}`({1})VALUES({2})", sqls.TableName, Fields, FieldsAt);
                    }

                    if (sqls.KeyName != "") //含有主键
                    {
                        sqls.DeleteByIdSql = MessageFormat.format("DELETE FROM `{0}` WHERE `{1}`=?", sqls.TableName, sqls.KeyName);
                        sqls.DeleteByIdsSql = MessageFormat.format("DELETE FROM `{0}` WHERE `{1}` IN", sqls.TableName, sqls.KeyName);
                        sqls.GetByIdSql = MessageFormat.format("SELECT {0} FROM `{1}` WHERE `{2}`=?", Fields, sqls.TableName, sqls.KeyName);
                        sqls.GetByIdsSql = MessageFormat.format("SELECT {0} FROM `{1}` WHERE `{2}` IN", Fields, sqls.TableName, sqls.KeyName);
                    }
                    sqls.DeleteAllSql = MessageFormat.format("DELETE FROM `{0}`", sqls.TableName);
                    sqls.GetAllSql = MessageFormat.format("SELECT {0} FROM `{1}`", Fields, sqls.TableName);
                    sqls.AllFields = Fields;

                    map.put(t.getName(), sqls);
                }
            }
        }
        return map.get(t.getName());
    }

    public MySqlConnection(Connection conn) {
        super.conn = conn;
        super.runner = new QueryRunner();
    }

    @Override
    public BigInteger GetIdentity() throws SQLException {
        return ExecuteScalar("SELECT @@IDENTITY");
    }

    @Override
    public <T> int Insert(T model) throws Exception {
        DapperSqls sqls = GetSqls(model.getClass());
        Object[] par;
        if (sqls.IsIdentity) { //是自增
            par = new Object[sqls.ExceptKeyFieldList.size()];
            for (int i = 0; i < sqls.ExceptKeyFieldList.size(); i++) {
                String name = sqls.ExceptKeyFieldList.get(i);
                par[i] = BeanUtils.getProperty(model, name);
            }
        } else {
            par = new Object[sqls.AllFieldList.size()];
            for (int i = 0; i < sqls.AllFieldList.size(); i++) {
                String name = sqls.AllFieldList.get(i);
                par[i] = BeanUtils.getProperty(model, name);
            }
        }
        return Execute(sqls.InsertSql, par);
    }

    @Override
    public <T> int InsertIdentity(T model) throws Exception {
        DapperSqls sqls = GetSqls(model.getClass());
        if (sqls.IsIdentity) {
            Object[] par = new Object[sqls.AllFieldList.size()];
            for (int i = 0; i < sqls.AllFieldList.size(); i++) {
                String name = sqls.AllFieldList.get(i);
                par[i] = BeanUtils.getProperty(model, name);
            }
            return Execute(sqls.InsertIdentitySql, par);
        }
        return 0;
    }

    @Override
    public int DeleteById(Class<?> type, Object id) throws SQLException {
        return Execute(GetSqls(type).DeleteByIdSql, id);
    }

    @Override
    public int DeleteByIds(Class<?> type, Object... ids) throws SQLException {
        if (ids.length != 0) {
            DapperSqls sqls = GetSqls(type);
            String sql = sqls.DeleteByIdsSql + "(" + DapperCommon.GetFieldsAtStr(ids) + ")";
            return Execute(sql, ids);
        }
        return 0;
    }

    @Override
    public int DeleteByWhere(Class<?> type, String where, Object... params) throws SQLException {
        DapperSqls sqls = GetSqls(type);
        String sql = "DELETE FROM `" + sqls.TableName + "` " + where;
        return Execute(sql, params);
    }

    @Override
    public int DeleteAll(Class<?> type) throws SQLException {
        return Execute(GetSqls(type).DeleteAllSql);
    }

    @Override
    public <T> int Update(T model) throws Exception {
        DapperSqls sqls = GetSqls(model.getClass());
        List<Object> params = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE `").append(sqls.TableName).append("` SET ");
        for (int i = 0; i < sqls.ExceptKeyFieldList.size(); i++) {
            String name = sqls.ExceptKeyFieldList.get(i);
            params.add(BeanUtils.getProperty(model, name));
            sb.append("`").append(name).append("`=?");
            if (i < sqls.ExceptKeyFieldList.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(" WHERE `").append(sqls.KeyName).append("`=?");
        params.add(BeanUtils.getProperty(model, sqls.KeyName));
        return Execute(sb.toString(), params.toArray());
    }

    @Override
    public <T> int Update(T model, String updateFields) throws Exception {
        DapperSqls sqls = GetSqls(model.getClass());
        List<Object> params = new ArrayList<>();
        String[] arr = updateFields.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE `").append(sqls.TableName).append("` SET ");
        for (int i = 0; i < arr.length; i++) {
            String name = arr[i];
            params.add(BeanUtils.getProperty(model, name));
            sb.append("`").append(name).append("`=?");
            if (i < arr.length - 1) {
                sb.append(",");
            }
        }
        sb.append(" WHERE `").append(sqls.KeyName).append("`=?");
        params.add(BeanUtils.getProperty(model, sqls.KeyName));
        return Execute(sb.toString(), params.toArray());
    }

    @Override
    public int UpdateByWhere(Class<?> type, String updateFields, String where, Object... params) throws SQLException {
        DapperSqls sqls = GetSqls(type);
        String[] arr = updateFields.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE `").append(sqls.TableName).append("` SET ");
        for (int i = 0; i < arr.length; i++) {
            String name = arr[i];
            sb.append("`").append(name).append("`=?");
            if (i < arr.length - 1) {
                sb.append(",");
            } else {
                sb.append(" ").append(where);
            }
        }
        return Execute(sb.toString(), params);
    }

    @Override
    public <T> List<T> GetAll(Class<T> type) throws SQLException {
        return Query(GetSqls(type).GetAllSql, type);
    }

    @Override
    public <T> List<T> GetAll(Class<T> type, String returnFields) throws SQLException {
        DapperSqls sqls = GetSqls(type);
        String sql = MessageFormat.format("SELECT {0} FROM `{1}`", returnFields, sqls.TableName);
        return Query(sql, type);
    }

    @Override
    public <T> T GetById(Class<T> type, Object id) throws SQLException {
        return QueryFirstOrDefault(GetSqls(type).GetByIdSql, type, id);
    }

    @Override
    public <T> T GetById(Class<T> type, Object id, String returnFields) throws SQLException {
        DapperSqls sqls = GetSqls(type);
        String sql = MessageFormat.format("SELECT {0} FROM `{1}` WHERE `{2}`=?", returnFields, sqls.TableName, sqls.KeyName);
        return QueryFirstOrDefault(sql, type, id);
    }

    @Override
    public <T> List<T> GetByIds(Class<T> type, String returnFields, Object... ids) throws SQLException {
        if (ids.length != 0) {
            DapperSqls sqls = GetSqls(type);
            if (returnFields.equals("*") || returnFields.equals("")) {
                String sql = sqls.GetByIdsSql + "(" + DapperCommon.GetFieldsAtStr(ids) + ")";
                return Query(sql, type, ids);
            }
            String sql = MessageFormat.format("SELECT {0} FROM `{1}` WHERE `{2}` IN({3})", returnFields, sqls.TableName, sqls.KeyName, DapperCommon.GetFieldsAtStr(ids));
            return Query(sql, type, ids);
        }
        return new ArrayList<>();
    }

    @Override
    public <T> List<T> GetByWhere(Class<T> type, String returnFields, String where, Object... params) throws SQLException {
        DapperSqls sqls = GetSqls(type);
        String sql;
        if (where == null)
            where = "";
        if (returnFields.equals("*") || returnFields.equals(""))
            sql = MessageFormat.format("SELECT {0} FROM `{1}` {2}", sqls.AllFields, sqls.TableName, where);
        else
            sql = MessageFormat.format("SELECT {0} FROM `{1}` {2}", returnFields, sqls.TableName, where);
        return Query(sql, type, params);
    }

    @Override
    public <T> T GetByWhereFirst(Class<T> type, String returnFields, String where, Object... params) throws SQLException {
        DapperSqls sqls = GetSqls(type);
        String sql;
        if (where == null)
            where = "";
        if (returnFields.equals("*") || returnFields.equals(""))
            sql = MessageFormat.format("SELECT {0} FROM `{1}` {2}", sqls.AllFields, sqls.TableName, where);
        else
            sql = MessageFormat.format("SELECT {0} FROM `{1}` {2}", returnFields, sqls.TableName, where);
        return QueryFirstOrDefault(sql, type, params);
    }

    @Override
    public <T> int GetTotal(Class<T> type, String where, Object... params) throws SQLException {
        if (where == null)
            where = "";
        String sql = MessageFormat.format("SELECT COUNT(1) FROM `{0}` {1}", GetSqls(type).TableName, where);
        long total = ExecuteScalar(sql, params);
        return (int) total;
    }

    @Override
    public <T> List<T> GetByIdsWhichField(Class<T> type, String whichField, String returnFields, Object... ids) throws SQLException {
        if (ids.length != 0) {
            String sql = MessageFormat.format("SELECT {0} FROM `{1}` WHERE {2} IN({3})", returnFields, GetSqls(type).TableName, whichField, DapperCommon.GetFieldsAtStr(ids));
            return Query(sql, type, ids);
        }
        return new ArrayList<>();
    }

    @Override
    public <T> List<T> GetBySkipTake(Class<T> type, int skip, int take, String returnFields, String orderBy, String where, Object... params) throws SQLException {
        DapperSqls sqls = GetSqls(type);
        if (where == null)
            where = "";
        if (orderBy == null || orderBy.equals("")) {
            if (!sqls.KeyName.equals("")) {
                orderBy = MessageFormat.format("ORDER BY `{0}`", sqls.KeyName);
            } else {
                orderBy = MessageFormat.format("ORDER BY `{0}`", sqls.AllFieldList.get(0));
            }
        }
        String sql = MessageFormat.format("SELECT {0} FROM `{1}` {2} {3} LIMIT {4},{5}", returnFields, sqls.TableName, where, orderBy, skip, take);
        return Query(sql, type, params);
    }

    @Override
    public <T> List<T> GetByPageIndex(Class<T> type, int pageIndex, int pageSize, String returnFields, String orderBy, String where, Object... params) throws SQLException {
        int skip = 0;
        if (pageIndex > 0) {
            skip = (pageIndex - 1) * pageSize;
        }
        return GetBySkipTake(type, skip, pageSize, returnFields, orderBy, where, params);
    }

    @Override
    public <T> DapperPage<T> GetByPage(Class<T> type, int pageIndex, int pageSize, String returnFields, String orderBy, String where, Object... params) throws SQLException {

        DapperPage<T> page = new DapperPage<>();
        page.total = GetTotal(type, where, params);
        page.data = GetByPageIndex(type, pageIndex, pageSize, returnFields, orderBy, where, params);
        return page;
    }


}
