package test.java.com.library.util;
import com.library.util.PasswordUtil;
import org.junit.Test;
import static org.junit.Assert.*;

public class PasswordUtilTest {

    @Test
    public void testHashPassword_ShouldReturnConsistentHash() {
        String password = "password123";
        String hash1 = PasswordUtil.hashPassword(password);
        String hash2 = PasswordUtil.hashPassword(password);

        assertEquals("Same password should produce same hash", hash1, hash2);
    }

    @Test
    public void testHashPassword_ShouldReturnDifferentHashForDifferentPasswords() {
        String password1 = "password123";
        String password2 = "password456";
        String hash1 = PasswordUtil.hashPassword(password1);
        String hash2 = PasswordUtil.hashPassword(password2);

        assertNotEquals("Different passwords should produce different hashes", hash1, hash2);
    }

    @Test
    public void testVerifyPassword_ShouldReturnTrueForCorrectPassword() {
        String password = "admin123";
        String hashedPassword = PasswordUtil.hashPassword(password);

        assertTrue("Verification should succeed for correct password",
                PasswordUtil.verifyPassword(password, hashedPassword));
    }

    @Test
    public void testVerifyPassword_ShouldReturnFalseForIncorrectPassword() {
        String password = "admin123";
        String wrongPassword = "wrongpassword";
        String hashedPassword = PasswordUtil.hashPassword(password);

        assertFalse("Verification should fail for incorrect password",
                PasswordUtil.verifyPassword(wrongPassword, hashedPassword));
    }

    @Test
    public void testHashPassword_ShouldNotBeNull() {
        String password = "test";
        String hash = PasswordUtil.hashPassword(password);

        assertNotNull("Hash should not be null", hash);
        assertFalse("Hash should not be empty", hash.isEmpty());
    }
}

