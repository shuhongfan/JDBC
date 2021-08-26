package com.dao;

import com.bean.Customer;
import com.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

class CustomerDAOTest {
  private CustomerDAOImpl dao=new CustomerDAOImpl();

  @Test
  void insert() {
    Connection conn = null;
    try {
      conn = JDBCUtils.getConnection();
      Customer cust = new Customer(1, "花花", "ss@ll.com", new Date(123354265163L));
      dao.insert(conn,cust);
      System.out.println("添加成功");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,null);
    }
  }

  @Test
  void deleteById() {
    Connection conn = null;
    try {
      conn = JDBCUtils.getConnection();
      dao.deleteById(conn,13);
      System.out.println("删除成功");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,null);
    }
  }

  @Test
  void update() {
    Connection conn = null;
    try {
      conn = JDBCUtils.getConnection();
      Customer cust = new Customer(18, "钢琴家", "gqj@ll.com", new Date(454845164L));
      dao.update(conn,cust);
      System.out.println("修改成功");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,null);
    }
  }

  @Test
  void getCustomerById() {
    Connection conn = null;
    try {
      conn = JDBCUtils.getConnection();
      Customer customer = dao.getCustomerById(conn, 10);
      System.out.println("查询成功");
      System.out.println(customer);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,null);
    }
  }

  @Test
  void getAll() {
    Connection conn = null;
    try {
      conn = JDBCUtils.getConnection();
      List<Customer> customerList = dao.getAll(conn);
      System.out.println("查询成功");
      customerList.forEach(System.out::println);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,null);
    }
  }

  @Test
  void getCount() {
    Connection conn = null;
    try {
      conn = JDBCUtils.getConnection();
      Long count = dao.getCount(conn);
      System.out.println("查询成功");
      System.out.println("所有的人数为："+count);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,null);
    }
  }

  @Test
  void getMaxBirth() {
    Connection conn = null;
    try {
      conn = JDBCUtils.getConnection();
      java.util.Date maxBirth = dao.getMaxBirth(conn);
      System.out.println("查询成功");
      System.out.println("最大生日为："+maxBirth);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,null);
    }
  }
}