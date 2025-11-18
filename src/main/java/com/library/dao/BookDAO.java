package com.library.dao;

import com.library.model.Book;
import com.library.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookDAO {
    private static final Logger logger = LoggerFactory.getLogger(BookDAO.class);

    // Columns used in extractBookFromResultSet
    private static final String BOOK_COLUMNS = """
        id, title, author, isbn, category, description,
        publisher, published_year, pages, language,
        quantity, available_quantity, cover_image,
        created_at, updated_at
    """;

    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT " + BOOK_COLUMNS + " FROM books ORDER BY title";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("Failed to fetch all books", e);
            return new ArrayList<>();
        }
        return books;
    }

    public Book findById(int id) {
        String sql = "SELECT " + BOOK_COLUMNS + " FROM books WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractBookFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to find book with ID: " + id, e);
        }
        return null;
    }

    public List<Book> search(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT " + BOOK_COLUMNS + " FROM books WHERE title LIKE ? OR author LIKE ? OR category LIKE ? OR isbn LIKE ? ORDER BY title";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(extractBookFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to search books with keyword: {}", keyword, e);
            return new ArrayList<>();
        }
        return books;
    }

    public List<Book> findByCategory(String category) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT " + BOOK_COLUMNS + " FROM books WHERE category = ? ORDER BY title";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(extractBookFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to find books by category: {}", category, e);
            return new ArrayList<>();
        }
        return books;
    }
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM books ORDER BY category";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            logger.error("Failed to load categories", e);
            return new ArrayList<>();
        }
        return categories;
    }
    
    public boolean create(Book book) {
        String sql = "INSERT INTO books (title, author, isbn, category, description, publisher, published_year, pages, language, quantity, available_quantity, cover_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setString(4, book.getCategory());
            stmt.setString(5, book.getDescription());
            stmt.setString(6, book.getPublisher());
            stmt.setInt(7, book.getPublishedYear());
            stmt.setInt(8, book.getPages());
            stmt.setString(9, book.getLanguage());
            stmt.setInt(10, book.getQuantity());
            stmt.setInt(11, book.getAvailableQuantity());
            stmt.setString(12, book.getCoverImage());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            logger.error("Failed to create book: {}", book.getTitle(), e);
            return false;
        }
        return false;
    }
    
    public boolean update(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, category = ?, description = ?, publisher = ?, published_year = ?, pages = ?, language = ?, quantity = ?, available_quantity = ?, cover_image = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setString(4, book.getCategory());
            stmt.setString(5, book.getDescription());
            stmt.setString(6, book.getPublisher());
            stmt.setInt(7, book.getPublishedYear());
            stmt.setInt(8, book.getPages());
            stmt.setString(9, book.getLanguage());
            stmt.setInt(10, book.getQuantity());
            stmt.setInt(11, book.getAvailableQuantity());
            stmt.setString(12, book.getCoverImage());
            stmt.setInt(13, book.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to update book ID: {}", book.getId(), e);
            return false;
        }
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to delete book ID: {}", id, e);
            return false;
        }
    }
    
    public boolean decreaseAvailableQuantity(int bookId) {
        String sql = "UPDATE books SET available_quantity = available_quantity - 1 WHERE id = ? AND available_quantity > 0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bookId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to decrease available quantity of book ID: {}", bookId, e);
            return false;
        }
    }
    
    public boolean increaseAvailableQuantity(int bookId) {
        String sql = "UPDATE books SET available_quantity = available_quantity + 1 WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bookId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to increase available quantity of book ID: {}", bookId, e);
            return false;
        }
    }
    
    public int getTotalBooks() {
        String sql = "SELECT COUNT(*) FROM books";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Failed to count total books", e);
        }
        return 0;
    }
    
    public int getAvailableBooks() {
        String sql = "SELECT COUNT(*) FROM books WHERE available_quantity > 0";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Failed to count available books", e);
        }
        return 0;
    }
    
    private Book extractBookFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setIsbn(rs.getString("isbn"));
        book.setCategory(rs.getString("category"));
        book.setDescription(rs.getString("description"));
        book.setPublisher(rs.getString("publisher"));
        book.setPublishedYear(rs.getInt("published_year"));
        book.setPages(rs.getInt("pages"));
        book.setLanguage(rs.getString("language"));
        book.setQuantity(rs.getInt("quantity"));
        book.setAvailableQuantity(rs.getInt("available_quantity"));
        book.setCoverImage(rs.getString("cover_image"));
        book.setCreatedAt(rs.getTimestamp("created_at"));
        book.setUpdatedAt(rs.getTimestamp("updated_at"));
        return book;
    }
}
