package com.store.manager.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    // Class members
    private static final String url = "jdbc:mariadb://localhost:3306/inventory";
    private static final String driverName = "org.mariadb.jdbc.Driver";
    private static final String username = "j-kuarian";
    private static final String password = "@admin";
    private static Connection connection;

    // Queries for Category class
    public static Connection getConnection() {
        // Method to establish DB connection and return connection object

        try {
            Class.forName(driverName);
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } return connection;
    }
}
