package com.shf;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
  @Test
  public void testConnection1() throws SQLException {
//    获取driver实现类对象
    Driver driver=new com.mysql.cj.jdbc.Driver();

    String url="jdbc:mysql://localhost:3306/jdbc";

    Properties info=new Properties();
    info.setProperty("user","root");
    info.setProperty("password","root");

    Connection connect = driver.connect(url, info);

    System.out.println(connect);
  }

  @Test
  public void testConnection2() throws Exception {
    Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
    Driver driver = (Driver) clazz.newInstance();

    String url="jdbc:mysql://localhost:3306/test";

    Properties info=new Properties();
    info.setProperty("user","root");
    info.setProperty("password","root");

    Connection connect = driver.connect(url, info);
    System.out.println(connect);
  }

  @Test
  public void testConnection3() throws Exception{

    Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
    Driver driver = (Driver) clazz.newInstance();

    String url="jdbc:mysql://localhost:3306/jdbc";
    String user="root";
    String password="root";

//    注册驱动
    DriverManager.registerDriver(driver);

    Connection connection = DriverManager.getConnection(url, user, password);
    System.out.println(connection);
  }

  @Test
  public void testConnection4() throws Exception{
    String url="jdbc:mysql://localhost:3306/jdbc";
    String user="root";
    String password="root";

//    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection = DriverManager.getConnection(url, user, password);
    System.out.println(connection);
  }

  @Test
  public void testConnection5() throws Exception{
//    读取配置中文件的4个基本信息
    InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
    Properties pros = new Properties();
    pros.load(is);

    String user = pros.getProperty("user");
    String password = pros.getProperty("password");
    String url = pros.getProperty("url");
    String driverClass = pros.getProperty("driverClass");

//    加载驱动
    Class.forName(driverClass);

//    获取连接
    Connection connection = DriverManager.getConnection(url, user, password);
    System.out.println(connection);
  }
}
