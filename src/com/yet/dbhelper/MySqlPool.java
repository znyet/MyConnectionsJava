package com.yet.dbhelper;


import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * Created by Administrator on 2017-04-24.
 * MySQL数据库连接池
 */
public class MySqlPool {

    private static final HikariDataSource ds = new HikariDataSource();

    static {

//        ds.setDriverClassName("org.mariadb.jdbc.Driver");
//        ds.setJdbcUrl("jdbc:mariadb://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8");
//        ds.setUsername("root");
//        ds.setPassword("123456");
//        ds.setMaximumPoolSize(10);
//        ds.setConnectionTimeout(30000); //30秒

        Properties pro = new Properties();
        InputStream in = null;

        in = MySqlPool.class.getClassLoader().getResourceAsStream("/config/mysql.properties");
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
        ds.setUsername(pro.getProperty("user"));
        ds.setPassword(pro.getProperty("password"));
        ds.setMaximumPoolSize(Integer.parseInt(pro.getProperty("maxPool")));
        ds.setConnectionTimeout(Long.parseLong(pro.getProperty("timeOut"))); //30秒

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
