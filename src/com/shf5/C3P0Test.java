package com.shf5;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class C3P0Test {
  private static ComboPooledDataSource cpds=new ComboPooledDataSource("hellc3p0");
  @Test
  public void testGetConnection1() throws Exception{
    Connection conn = cpds.getConnection();
    System.out.println(conn);
  }

  @Test
  public void testGetConnection() throws Exception{
    ComboPooledDataSource cpds = new ComboPooledDataSource();
    cpds.setDriverClass( "com.mysql.cj.jdbc.Driver" ); //loads the jdbc driver
    cpds.setJdbcUrl( "jdbc:mysql://localhost:3306/jdbc" );
    cpds.setUser("root");
    cpds.setPassword("root");

//    初始化数据库连接池的连接数
    cpds.setInitialPoolSize(10);
    Connection conn = cpds.getConnection();
    System.out.println(conn);

//    销毁数据库连接池
//    DataSources.destroy(cpds);
  }

}
