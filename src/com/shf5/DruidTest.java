package com.shf5;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DruidTest {
  @Test
  public void getConnection() throws Exception {
    Properties pros = new Properties();
    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
    pros.load(is);

    DataSource dataSource = DruidDataSourceFactory.createDataSource(pros);
    Connection connection = dataSource.getConnection();
    System.out.println(connection);
  }
}
