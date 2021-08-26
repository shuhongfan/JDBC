package com.shf3;

import com.bean.Customer;
import com.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

public class Exer3 {
  @Test
  public void deleteByExamCard1(){
    System.out.println("请输入学生的考号：");
    Scanner scanner = new Scanner(System.in);
    String examCard = scanner.next();
    String sql1="delete from examstudent where examCard=?";
    int deleteCount = update(sql1, examCard);
    if (deleteCount > 0) System.out.println("删除成功");
    else System.out.println("查无此人");
  }

  @Test
  public void deleteByExamCard(){
    System.out.println("请输入学生的考号：");
    Scanner scanner = new Scanner(System.in);
    String examCard = scanner.next();
    String sql="select flowID, type, IDCard, examCard, studentName name, location, grade from examstudent where examCard=?";
    Student student = getInstance(Student.class, sql, examCard);
    if (student == null) System.out.println("查无此人，请重新输入");
    else {
      String sql1="delete from examstudent where examCard=?";
      int deleteCount = update(sql1, examCard);
      if (deleteCount > 0) System.out.println("删除成功");
    }
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

  @Test
  public void queryWithIDCardOrExamCard(){
    System.out.println("请选择您要输入的类型：");
    System.out.println("a.准考证");
    System.out.println("b.身份证");
    Scanner scanner = new Scanner(System.in);
    String selection = scanner.next();
    if ("a".equalsIgnoreCase(selection)){
      System.out.println("请输入准考证号：");
      String examCard = scanner.next();
      String sql="select flowID, type, IDCard, examCard, studentName name, location, grade from examstudent where examCard=?";
      Student student = getInstance(Student.class,sql, examCard);
      if (student != null) System.out.println(student);
      else System.out.println("输入的准考证号有误");
    } else if("b".equalsIgnoreCase(selection)){
      System.out.println("请输入身份证号：");
      String IDCard = scanner.next();
      String sql="select flowID, type, IDCard, examCard, studentName name, location, grade from examstudent where IDCard=?";
      Student student = getInstance(Student.class,sql, IDCard);
      if (student != null) System.out.println(student);
      else System.out.println("输入的准考证号有误");
    }
  }

  public <T>T getInstance(Class<T> clazz,String sql, Object... args){
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet resultSet = null;
    try {
      conn = JDBCUtils.getConnection();
      ps = conn.prepareStatement(sql);
      for (int i = 0; i < args.length; i++) {
        ps.setObject(i+1,args[i]);
      }
      resultSet = ps.executeQuery();

//    获取结果集的元数据
      ResultSetMetaData metaData = resultSet.getMetaData();
//    获取结果集的列数
      int columnCount = metaData.getColumnCount();

      if (resultSet.next()){
        T t = clazz.newInstance();
        for (int i = 0; i < columnCount; i++) {
          //        获取列值
          Object columnValue = resultSet.getObject(i + 1);
          //        获取每个列的别名
          String columnLabel = metaData.getColumnLabel(i + 1);
//          给cust对象指定的columnName属性,赋值为columnValue,通过反射
          Field field = clazz.getDeclaredField(columnLabel);
          field.setAccessible(true);
          field.set(t,columnValue);
        }
        return t;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCUtils.closeResource(conn,ps,resultSet);
    }
    return null;
  }
}
