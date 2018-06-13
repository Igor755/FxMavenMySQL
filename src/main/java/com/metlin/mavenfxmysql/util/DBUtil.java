package com.metlin.mavenfxmysql.util;

import java.sql.*;

public class DBUtil {

    private static Connection connection;
    static Statement statement = null;
    static String USER = "root";
    static String PASSWORD = "root";
    static String URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false";


    public static Connection getConnection() throws SQLException {


        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            createDatabase();
            createTable();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {

        } finally {

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);

            }
        }
        return connection;
    }

    private static void createDatabase() throws SQLException {


        String sql = "CREATE DATABASE IF NOT EXISTS people;";
        statement = connection.createStatement();
        statement.executeUpdate(sql);
        System.out.println("Database has been created");

    }

    private static void createTable() throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS people.warrior" +
                "(id INT NOT NULL primary key auto_increment, " +
                "name varchar(20) NOT NULL, " +
                "last varchar(20) NOT NULL, " +
                "middle varchar(20) NOT NULL, " +
                "birth date NOT NULL, " +
                "personalnumber varchar(8) NOT NULL, " +
                "militaryrank varchar(30) NOT NULL, " +
                "orderdate date NOT NULL)";

        statement = connection.createStatement();
        statement.executeUpdate(sql);
        System.out.println("Table has been created");

    }
}

