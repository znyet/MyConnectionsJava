package com.yet.dbhelper;

import com.yet.dapper.MyConnection;

public class DbHelper {
    public static MyConnection GetConn(){
        return MyConnection.CreateMySql(MySqlPool.getConnection());
    }
}
