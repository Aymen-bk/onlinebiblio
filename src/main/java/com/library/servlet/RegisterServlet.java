package com.library.servlet;

import com.library.dao.UserDAO;
import com.library.model.User;
import com.library.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        
        // Validation
        if (username == null || username.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty()) {
            response.sendRedirect("register.jsp?error=Please fill in all fields");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            response.sendRedirect("register.jsp?error=Passwords do not match");
            return;
        }
        
        if (password.length() < 6) {
            response.sendRedirect("register.jsp?error=Password must be at least 6 characters");
            return;
        }
        
        // Check if username or email already exists
        if (userDAO.findByUsername(username) != null) {
            response.sendRedirect("register.jsp?error=Username already exists");
            return;
        }
        
        if (userDAO.findByEmail(email) != null) {
            response.sendRedirect("register.jsp?error=Email already exists");
            return;
        }
        
        // Create new user
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(PasswordUtil.hashPassword(password));
        user.setFullName(fullName);
        user.setRole("USER");
        
        if (userDAO.create(user)) {
            response.sendRedirect("login.jsp?success=Registration successful! Please login.");
        } else {
            response.sendRedirect("register.jsp?error=Registration failed. Please try again.");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("register.jsp");
    }
}
