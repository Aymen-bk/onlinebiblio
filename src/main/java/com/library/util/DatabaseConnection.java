package com.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Database URL (can still be hard-coded if it's not sensitive)
    private static final String URL = "jdbc:mysql://localhost:3306/online_library?useSSL=false&serverTimezone=UTC";

    // Load credentials from environment variables
    private static final String USERNAME = System.getenv("DB_USERNAME");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    // Connection for unit tests (mock)
    private static Connection testConnection = null;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (testConnection != null) {
            return testConnection; // return mock connection for testing
        }

        if (USERNAME == null || PASSWORD == null) {
            throw new RuntimeException("Database credentials not set in environment variables");
        }

        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void setTestConnection(Connection conn) {
        testConnection = conn; // inject mock connection for unit tests
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
