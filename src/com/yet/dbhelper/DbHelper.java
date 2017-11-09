package com.yet.dbhelper;

import com.yet.dapper.MyConnection;

public class DbHelper {

    public static MyConnection open() {
        return MyConnection.createMySql(MySqlPool.getConnection(), true);
    }

    public static MyConnection openTran() {
        return MyConnection.createMySql(MySqlPool.getConnection(), false);
    }
}
