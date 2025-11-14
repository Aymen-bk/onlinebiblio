package com.library.servlet;

import com.library.dao.BookDAO;
import com.library.dao.BorrowingDAO;
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

public class ReturnServlet extends HttpServlet {
    private BookDAO bookDAO;
    private BorrowingDAO borrowingDAO;
    
    @Override
    public void init() throws ServletException {
        bookDAO = new BookDAO();
        borrowingDAO = new BorrowingDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp?error=Please login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        String borrowingIdParam = request.getParameter("borrowingId");
        
        if (borrowingIdParam == null || borrowingIdParam.trim().isEmpty()) {
            response.sendRedirect("my-borrowings?error=Invalid borrowing");
            return;
        }
        
        try {
            int borrowingId = Integer.parseInt(borrowingIdParam);
            Borrowing borrowing = borrowingDAO.findById(borrowingId);
            
            if (borrowing == null) {
                response.sendRedirect("my-borrowings?error=Borrowing not found");
                return;
            }
            
            // Verify that this borrowing belongs to the current user
            if (borrowing.getUserId() != user.getId()) {
                response.sendRedirect("my-borrowings?error=Access denied");
                return;
            }
            
            if (!"BORROWED".equals(borrowing.getStatus()) && !"OVERDUE".equals(borrowing.getStatus())) {
                response.sendRedirect("my-borrowings?error=Book already returned");
                return;
            }
            
            // Return the book
            Date returnDate = Date.valueOf(LocalDate.now());
            if (borrowingDAO.returnBook(borrowingId, returnDate) && 
                bookDAO.increaseAvailableQuantity(borrowing.getBookId())) {
                response.sendRedirect("my-borrowings?success=Book returned successfully");
            } else {
                response.sendRedirect("my-borrowings?error=Failed to return book");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("my-borrowings?error=Invalid borrowing ID");
        }
    }
}
