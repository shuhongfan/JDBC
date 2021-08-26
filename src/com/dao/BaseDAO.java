package com.dao;

import com.bean.Customer;
import com.utils.JDBCUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<T> {
  private Class<T> clazz=null;
  {
//    获取当前BAseDAO的子类继承父类的泛型
    Type genericSuperclass = this.getClass().getGenericSuperclass();
    ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
    Type[] typeArguments = parameterizedType.getActualTypeArguments();
    clazz=(Class<T>) typeArguments[0];
  }
//  增删改
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

//  查询一个
  public T getInstance(Connection conn, String sql, Object... args){
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

//  查询多个
  public List<T> getForList(Connection conn, String sql, Object... args){
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
      JDBCUtils.closeResource(null,ps,resultSet);
    }
    return null;
  }

//  查询特殊值
  public <E> E getValue(Connection conn,String sql,Object...args){
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = conn.prepareStatement(sql);
      for (int i = 0; i < args.length; i++) {
        ps.setObject(i+1,args[i]);
      }

      rs = ps.executeQuery();
      if (rs.next()){
        return (E) rs.getObject(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(null,ps,rs);
    }
    return null;
  }
}
