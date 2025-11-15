<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Books - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/includes/navbar.jsp" />
    
    <div class="container">
        <h1 style="margin-bottom: 2rem;">Manage Books</h1>
        
        <c:if test="${not empty param.success}">
            <div class="alert alert-success">${param.success}</div>
        </c:if>
        
        <c:if test="${not empty param.error}">
            <div class="alert alert-error">${param.error}</div>
        </c:if>
        
        <div style="margin-bottom: 2rem;">
            <a href="${pageContext.request.contextPath}/admin/book-form.jsp" class="btn btn-primary">Add New Book</a>
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-outline">Back to Dashboard</a>
        </div>
        
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>ISBN</th>
                    <th>Category</th>
                    <th>Quantity</th>
                    <th>Available</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="book" items="${books}">
                    <tr>
                        <td>${book.id}</td>
                        <td>${book.title}</td>
                        <td>${book.author}</td>
                        <td>${book.isbn}</td>
                        <td>${book.category}</td>
                        <td>${book.quantity}</td>
                        <td>${book.availableQuantity}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/books?action=edit&id=${book.id}" class="btn btn-primary btn-sm">Edit</a>
                            <a href="${pageContext.request.contextPath}/admin/books?action=delete&id=${book.id}" 
                               class="btn btn-danger btn-sm" 
                               onclick="return confirm('Are you sure you want to delete this book?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
