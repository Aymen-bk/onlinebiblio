package com.library.servlet;

import com.library.dao.BookDAO;
import com.library.model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BookServlet extends HttpServlet {
    private BookDAO bookDAO;
    
    @Override
    public void init() throws ServletException {
        bookDAO = new BookDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String search = request.getParameter("search");
        String category = request.getParameter("category");
        
        List<Book> books;
        
        if (search != null && !search.trim().isEmpty()) {
            books = bookDAO.search(search);
            request.setAttribute("searchQuery", search);
        } else if (category != null && !category.trim().isEmpty()) {
            books = bookDAO.findByCategory(category);
            request.setAttribute("selectedCategory", category);
        } else {
            books = bookDAO.findAll();
        }
        
        List<String> categories = bookDAO.getAllCategories();
        
        request.setAttribute("books", books);
        request.setAttribute("categories", categories);
        
        request.getRequestDispatcher("/books.jsp").forward(request, response);
    }
}
