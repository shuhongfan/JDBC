package com.shf5;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPTest {
  @Test
  public void testGetConnection() throws SQLException {
    BasicDataSource basicDataSource = new BasicDataSource();
    basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    basicDataSource.setUrl("jdbc:mysql://localhost:3306/jdbc");
    basicDataSource.setUsername("root");
    basicDataSource.setPassword("root");

    basicDataSource.setInitialSize(10);

    Connection conn = basicDataSource.getConnection();
    System.out.println(conn);
  }
}
