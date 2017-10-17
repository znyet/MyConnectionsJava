package com.yet.dbhelper;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;

/**
 * Created by Administrator on 2017-04-24.
 * MySQL数据库连接池
 */
public class MySqlPool {

    public MySqlPool() {}

    private static ComboPooledDataSource cpds;

    // 配置数据源
    private static void initDataSource() {
        cpds = new ComboPooledDataSource();
        cpds.setDataSourceName("acms01");
        cpds.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8");//连接url
        try {
            cpds.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } //数据库驱动
        cpds.setUser("root");//用户名
        cpds.setPassword("123456");//密码
        cpds.setMinPoolSize(10);//连接池中保留的最小连接数10
        cpds.setMaxPoolSize(100);//连接池中保留的最大连接数100
        cpds.setAcquireIncrement(10);//一次性创建新连接的数目
        cpds.setInitialPoolSize(10);//初始创建
        cpds.setMaxIdleTime(6000);//最大空闲时间
    }//*/

    static {
        initDataSource();
    }
    // 从连接池中获得连接对象
    public static Connection getConnection() {
        try {
            return cpds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 获得数据源
    public static DataSource getDataSource() {
        return cpds;
    }

}
