package com.shf5;

import com.shf2.User;
import com.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.*;

public class TransactionTest {
  @Test
  public void testTransactionSelect() throws Exception{
    Connection conn = JDBCUtils.getConnection();
//    获取隔离级别
    System.out.println(conn.getTransactionIsolation());
//    取消自动提交
    conn.setAutoCommit(false);
    conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

    String sql="select user,password,balance from user_table where user=?";
    User user = getInstance(conn, User.class, sql, "CC");

    System.out.println(user);
  }

  @Test
  public void testTransactionUpdate() throws Exception{
    Connection conn = JDBCUtils.getConnection();
    String sql="update user_table set balance=? where user=?";
    update(conn, sql,5000,"CC");
    Thread.sleep(5000);
    System.out.println("修改成功");
  }

  public <T>T getInstance(Connection conn,Class<T> clazz,String sql, Object... args){
    PreparedStatement ps = null;
    ResultSet resultSet = null;
    try {
      ps = conn.prepareStatement(sql);
      for (int i = 0; i < args.length; i++) {
        ps.setObject(i+1,args[i]);
      }
      resultSet = ps.executeQuery();

//    获取结果集的元数据
      ResultSetMetaData metaData = resultSet.getMetaData();
//    获取结果集的列数
      int columnCount = metaData.getColumnCount();

      if (resultSet.next()){
        T t = clazz.newInstance();
        for (int i = 0; i < columnCount; i++) {
          //        获取列值
          Object columnValue = resultSet.getObject(i + 1);
          //        获取每个列的别名
          String columnLabel = metaData.getColumnLabel(i + 1);
//          给cust对象指定的columnName属性,赋值为columnValue,通过反射
          Field field = clazz.getDeclaredField(columnLabel);
          field.setAccessible(true);
          field.set(t,columnValue);
        }
        return t;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(null,ps,resultSet);
    }
    return null;
  }

  @Test
  public void testUpdate(){
    Connection conn = null;
    try {
      conn = JDBCUtils.getConnection();
//      取消数据的自动提交 功能
      conn.setAutoCommit(false);

      String sql="update user_table set balance=balance-100 where user=?";
      update(conn,sql,"AA");

      //      模拟网络异常
      System.out.println(10/0);

      String sql1="update user_table set balance=balance+100 where user=?";
      update(conn,sql1,"BB");
      System.out.println("转账成功");

//      提交数据
      conn.commit();
    } catch (Exception e) {
      e.printStackTrace();
//      回滚数据
      try {
        if (conn != null) {
          conn.rollback();
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    } finally {
      JDBCUtils.closeResource(conn,null);
    }
  }

  @Test
  public int update(Connection conn,String sql, Object ...args){
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
      return ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
//      资源的关闭
      JDBCUtils.closeResource(null,ps);
    }
    return 0;
  }
}
