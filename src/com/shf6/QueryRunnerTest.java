package com.shf6;

import com.bean.Customer;
import com.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class QueryRunnerTest {
  @Test
  public void testQuery1(){
    Connection conn = null;
    try {
      QueryRunner runner = new QueryRunner();
      conn = JDBCUtils.getConnection();
      String sql="select id,name,email,birth from customers where id=?";
      ResultSetHandler<Customer> handler = new ResultSetHandler<>() {
        @Override
        public Customer handle(ResultSet resultSet) throws SQLException {
//          return new Customer(33,"成龙","jack@12.com",new Date(125456541651L));
          if (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            java.sql.Date date = resultSet.getDate("birth");
            Customer customer = new Customer(id, name, email, date);
            return customer;
          }
          return null;
        }
      };
      Customer customer = runner.query(conn, sql, handler, 12);
      System.out.println(customer);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,null);
    }
  }

  @Test
  public void testQueryScalar1(){
    Connection conn = null;
    try {
      QueryRunner runner = new QueryRunner();
      conn = JDBCUtils.getConnection();
      String sql="select max(birth) from customers";
      ScalarHandler<Object> handler = new ScalarHandler<>();
      Date date = (Date) runner.query(conn, sql, handler);
      System.out.println(date);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,null);
    }
  }

  @Test
  public void testQueryScalar(){
    Connection conn = null;
    try {
      QueryRunner runner = new QueryRunner();
      conn = JDBCUtils.getConnection();
      String sql="select count(*) from customers";
      ScalarHandler<Object> handler = new ScalarHandler<>();
      Long count =(long) runner.query(conn, sql, handler);
      System.out.println(count);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,null);
    }
  }

  @Test
  public void testQueryMaps(){
    Connection conn = null;
    try {
      QueryRunner runner = new QueryRunner();
      conn = JDBCUtils.getConnection();
      String sql="select id,name,email,birth from customers where id<?";
      MapListHandler handler = new MapListHandler();
      List<Map<String, Object>> mapList = runner.query(conn, sql, handler, 12);
      mapList.forEach(System.out::println);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,null);
    }

  }
  @Test
  public void testQueryMap(){
    Connection conn = null;
    try {
      QueryRunner runner = new QueryRunner();
      conn = JDBCUtils.getConnection();
      String sql="select id,name,email,birth from customers where id=?";
      MapHandler handler = new MapHandler();
      Map<String, Object> map = runner.query(conn, sql, handler, 12);
      System.out.println(map);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,null);
    }

  }

  @Test
  public void testQueryList(){
    Connection conn = null;
    try {
      QueryRunner runner = new QueryRunner();
      conn = JDBCUtils.getConnection();
      String sql="select id,name,email,birth from customers where id<?";
      BeanListHandler<Customer> handler = new BeanListHandler<>(Customer.class);
      List<Customer> query = runner.query(conn, sql, handler, 12);
      query.forEach(System.out::println);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,null);
    }

  }

  @Test
  public void testQuery(){
    Connection conn = null;
    try {
      QueryRunner runner = new QueryRunner();
      conn = JDBCUtils.getConnection();
      String sql="select id,name,email,birth from customers where id=?";
      BeanHandler<Customer> handler = new BeanHandler<Customer>(Customer.class);
      Customer customer = runner.query(conn, sql, handler, 12);
      System.out.println(customer);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,null);
    }

  }

  @Test
  public void testInsert(){
    Connection conn = null;
    try {
      QueryRunner runner = new QueryRunner();
      conn = JDBCUtils.getConnection();
      String sql="insert into customers(name,email,birth)values(?,?,?)";
      int insertCount = runner.update(conn, sql, "蔡徐坤", "cxk@qq.com", "1992-08-09");
      System.out.println(insertCount);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,null);
    }
  }
}
