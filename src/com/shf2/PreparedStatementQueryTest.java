package com.shf2;

import com.bean.Customer;
import com.bean.Order;
import com.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PreparedStatementQueryTest {
  @Test
  public void testLogin(){
    Scanner scanner = new Scanner(System.in);

    System.out.println("请输入用户名：");
    String name = scanner.nextLine();
    System.out.println("亲输入密码：");
    String password = scanner.nextLine();
    String sql="select user,password from user_table where=? and password=?";
    User instance = getInstance(User.class, sql, name, password);
    if (instance != null) System.out.println("登录成功");
    else System.out.println("用户名或密码错误");
  }

  @Test
  public void testGetInstance(){
    String sql="select id,name,email from customers where id=?";
    Customer customer = getInstance(Customer.class, sql, 12);
    System.out.println(customer);

    String sql1="select order_id orderId,order_name orderName from `order` where order_id=?";
    List<Order> orderList = getForList(Order.class, sql1, 1);
    orderList.forEach(System.out::println);
  }

  @Test
  public void testGetForList(){
    String sql="select id,name,email from customers where id<?";
    List<Customer> customerList = getForList(Customer.class, sql, 12);
    customerList.forEach(System.out::println);

    String sql1="select order_id orderId,order_name orderName from `order` where order_id<?";
    List<Order> orderList = getForList(Order.class, sql1, 5);
    orderList.forEach(System.out::println);

    String sql2="select order_id orderId,order_name orderName from `order`";
    List<Order> orderList1 = getForList(Order.class, sql1);
    orderList1.forEach(System.out::println);
  }

  public <T> List<T> getForList(Class<T> clazz, String sql, Object... args){
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet resultSet = null;
    try {
      conn = JDBCUtils.getConnection();
      ps = conn.prepareStatement(sql);
      for (int i = 0; i < args.length; i++) {
        ps.setObject(i+1,args[i]);
      }
      resultSet = ps.executeQuery();

//    获取结果集的元数据
      ResultSetMetaData metaData = resultSet.getMetaData();
//    获取结果集的列数
      int columnCount = metaData.getColumnCount();

//      创建集合
      ArrayList<T> list = new ArrayList<>();
      while (resultSet.next()){
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
        list.add(t);
      }
      return list;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,ps,resultSet);
    }
    return null;
  }

  public <T>T getInstance(Class<T> clazz,String sql, Object... args){
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet resultSet = null;
    try {
      conn = JDBCUtils.getConnection();
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
      JDBCUtils.closeResource(conn,ps,resultSet);
    }
    return null;
  }
}
