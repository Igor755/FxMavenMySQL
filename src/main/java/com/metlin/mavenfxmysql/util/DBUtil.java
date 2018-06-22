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

        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private static void createTable() throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS people.warrior" +
                "(id INT NOT NULL primary key auto_increment, " +
                "numberunit INT NOT NULL, " +
                "last varchar(20) NOT NULL, " +
                "name varchar(20) NOT NULL, " +
                "middle varchar(20) NOT NULL, " +
                "birth date NOT NULL, " +
                "personalnumber varchar(8) NOT NULL, " +
                "militaryrank varchar(30) NOT NULL, " +
                "nomrspiner varchar(20) NOT NULL, " +
                "nomrdate date NOT NULL, " +
                "nomrnumber varchar(4) NOT NULL, " +
                "militarypost varchar(20) NOT NULL, " +
                "nompspiner varchar(20) NOT NULL, " +
                "nompdate date NOT NULL, " +
                "nompnumber varchar(4) NOT NULL)";


        statement = connection.createStatement();

        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}

