package test.java.com.library.model;

import com.library.model.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Timestamp;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    public void testUserCreation() {
        user.setId(1);
        user.setUsername("john_doe");
        user.setEmail("john@example.com");
        user.setFullName("John Doe");
        user.setRole("USER");

        assertEquals("ID should be 1", 1, user.getId());
        assertEquals("Username should be john_doe", "john_doe", user.getUsername());
        assertEquals("Email should be john@example.com", "john@example.com", user.getEmail());
        assertEquals("Full name should be John Doe", "John Doe", user.getFullName());
        assertEquals("Role should be USER", "USER", user.getRole());
    }

    @Test
    public void testIsAdmin_ShouldReturnTrueForAdminRole() {
        user.setRole("ADMIN");
        assertTrue("User with ADMIN role should be identified as admin", user.isAdmin());
    }

    @Test
    public void testIsAdmin_ShouldReturnFalseForUserRole() {
        user.setRole("USER");
        assertFalse("User with USER role should not be identified as admin", user.isAdmin());
    }

    @Test
    public void testIsAdmin_ShouldReturnFalseForNullRole() {
        user.setRole(null);
        assertFalse("User with null role should not be identified as admin", user.isAdmin());
    }

    @Test
    public void testUserConstructor() {
        User testUser = new User(1, "jane_doe", "jane@example.com", "Jane Doe", "USER");

        assertEquals("ID should be 1", 1, testUser.getId());
        assertEquals("Username should be jane_doe", "jane_doe", testUser.getUsername());
        assertEquals("Email should be jane@example.com", "jane@example.com", testUser.getEmail());
        assertEquals("Full name should be Jane Doe", "Jane Doe", testUser.getFullName());
        assertEquals("Role should be USER", "USER", testUser.getRole());
    }

    @Test
    public void testPasswordField() {
        String hashedPassword = "abc123def456";
        user.setPassword(hashedPassword);

        assertEquals("Password should be correctly set", hashedPassword, user.getPassword());
    }

    @Test
    public void testTimestamps() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        assertEquals("Created at timestamp should match", now, user.getCreatedAt());
        assertEquals("Updated at timestamp should match", now, user.getUpdatedAt());
    }
}
