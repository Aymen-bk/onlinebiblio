<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty book ? 'Add' : 'Edit'} Book - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/includes/navbar.jsp" />
    
    <div class="container">
        <h1 style="margin-bottom: 2rem;">${empty book ? 'Add New' : 'Edit'} Book</h1>
        
        <div style="max-width: 800px; margin: 0 auto;">
            <form action="${pageContext.request.contextPath}/admin/books" method="post">
                <input type="hidden" name="action" value="${empty book ? 'create' : 'update'}">
                <c:if test="${not empty book}">
                    <input type="hidden" name="id" value="${book.id}">
                </c:if>
                
                <div class="form-group">
                    <label for="title" class="form-label">Title *</label>
                    <input type="text" id="title" name="title" class="form-control" value="${book.title}" required>
                </div>
                
                <div class="form-group">
                    <label for="author" class="form-label">Author *</label>
                    <input type="text" id="author" name="author" class="form-control" value="${book.author}" required>
                </div>
                
                <div class="form-group">
                    <label for="isbn" class="form-label">ISBN</label>
                    <input type="text" id="isbn" name="isbn" class="form-control" value="${book.isbn}">
                </div>
                
                <div class="form-group">
                    <label for="category" class="form-label">Category *</label>
                    <input type="text" id="category" name="category" class="form-control" value="${book.category}" required>
                </div>
                
                <div class="form-group">
                    <label for="description" class="form-label">Description</label>
                    <textarea id="description" name="description" class="form-control">${book.description}</textarea>
                </div>
                
                <div class="form-group">
                    <label for="publisher" class="form-label">Publisher</label>
                    <input type="text" id="publisher" name="publisher" class="form-control" value="${book.publisher}">
                </div>
                
                <div class="form-group">
                    <label for="publishedYear" class="form-label">Published Year</label>
                    <input type="number" id="publishedYear" name="publishedYear" class="form-control" value="${book.publishedYear}">
                </div>
                
                <div class="form-group">
                    <label for="pages" class="form-label">Pages</label>
                    <input type="number" id="pages" name="pages" class="form-control" value="${book.pages}">
                </div>
                
                <div class="form-group">
                    <label for="language" class="form-label">Language</label>
                    <input type="text" id="language" name="language" class="form-control" value="${empty book ? 'English' : book.language}">
                </div>
                
                <div class="form-group">
                    <label for="quantity" class="form-label">Total Quantity *</label>
                    <input type="number" id="quantity" name="quantity" class="form-control" value="${empty book ? 1 : book.quantity}" required min="1">
                </div>
                
                <div class="form-group">
                    <label for="availableQuantity" class="form-label">Available Quantity *</label>
                    <input type="number" id="availableQuantity" name="availableQuantity" class="form-control" value="${empty book ? 1 : book.availableQuantity}" required min="0">
                </div>
                
                <div class="form-group">
                    <label for="coverImage" class="form-label">Cover Image URL</label>
                    <input type="text" id="coverImage" name="coverImage" class="form-control" value="${book.coverImage}" placeholder="/placeholder.svg?height=400&width=300">
                </div>
                
                <div style="display: flex; gap: 1rem;">
                    <button type="submit" class="btn btn-primary">${empty book ? 'Add' : 'Update'} Book</button>
                    <a href="${pageContext.request.contextPath}/admin/books" class="btn btn-outline">Cancel</a>
                </div>
            </form>
        </div>
    </div>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
