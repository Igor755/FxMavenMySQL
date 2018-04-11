package com.metlin.mavenfxmysql.util;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    public static final String USER = "root";
    public static final String PASSWORD = "root";
    public static final String URL = "jdbc:mysql://localhost:3306/mysql";
    //private static String error = "";
    //String error = "";



    public static String DBUtil(String s) {

            Connection connection;
            Driver driver;


        try {
            driver = new FabricMySQLDriver();
        }
        catch (SQLException ex){
            s = "Error for create driver";
            System.out.println(s);
            return s;
        }

        try{
            DriverManager.registerDriver(driver);
        }
        catch (SQLException ex){
            s = "Dont register driver";
            System.out.println(s);
            return s;
        }
        try{
            s = "connect...........";
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println(s);
            return s;
        }
        catch (SQLException ex){
            s = "Dont create connect";
            System.out.println(s);
            return s;
        }


    }
}
