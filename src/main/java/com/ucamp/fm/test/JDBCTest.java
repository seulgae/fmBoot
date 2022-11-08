package com.ucamp.fm.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection con = null;

        Class.forName("oracle.jdbc.driver.OracleDriver");
        System.out.println("정상 로딩");

        con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "fm", "oracle");
        System.out.println("데이터베이스의 연결에 성공하였습니다.");
    }
}