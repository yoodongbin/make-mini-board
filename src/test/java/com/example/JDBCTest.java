package com.example;

import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCTest {
    static {
        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("com.mysql.jdbc.Driver");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnection() {

        try(Connection con =
                    DriverManager.getConnection(
                            "jdbc:mysql://127.0.0.1:3306/board?serverTimezone=Asia/Seoul",
                            "root",
                            "220106")){
            System.out.println(con);
        } catch (Exception e) {
            System.out.println("Fail");
        }

    }
}
