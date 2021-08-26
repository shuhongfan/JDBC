package com.dao;

import com.bean.Customer;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

public interface CustomerDAO {
  void insert(Connection conn, Customer cust);

  void deleteById(Connection conn,int id);

  void update(Connection conn,Customer cust);

  Customer getCustomerById(Connection conn,int id);

  List<Customer> getAll(Connection conn);

//  返回书记表中的数据的条目数
  Long getCount(Connection conn);

//  获取生日最大
  Date getMaxBirth(Connection conn);

}
