package com.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.shf.ConnectionTest;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
  public static Connection getConnection() throws Exception{
    Properties pros = new Properties();
    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
    pros.load(is);

    DataSource dataSource = DruidDataSourceFactory.createDataSource(pros);
    Connection connection = dataSource.getConnection();
    return connection;
  }
//  public static Connection getConnection() throws Exception{
//    ComboPooledDataSource cpds = new ComboPooledDataSource("hellc3p0");
//    Connection conn = cpds.getConnection();
//    return conn;
//  }
//  public static Connection getConnection() throws Exception{
//    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
//    Properties pros = new Properties();
//    pros.load(is);
//
//    String user = pros.getProperty("user");
//    String password = pros.getProperty("password");
//    String url = pros.getProperty("url");
//    String driverClass = pros.getProperty("driverClass");
//
////    加载驱动
//    Class.forName(driverClass);
//
////    获取连接
//    Connection connection = connection = DriverManager.getConnection(url, user, password);
//    return connection;
//  }

//  public static void closeResource(Connection connection, Statement ps){
//    //    资源关闭
//    try {
//      if (ps!=null) ps.close();
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    try {
//      if (connection!=null) connection.close();
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//  }

//  public static void closeResource(Connection connection, Statement ps, ResultSet resultSet){
//    //    资源关闭
//    try {
//      if (ps!=null) ps.close();
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    try {
//      if (connection!=null) connection.close();
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    try {
//      if (resultSet!=null) resultSet.close();
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//  }

  public static void closeResource(Connection connection){
    DbUtils.closeQuietly(connection);
  }
  public static void closeResource(Connection connection, Statement ps){
    DbUtils.closeQuietly(connection);
    DbUtils.closeQuietly(ps);
  }
  public static void closeResource(Connection connection, Statement ps, ResultSet resultSet){
    DbUtils.closeQuietly(connection);
    DbUtils.closeQuietly(ps);
    DbUtils.closeQuietly(resultSet);
  }
}
