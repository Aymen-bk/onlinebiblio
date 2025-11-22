package com.library.servlet;

import com.library.dao.BookDAO;
import com.library.dao.BorrowingDAO;
import com.library.model.Book;
import com.library.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class BookDetailServlet extends HttpServlet {

    private transient BookDAO bookDAO;
    private transient BorrowingDAO borrowingDAO;

    @Override
    public void init() throws ServletException {
        bookDAO = new BookDAO();
        borrowingDAO = new BorrowingDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookIdParam = request.getParameter("id");

        // Redirect if no id provided
        if (bookIdParam == null || bookIdParam.trim().isEmpty()) {
            response.sendRedirect("books");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("books?error=Invalid book ID");
            return;
        }

        Book book = bookDAO.findById(bookId);

        // Redirect if book not found
        if (book == null) {
            response.sendRedirect("books?error=Book not found");
            return;
        }

        // Check if user has active borrowing for this book
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                boolean hasActiveBorrowing = borrowingDAO.hasActiveBorrowing(user.getId(), bookId);
                request.setAttribute("hasActiveBorrowing", hasActiveBorrowing);
            }
        }

        // Forward to book detail page
        request.setAttribute("book", book);
        request.getRequestDispatcher("/book-detail.jsp").forward(request, response);
    }
}
