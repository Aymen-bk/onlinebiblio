package com.library.dao;

import com.library.model.Borrowing;
import com.library.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BorrowingDAO {

    private static final Logger logger = LoggerFactory.getLogger(BorrowingDAO.class);

    public boolean create(Borrowing borrowing) {
        String sql = "INSERT INTO borrowings (user_id, book_id, borrow_date, due_date, status, notes) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, borrowing.getUserId());
            stmt.setInt(2, borrowing.getBookId());
            stmt.setDate(3, borrowing.getBorrowDate());
            stmt.setDate(4, borrowing.getDueDate());
            stmt.setString(5, borrowing.getStatus());
            stmt.setString(6, borrowing.getNotes());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    borrowing.setId(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            logger.error("Failed to create borrowing for user {} and book {}", borrowing.getUserId(), borrowing.getBookId(), e);
            throw new IllegalArgumentException("Unable to create borrowing", e);
        }
        return false;
    }

    public List<Borrowing> findByUserId(int userId) {
        List<Borrowing> borrowings = new ArrayList<>();
        String sql = """
                SELECT b.*, bk.title AS book_title, bk.author AS book_author
                FROM borrowings b
                JOIN books bk ON b.book_id = bk.id
                WHERE b.user_id = ?
                ORDER BY b.borrow_date DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                borrowings.add(extractBorrowingFromResultSet(rs));
            }

        } catch (SQLException e) {
            logger.error("Failed to load borrowings for user ID {}", userId, e);
            return Collections.emptyList();
        }

        return borrowings;
    }

    public List<Borrowing> findAll() {
        List<Borrowing> borrowings = new ArrayList<>();
        String sql = """
                SELECT b.*, u.full_name AS user_name, bk.title AS book_title, bk.author AS book_author
                FROM borrowings b
                JOIN users u ON b.user_id = u.id
                JOIN books bk ON b.book_id = bk.id
                ORDER BY b.borrow_date DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Borrowing borrowing = extractBorrowingFromResultSet(rs);
                borrowing.setUserName(rs.getString("user_name"));
                borrowings.add(borrowing);
            }

        } catch (SQLException e) {
            logger.error("Failed to load all borrowings", e);
            return Collections.emptyList();
        }

        return borrowings;
    }

    public Borrowing findById(int id) {
        String sql = """
                SELECT b.*, u.full_name AS user_name, bk.title AS book_title, bk.author AS book_author
                FROM borrowings b
                JOIN users u ON b.user_id = u.id
                JOIN books bk ON b.book_id = bk.id
                WHERE b.id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Borrowing borrowing = extractBorrowingFromResultSet(rs);
                borrowing.setUserName(rs.getString("user_name"));
                return borrowing;
            }

        } catch (SQLException e) {
            logger.error("Failed to find borrowing with ID {}", id, e);
            return null;
        }

        return null;
    }

    public boolean returnBook(int borrowingId, Date returnDate) {
        String sql = "UPDATE borrowings SET return_date = ?, status = 'RETURNED' WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, returnDate);
            stmt.setInt(2, borrowingId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.error("Failed to return book for borrowing ID {}", borrowingId, e);
            return false;
        }
    }

    public boolean hasActiveBorrowing(int userId, int bookId) {
        String sql = "SELECT COUNT(*) FROM borrowings WHERE user_id = ? AND book_id = ? AND status = 'BORROWED'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            logger.error("Failed to check active borrowing for user {} and book {}", userId, bookId, e);
            return false;
        }

        return false;
    }

    public int getActiveBorrowingsCount() {
        String sql = "SELECT COUNT(*) FROM borrowings WHERE status = 'BORROWED'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            logger.error("Failed to count active borrowings", e);
        }

        return 0;
    }

    public int getOverdueBorrowingsCount() {
        String sql = "SELECT COUNT(*) FROM borrowings WHERE status = 'BORROWED' AND due_date < CURDATE()";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            logger.error("Failed to count overdue borrowings", e);
        }

        return 0;
    }

    public boolean updateOverdueStatus() {
        String sql = "UPDATE borrowings SET status = 'OVERDUE' WHERE status = 'BORROWED' AND due_date < CURDATE()";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            return stmt.executeUpdate(sql) > 0;

        } catch (SQLException e) {
            logger.error("Failed to update overdue borrowings", e);
            return false;
        }
    }

    private Borrowing extractBorrowingFromResultSet(ResultSet rs) throws SQLException {
        Borrowing b = new Borrowing();

        b.setId(rs.getInt("id"));
        b.setUserId(rs.getInt("user_id"));
        b.setBookId(rs.getInt("book_id"));
        b.setBorrowDate(rs.getDate("borrow_date"));
        b.setDueDate(rs.getDate("due_date"));
        b.setReturnDate(rs.getDate("return_date"));
        b.setStatus(rs.getString("status"));
        b.setNotes(rs.getString("notes"));
        b.setCreatedAt(rs.getTimestamp("created_at"));
        b.setUpdatedAt(rs.getTimestamp("updated_at"));

        // Columns from joined tables
        b.setBookTitle(rs.getString("book_title"));
        b.setBookAuthor(rs.getString("book_author"));

        return b;
    }
}
