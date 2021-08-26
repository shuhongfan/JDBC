package com.shf4;

import com.bean.Customer;
import com.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Blob {
  @Test
  public void testQuery() {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    InputStream is = null;
    FileOutputStream fos = null;
    try {
      conn = JDBCUtils.getConnection();
      String sql="select id,name,email,birth,photo from customers where id=?";
      ps = conn.prepareStatement(sql);
      ps.setInt(1,22);
      rs = ps.executeQuery();
      if (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        Date birth = rs.getDate("birth");
        Customer cust = new Customer(id, name, email, birth);
        System.out.println(cust);

        java.sql.Blob photo = rs.getBlob("photo");
        is = photo.getBinaryStream();
        fos = new FileOutputStream("2.jpg");
        byte[] buffer=new byte[1024];
        int len;
        while ((len=is.read(buffer))!=-1) fos.write(buffer,0,len);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (is!=null) is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        if (fos!=null) fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      JDBCUtils.closeResource(conn,ps,rs);
    }
  }

  @Test
  public void testInsert() throws Exception{
    Connection connection = JDBCUtils.getConnection();
    String sql="insert into customers(name,email,birth,photo) values(?,?,?,?)";
    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setObject(1,"张雨生");
    ps.setObject(2,"ss@sa.com");
    ps.setObject(3,"1998-01-09");
    FileInputStream fis = new FileInputStream(new File("1.jpg"));
    ps.setBlob(4,fis);

    ps.execute();
    JDBCUtils.closeResource(connection,ps);
  }
}
