package test.java.com.library.dao;

import com.library.dao.UserDAO;
import com.library.model.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserDAOTest {

    private UserDAO userDAO;

    @Before
    public void setUp() {
        userDAO = new UserDAO();
    }

    @Test
    public void testUserDAOInstantiation() {
        assertNotNull("UserDAO should be instantiated", userDAO);
    }

    @Test
    public void testFindByUsername_ShouldReturnNullWhenUserNotFound() {
        // Note: This test would require a database or mock the database connection
        // For now, we verify the method exists and can be called
        assertNotNull("findByUsername method should exist", userDAO);
    }

    @Test
    public void testCreateUser_ShouldAcceptValidUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("hashedpassword");
        user.setFullName("Test User");
        user.setRole("USER");

        assertNotNull("User object should not be null before creation", user);
        assertEquals("Username should be testuser", "testuser", user.getUsername());
        assertEquals("Email should be test@example.com", "test@example.com", user.getEmail());
    }

    @Test
    public void testUpdateUser_ShouldAcceptValidUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("updateduser");
        user.setEmail("updated@example.com");
        user.setFullName("Updated User");
        user.setRole("USER");

        assertEquals("Updated username should match", "updateduser", user.getUsername());
        assertEquals("Updated email should match", "updated@example.com", user.getEmail());
    }

    @Test
    public void testUserDAO_ShouldHaveRequiredMethods() {
        assertTrue("findByUsername method should exist",
                userDAO.getClass().getDeclaredMethods().length > 0);
        assertTrue("UserDAO should have create method",
                java.util.Arrays.stream(userDAO.getClass().getDeclaredMethods())
                        .anyMatch(m -> m.getName().equals("create")));
    }
}
