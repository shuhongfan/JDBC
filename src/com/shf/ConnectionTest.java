package com.shf;


import org.junit.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
  @Test
  public void testConnection1() throws SQLException {
    Driver driver=new com.mysql.jdbc.Driver();
    String url="jdbc:mysql://localhost:3306/jdbc";
    Properties info=null;
    Connection connect = driver.connect(url, info);
    System.out.println(connect);
  }
}
