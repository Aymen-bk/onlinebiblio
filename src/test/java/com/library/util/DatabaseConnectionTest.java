package com.library.util;

import org.junit.*;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DatabaseConnectionTest {

    private static Connection mockConnection;

    @BeforeClass
    public static void setupClass() {
        // Create a mock Connection
        mockConnection = mock(Connection.class);
        // Inject the mock connection
        DatabaseConnection.setTestConnection(mockConnection);
    }

    @AfterClass
    public static void cleanupClass() throws SQLException {
        // Close the mock connection if necessary
        DatabaseConnection.closeConnection(mockConnection);
        // Reset test connection
        DatabaseConnection.setTestConnection(null);
    }

    @Test
    public void testGetConnection_ReturnsMockConnection() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        assertNotNull("Connection should not be null", conn);
        assertEquals("Should return the injected mock connection", mockConnection, conn);
    }

    @Test
    public void testCloseConnection_DoesNotThrow() {
        try {
            DatabaseConnection.closeConnection(mockConnection);
            // Verify close() was called
            verify(mockConnection).close();
        } catch (SQLException e) {
            fail("SQLException should not occur");
        }
    }

    @Test
    @Ignore("Requires real DB credentials")
    public void testRealConnection() {
        // Temporarily disable the mock to test real DB connection
        DatabaseConnection.setTestConnection(null);
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            assertNotNull("Real database connection should not be null", conn);
            assertFalse("Connection should be open", conn.isClosed());
        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn);
            // Re-inject mock for other tests
            DatabaseConnection.setTestConnection(mockConnection);
        }
    }
}
