package com.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {

    // Database URL (can still be hard-coded if it's not sensitive)
    //private static final String URL  = "jdbc:mysql://host.docker.internal:3306/online_library?useSSL=false&serverTimezone=UTC";
    private static final String URL  = "jdbc:mysql://localhost:3306/online_library?useSSL=false&serverTimezone=UTC";
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    // Load credentials from environment variables
    private static final String USERNAME = "tarik";
    private static final String PASSWORD = "tarik123";

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
                LOGGER.log(Level.SEVERE, "Failed to close database connection", e);
            }
        }
    }
}
