package com.shf2;

import com.shf.ConnectionTest;
import com.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class PreparedStatementUpdateTest {
  @Test
  public void testInsert(){
    Connection connection = null;
    PreparedStatement ps = null;
    try {
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
      connection = DriverManager.getConnection(url, user, password);
      System.out.println(connection);

//    预编译sql语句 返回preparedStatement
      String sql="insert into customers(name,email,birth) values(?,?,?)";
      ps = connection.prepareStatement(sql);

//    填充占位符
      ps.setString(1,"哪吒");
      ps.setString(2,"sss@g.com");
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date parse = sdf.parse("1000-01-01");
      ps.setString(3, String.valueOf(new java.sql.Date(parse.getTime())));

      //    执行操作
      ps.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }finally {
      //    资源关闭
      try {
        if (ps!=null) ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      try {
        if (connection!=null) connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void testUpdate(){
    Connection connection = null;
    PreparedStatement ps = null;
    try {
//    获取数据库连接
      connection = JDBCUtils.getConnection();

//    预编译sql语句,返回preparedStatement的实例
      String sql="update customers set name=? where id=?";
      ps = connection.prepareStatement(sql);

//    填充占位符
      ps.setObject(1,"莫扎特");
      ps.setObject(2,18);

//    执行
      ps.execute();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      //    资源关闭
      JDBCUtils.closeResource(connection,ps);
    }
  }

  @Test
  public void update(String sql,Object ...args){
    Connection conn = null;
    PreparedStatement ps = null;
    try {
      //    获取数据库连接
      conn = JDBCUtils.getConnection();

      //    预编译sql语句,返回preparedStatement的实例
      ps = conn.prepareStatement(sql);

      //    填充占位符
      for (int i = 0; i < args.length; i++) {
        ps.setObject(i+1,args[i]);
      }

//    执行操作
      ps.execute();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
//      资源的关闭
      JDBCUtils.closeResource(conn,ps);
    }
  }

  @Test
  public void testCommonUpdate(){
//    删除
    String sql="delete from customers where id = ?";
    update(sql,3);
  }

  @Test
  public void testCommonUpdate1(){
//    修改
    String sql="update `order` set order_name=? where order_id=?";
    update(sql,"DD","2");
  }
}
