package com.library.servlet;

import com.library.dao.BorrowingDAO;
import com.library.model.Borrowing;
import com.library.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class MyBorrowingsServlet extends HttpServlet {
    private BorrowingDAO borrowingDAO;
    
    @Override
    public void init() throws ServletException {
        borrowingDAO = new BorrowingDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        // Update overdue status
        borrowingDAO.updateOverdueStatus();
        
        List<Borrowing> borrowings = borrowingDAO.findByUserId(user.getId());
        
        request.setAttribute("borrowings", borrowings);
        request.getRequestDispatcher("/my-borrowings.jsp").forward(request, response);
    }
}
