package it.dan.dao;

import java.sql.*;

public class ConnectionToDB {
    private static final String DB_URL = "jdbc:mysql://danit.cukm9c6zpjo8.us-west-2.rds.amazonaws.com:3306/fs5";
    private static final String USERNAME = "fs5_user";
    private static final String USER_PASS = "bArceloNa";

//    private static final String DB_URL = "jdbc:postgresql://localhost:5432/tinder";
//    private static final String USERNAME = "postgres";
//    private static final String USER_PASS = "123";



    public static Connection getConnection() {


        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, USER_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}