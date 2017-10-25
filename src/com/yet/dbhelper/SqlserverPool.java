package com.yet.dbhelper;

import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * Created by Administrator on 2017-10-25.
 */
public class SqlserverPool {
    private static final HikariDataSource ds = new HikariDataSource();

    static {

        Properties pro = new Properties();
        InputStream in = SqlserverPool.class.getClassLoader().getResourceAsStream("/config/sqlserver.properties");
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ds.setDriverClassName(pro.getProperty("className"));
        ds.setJdbcUrl(pro.getProperty("url"));
    }

    // 从连接池中获得连接对象
    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
