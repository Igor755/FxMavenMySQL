package com.metlin.mavenfxmysql.util;

import java.sql.*;

public class DBUtil {

    private static Connection connection;
    //public Connection connection;


    public static Connection getConnection() throws SQLException {


        String USER = "root";
        String PASSWORD = "root";
        String URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
        try {
            Class.forName("com.mysql.jdbc.Driver");
             connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

