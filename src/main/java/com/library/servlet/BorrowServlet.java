package com.library.servlet;

import com.library.dao.BookDAO;
import com.library.dao.BorrowingDAO;
import com.library.model.Book;
import com.library.model.Borrowing;
import com.library.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class BorrowServlet extends HttpServlet {

    private BookDAO bookDAO;
    private BorrowingDAO borrowingDAO;

    @Override
    public void init() throws ServletException {
        bookDAO = new BookDAO();
        borrowingDAO = new BorrowingDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getLoggedInUser(request, response);
        if (user == null) return;

        Integer bookId = getBookId(request, response);
        if (bookId == null) return;

        borrowBook(request, response, user, bookId);
    }

    // ---------------- Helper methods ----------------

    private User getLoggedInUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp?error=Please login to borrow books");
            return null;
        }
        return (User) session.getAttribute("user");
    }

    private Integer getBookId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String param = request.getParameter("bookId");
        if (param == null || param.trim().isEmpty()) {
            response.sendRedirect("books?error=Invalid book");
            return null;
        }
        try {
            return Integer.parseInt(param);
        } catch (NumberFormatException e) {
            response.sendRedirect("books?error=Invalid book ID");
            return null;
        }
    }

    private void borrowBook(HttpServletRequest request, HttpServletResponse response, User user, int bookId) throws IOException {
        Book book = bookDAO.findById(bookId);
        if (book == null) {
            response.sendRedirect("books?error=Book not found");
            return;
        }

        if (!book.isAvailable()) {
            response.sendRedirect("book-detail?id=" + bookId + "&error=Book is not available");
            return;
        }

        if (borrowingDAO.hasActiveBorrowing(user.getId(), bookId)) {
            response.sendRedirect("book-detail?id=" + bookId + "&error=You already have this book borrowed");
            return;
        }

        Borrowing borrowing = new Borrowing();
        borrowing.setUserId(user.getId());
        borrowing.setBookId(bookId);
        borrowing.setBorrowDate(Date.valueOf(LocalDate.now()));
        borrowing.setDueDate(Date.valueOf(LocalDate.now().plusDays(14)));
        borrowing.setStatus("BORROWED");

        boolean success = borrowingDAO.create(borrowing) && bookDAO.decreaseAvailableQuantity(bookId);
        if (success) {
            response.sendRedirect("my-borrowings?success=Book borrowed successfully");
        } else {
            response.sendRedirect("book-detail?id=" + bookId + "&error=Failed to borrow book");
        }
    }
}
