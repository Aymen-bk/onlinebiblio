package com.library.servlet.admin;

import com.library.dao.BookDAO;
import com.library.dao.BorrowingDAO;
import com.library.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminDashboardServlet extends HttpServlet {
    private BookDAO bookDAO;
    private UserDAO userDAO;
    private BorrowingDAO borrowingDAO;
    
    @Override
    public void init() throws ServletException {
        bookDAO = new BookDAO();
        userDAO = new UserDAO();
        borrowingDAO = new BorrowingDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Update overdue status
        borrowingDAO.updateOverdueStatus();
        
        // Get statistics
        int totalBooks = bookDAO.getTotalBooks();
        int availableBooks = bookDAO.getAvailableBooks();
        int totalUsers = userDAO.getTotalUsers();
        int activeBorrowings = borrowingDAO.getActiveBorrowingsCount();
        int overdueBorrowings = borrowingDAO.getOverdueBorrowingsCount();
        
        request.setAttribute("totalBooks", totalBooks);
        request.setAttribute("availableBooks", availableBooks);
        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("activeBorrowings", activeBorrowings);
        request.setAttribute("overdueBorrowings", overdueBorrowings);
        
        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}
