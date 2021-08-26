package com.shf2;

import com.bean.Customer;
import com.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.*;

public class CustomerForQuery {
  @Test void testQueryForCustomer(){
    String sql="select id,name,birth,email from customers where id=?";
    Customer customer = queryForCustomer(sql, 13);
    System.out.println(customer);
  }

  @Test
  public Customer queryForCustomer(String sql,Object... args){
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
        Customer customer=new Customer();
        for (int i = 0; i < columnCount; i++) {
  //        获取列值
          Object columnValue = resultSet.getObject(i + 1);
  //        获取每个列的列名
//          String columnName = metaData.getColumnName(i + 1);
          String columnLabel = metaData.getColumnLabel(i + 1);
//          给cust对象指定的columnName属性,赋值为columnValue,通过反射
          Field field = Customer.class.getDeclaredField(columnLabel);
          field.setAccessible(true);
          field.set(customer,columnValue);
        }
        return customer;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,ps,resultSet);
    }
    return null;
  }

  @Test
  public void testQuery1() throws Exception{
    Connection connection = JDBCUtils.getConnection();
    String sql="select id,name,email,birth from customers where id=?";
    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setObject(1,1);

//    执行并返回结果集
    ResultSet resultSet = ps.executeQuery();
    //    处理结果集
    if (resultSet.next()){ // 判断结果集的下一条是否有数据,如果有数据返回true,无数据,指针不会下移
      int id = resultSet.getInt(1);
      String name = resultSet.getString(2);
      String email = resultSet.getString(3);
      Date birth = resultSet.getDate(4);
//      System.out.println("id="+id+"name="+name+"email="+email+"birth="+birth);

      Customer customer = new Customer(id, name, email, birth);
      System.out.println(customer);

      JDBCUtils.closeResource(connection,ps,resultSet);
    }
  }
}
