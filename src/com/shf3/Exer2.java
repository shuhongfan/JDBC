package com.shf3;

import com.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Exer2 {
  @Test
  public void testInsert(){
    Scanner scanner = new Scanner(System.in);
    System.out.println("四级/六级：");
    int type = scanner.nextInt();
    System.out.println("身份证号：");
    String IDCard = scanner.next();
    System.out.println("准考证号：");
    String examCard = scanner.next();
    System.out.println("学生姓名：");
    String studentName = scanner.next();
    System.out.println("所在城市：");
    String location = scanner.next();
    System.out.println("考试成绩：");
    int grade = scanner.nextInt();

    String sql="insert into examstudent(type,IDCard,examCard,studentName,location,grade) values(?,?,?,?,?,?)";
    int insertCount = update(sql, type, IDCard, examCard, studentName, location, grade);
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
