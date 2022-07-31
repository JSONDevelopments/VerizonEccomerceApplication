package com.jason.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCon {

    public static Connection getConnection() {
        String jdbcURL = "jdbc:mysql://ecommercedb.mysql.database.azure.com/ecommercedb";
        String jdbcUsername = "jasonjj33";
        String jdbcPassword = "Awesome33";
        String jdbcDriver = "com.mysql.jdbc.Driver";
        Connection connection = null;
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
