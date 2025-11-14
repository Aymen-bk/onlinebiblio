package test.java.com.library.model;

import com.library.model.Borrowing;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Date;  // Import java.sql.Date instead of Timestamp
import java.util.Calendar;

public class BorrowingTest {

    private Borrowing borrowing;

    @Before
    public void setUp() {
        borrowing = new Borrowing();
    }

    @Test
    public void testBorrowingCreation() {
        borrowing.setId(1);
        borrowing.setUserId(5);
        borrowing.setBookId(10);
        borrowing.setStatus("ACTIVE");

        assertEquals("ID should be 1", 1, borrowing.getId());
        assertEquals("User ID should be 5", 5, borrowing.getUserId());
        assertEquals("Book ID should be 10", 10, borrowing.getBookId());
        assertEquals("Status should be ACTIVE", "ACTIVE", borrowing.getStatus());
    }

    @Test
    public void testBorrowingDateFields() {
        Date borrowDate = new Date(System.currentTimeMillis());  // Use java.sql.Date
        borrowing.setBorrowDate(borrowDate);

        assertEquals("Borrow date should match", borrowDate, borrowing.getBorrowDate());
    }

    @Test
    public void testBorrowingStatus() {
        borrowing.setStatus("RETURNED");
        assertEquals("Status should be RETURNED", "RETURNED", borrowing.getStatus());

        borrowing.setStatus("OVERDUE");
        assertEquals("Status should be OVERDUE", "OVERDUE", borrowing.getStatus());
    }

    @Test
    public void testBorrowingReturnDate() {
        Date returnDate = new Date(System.currentTimeMillis());  // Use java.sql.Date
        borrowing.setReturnDate(returnDate);

        assertEquals("Return date should match", returnDate, borrowing.getReturnDate());
    }

    @Test
    public void testDueDate() {
        Date dueDate = new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000)); // 7 days from now
        borrowing.setDueDate(dueDate);

        assertEquals("Due date should match", dueDate, borrowing.getDueDate());
    }

    @Test
    public void testIsOverdue() {
        // Test when book is not overdue
        Date futureDueDate = new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000));
        borrowing.setDueDate(futureDueDate);
        borrowing.setReturnDate(null);

        assertFalse("Book should not be overdue", borrowing.isOverdue());

        // Test when book is overdue
        Date pastDueDate = new Date(System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000));
        borrowing.setDueDate(pastDueDate);
        borrowing.setReturnDate(null);

        assertTrue("Book should be overdue", borrowing.isOverdue());

        // Test when book is returned (not overdue regardless of due date)
        borrowing.setReturnDate(new Date(System.currentTimeMillis()));
        assertFalse("Returned book should not be overdue", borrowing.isOverdue());
    }

    @Test
    public void testTimestampFields() {
        // Test Timestamp fields (createdAt and updatedAt)
        java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
        borrowing.setCreatedAt(now);
        borrowing.setUpdatedAt(now);

        assertEquals("Created at should match", now, borrowing.getCreatedAt());
        assertEquals("Updated at should match", now, borrowing.getUpdatedAt());
    }

    @Test
    public void testJoinedData() {
        borrowing.setUserName("John Doe");
        borrowing.setBookTitle("The Great Gatsby");
        borrowing.setBookAuthor("F. Scott Fitzgerald");

        assertEquals("User name should match", "John Doe", borrowing.getUserName());
        assertEquals("Book title should match", "The Great Gatsby", borrowing.getBookTitle());
        assertEquals("Book author should match", "F. Scott Fitzgerald", borrowing.getBookAuthor());
    }
}