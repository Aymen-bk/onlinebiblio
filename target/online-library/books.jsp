<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Browse Books - Public Online Library</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/includes/navbar.jsp" />
    
    <div class="container">
        <h1 style="margin-bottom: 2rem;">Browse Books</h1>
        
        <c:if test="${not empty param.error}">
            <div class="alert alert-error">${param.error}</div>
        </c:if>
        
        <!-- Search Bar -->
        <div class="search-container">
            <form action="${pageContext.request.contextPath}/books" method="get" class="search-form">
                <input type="text" name="search" class="search-input" placeholder="Search by title, author, ISBN, or category..." value="${searchQuery}">
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
        </div>
        
        <!-- Categories -->
        <div class="categories">
            <a href="${pageContext.request.contextPath}/books" class="category-btn ${empty selectedCategory ? 'active' : ''}">All Books</a>
            <c:forEach var="category" items="${categories}">
                <a href="${pageContext.request.contextPath}/books?category=${category}" 
                   class="category-btn ${selectedCategory eq category ? 'active' : ''}">${category}</a>
            </c:forEach>
        </div>
        
        <!-- Books Grid -->
        <c:choose>
            <c:when test="${empty books}">
                <div style="text-align: center; padding: 3rem; color: var(--text-secondary);">
                    <h3>No books found</h3>
                    <p>Try adjusting your search or browse all books</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="grid">
                    <c:forEach var="book" items="${books}">
                        <div class="card">
                            <img src="${book.coverImage}" alt="${book.title}" class="card-img">
                            <div class="card-body">
                                <h3 class="card-title">${book.title}</h3>
                                <p class="card-text">by ${book.author}</p>
                                <p class="card-text">
                                    <span class="badge ${book.availableQuantity > 0 ? 'badge-success' : 'badge-danger'}">
                                        ${book.availableQuantity > 0 ? 'Available' : 'Not Available'}
                                    </span>
                                    <span class="badge badge-info">${book.category}</span>
                                </p>
                                <a href="${pageContext.request.contextPath}/book-detail?id=${book.id}" class="btn btn-primary btn-sm">View Details</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
