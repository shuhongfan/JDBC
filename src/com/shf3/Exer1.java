package com.shf3;

import com.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Exer1 {
  @Test
  public void testInert(){
    Scanner scanner = new Scanner(System.in);
    System.out.println("请输入用户名：");
    String name = scanner.nextLine();
    System.out.println("请输入邮箱:");
    String email = scanner.nextLine();
    System.out.println("请输入生日：");
    String birth = scanner.nextLine();

    String sql="insert into customers(name,email,birth) values(?,?,?)";
    int insertCount = update(sql, name, email, birth);
    if (insertCount > 0) System.out.println("添加成功");
    else System.out.println("添加失败");
  }

  @Test
  public int update(String sql, Object ...args){
    Connection conn = null;
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
      JDBCUtils.closeResource(conn,ps);
    }
    return 0;
  }
}
