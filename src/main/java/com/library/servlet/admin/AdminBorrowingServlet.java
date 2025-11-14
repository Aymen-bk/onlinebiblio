package com.library.servlet.admin;

import com.library.dao.BorrowingDAO;
import com.library.model.Borrowing;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AdminBorrowingServlet extends HttpServlet {
    private BorrowingDAO borrowingDAO;
    
    @Override
    public void init() throws ServletException {
        borrowingDAO = new BorrowingDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Update overdue status
        borrowingDAO.updateOverdueStatus();
        
        List<Borrowing> borrowings = borrowingDAO.findAll();
        request.setAttribute("borrowings", borrowings);
        request.getRequestDispatcher("/admin/borrowings.jsp").forward(request, response);
    }
}
