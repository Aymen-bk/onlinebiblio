package com.library.dao;

import com.library.model.Borrowing;
import com.library.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowingDAO {
    
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
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    borrowing.setId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Borrowing> findByUserId(int userId) {
        List<Borrowing> borrowings = new ArrayList<>();
        String sql = "SELECT b.*, bk.title as book_title, bk.author as book_author " +
                    "FROM borrowings b " +
                    "JOIN books bk ON b.book_id = bk.id " +
                    "WHERE b.user_id = ? " +
                    "ORDER BY b.borrow_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                borrowings.add(extractBorrowingFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowings;
    }
    
    public List<Borrowing> findAll() {
        List<Borrowing> borrowings = new ArrayList<>();
        String sql = "SELECT b.*, u.full_name as user_name, bk.title as book_title, bk.author as book_author " +
                    "FROM borrowings b " +
                    "JOIN users u ON b.user_id = u.id " +
                    "JOIN books bk ON b.book_id = bk.id " +
                    "ORDER BY b.borrow_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Borrowing borrowing = extractBorrowingFromResultSet(rs);
                borrowing.setUserName(rs.getString("user_name"));
                borrowings.add(borrowing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowings;
    }
    
    public Borrowing findById(int id) {
        String sql = "SELECT b.*, u.full_name as user_name, bk.title as book_title, bk.author as book_author " +
                    "FROM borrowings b " +
                    "JOIN users u ON b.user_id = u.id " +
                    "JOIN books bk ON b.book_id = bk.id " +
                    "WHERE b.id = ?";
        
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return false;
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return 0;
    }
    
    public boolean updateOverdueStatus() {
        String sql = "UPDATE borrowings SET status = 'OVERDUE' WHERE status = 'BORROWED' AND due_date < CURDATE()";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private Borrowing extractBorrowingFromResultSet(ResultSet rs) throws SQLException {
        Borrowing borrowing = new Borrowing();
        borrowing.setId(rs.getInt("id"));
        borrowing.setUserId(rs.getInt("user_id"));
        borrowing.setBookId(rs.getInt("book_id"));
        borrowing.setBorrowDate(rs.getDate("borrow_date"));
        borrowing.setDueDate(rs.getDate("due_date"));
        borrowing.setReturnDate(rs.getDate("return_date"));
        borrowing.setStatus(rs.getString("status"));
        borrowing.setNotes(rs.getString("notes"));
        borrowing.setCreatedAt(rs.getTimestamp("created_at"));
        borrowing.setUpdatedAt(rs.getTimestamp("updated_at"));
        borrowing.setBookTitle(rs.getString("book_title"));
        borrowing.setBookAuthor(rs.getString("book_author"));
        return borrowing;
    }
}
