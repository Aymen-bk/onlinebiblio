package com.library.servlet.admin;

import com.library.dao.BookDAO;
import com.library.model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AdminBookServlet extends HttpServlet {
    private BookDAO bookDAO;
    
    @Override
    public void init() throws ServletException {
        bookDAO = new BookDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("edit".equals(action)) {
            showEditForm(request, response);
        } else if ("delete".equals(action)) {
            deleteBook(request, response);
        } else {
            listBooks(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("create".equals(action)) {
            createBook(request, response);
        } else if ("update".equals(action)) {
            updateBook(request, response);
        }
    }
    
    private void listBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Book> books = bookDAO.findAll();
        request.setAttribute("books", books);
        request.getRequestDispatcher("/admin/books.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("id"));
        Book book = bookDAO.findById(bookId);
        request.setAttribute("book", book);
        request.getRequestDispatcher("/admin/book-form.jsp").forward(request, response);
    }
    
    private void createBook(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Book book = extractBookFromRequest(request);
        
        if (bookDAO.create(book)) {
            response.sendRedirect("books?success=Book created successfully");
        } else {
            response.sendRedirect("books?error=Failed to create book");
        }
    }
    
    private void updateBook(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Book book = extractBookFromRequest(request);
        book.setId(Integer.parseInt(request.getParameter("id")));
        
        if (bookDAO.update(book)) {
            response.sendRedirect("books?success=Book updated successfully");
        } else {
            response.sendRedirect("books?error=Failed to update book");
        }
    }
    
    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int bookId = Integer.parseInt(request.getParameter("id"));
        
        if (bookDAO.delete(bookId)) {
            response.sendRedirect("books?success=Book deleted successfully");
        } else {
            response.sendRedirect("books?error=Failed to delete book");
        }
    }
    
    private Book extractBookFromRequest(HttpServletRequest request) {
        Book book = new Book();
        book.setTitle(request.getParameter("title"));
        book.setAuthor(request.getParameter("author"));
        book.setIsbn(request.getParameter("isbn"));
        book.setCategory(request.getParameter("category"));
        book.setDescription(request.getParameter("description"));
        book.setPublisher(request.getParameter("publisher"));
        book.setPublishedYear(Integer.parseInt(request.getParameter("publishedYear")));
        book.setPages(Integer.parseInt(request.getParameter("pages")));
        book.setLanguage(request.getParameter("language"));
        book.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        book.setAvailableQuantity(Integer.parseInt(request.getParameter("availableQuantity")));
        book.setCoverImage(request.getParameter("coverImage"));
        return book;
    }
}
