package com.metlin.mavenfxmysql.util;

import java.sql.*;

public class DBUtil {

    private static Connection connection;
    //public Connection connection;


    public static Connection getConnection() throws SQLException {

        Statement statement = null;


        String USER = "root";
        String PASSWORD = "root";
        String URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
        String MyBase = "people";


        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            /*statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS" + MyBase);

            String sql = "CREATE TABLE IF NOT EXISTS warrior" +
            "(id INT NOT NULL primary key auto_increment," +
            "name varchar(100) NOT NULL, " +
            "last varchar(100) NOT NULL, " +
            "middle varchar(100) NOT NULL, " +
            "birth varchar(100) NOT NULL)";

            statement.executeUpdate(sql);
            statement.close();*/

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

