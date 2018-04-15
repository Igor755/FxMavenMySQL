package com.metlin.mavenfxmysql.util;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;

public class DBUtil {

    public static final String USER = "root";
    public static final String PASSWORD = "root";
    public static final String URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false";



    public Connection connection;
    public Driver driver;
    public String s;


    public String getConnection() {

        try {
            driver = new FabricMySQLDriver();
        } catch (SQLException ex) {
            s = "Error for create driver";
            System.out.println(s);
            return s;
        }

        try {
            DriverManager.registerDriver(driver);
        } catch (SQLException ex) {
            s = "Dont register driver";
            System.out.println(s);
            return s;
        }
        try {
            s = "connect...........";
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(s);
            return s;
        } catch (SQLException ex) {
            s = "Dont create connect";
            System.out.println(s);
            return s;
        }


    }
}

/*
    public ArrayList<User> userList()

    {

        ArrayList<User> userList = new ArrayList<User>();
        Connection connection = getConnection();

        String query = "SELECT * FROM 'warrior'";
        Statement statement;
        ResultSet resultSet;

        try

        {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            User user;
            while (resultSet.next()) {
                user = new User(resultSet.getInt("idwarrior"), resultSet.getString("name"), resultSet.getString("lastname"), resultSet.getString("middlename"), resultSet.getDate("birth"));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return userList;
    }*/
