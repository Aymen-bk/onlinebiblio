<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${book.title} - Public Online Library</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/includes/navbar.jsp" />
    
    <div class="container">
        <c:if test="${not empty param.error}">
            <div class="alert alert-error">${param.error}</div>
        </c:if>
        
        <div class="book-detail">
            <div>
                <img src="${book.coverImage}" alt="${book.title}" class="book-detail-img">
            </div>
            
            <div class="book-info">
                <h1>${book.title}</h1>
                <h3 style="color: var(--text-secondary); margin-bottom: 1.5rem;">by ${book.author}</h3>
                
                <div class="book-meta">
                    <div class="book-meta-item">
                        <span class="book-meta-label">ISBN:</span>
                        <span>${book.isbn}</span>
                    </div>
                    <div class="book-meta-item">
                        <span class="book-meta-label">Category:</span>
                        <span>${book.category}</span>
                    </div>
                    <div class="book-meta-item">
                        <span class="book-meta-label">Publisher:</span>
                        <span>${book.publisher}</span>
                    </div>
                    <div class="book-meta-item">
                        <span class="book-meta-label">Published Year:</span>
                        <span>${book.publishedYear}</span>
                    </div>
                    <div class="book-meta-item">
                        <span class="book-meta-label">Pages:</span>
                        <span>${book.pages}</span>
                    </div>
                    <div class="book-meta-item">
                        <span class="book-meta-label">Language:</span>
                        <span>${book.language}</span>
                    </div>
                    <div class="book-meta-item">
                        <span class="book-meta-label">Availability:</span>
                        <span class="badge ${book.availableQuantity > 0 ? 'badge-success' : 'badge-danger'}">
                            ${book.availableQuantity} of ${book.quantity} available
                        </span>
                    </div>
                </div>
                
                <h3>Description</h3>
                <p style="color: var(--text-secondary); line-height: 1.8; margin-bottom: 2rem;">
                    ${book.description}
                </p>
                
                <c:choose>
                    <c:when test="${empty sessionScope.user}">
                        <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-primary">Login to Borrow</a>
                    </c:when>
                    <c:when test="${hasActiveBorrowing}">
                        <button class="btn btn-secondary" disabled>Already Borrowed</button>
                    </c:when>
                    <c:when test="${book.availableQuantity > 0}">
                        <form action="${pageContext.request.contextPath}/borrow" method="post" style="display: inline;">
                            <input type="hidden" name="bookId" value="${book.id}">
                            <button type="submit" class="btn btn-primary">Borrow This Book</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-secondary" disabled>Not Available</button>
                    </c:otherwise>
                </c:choose>
                
                <a href="${pageContext.request.contextPath}/books" class="btn btn-outline" style="margin-left: 1rem;">Back to Books</a>
            </div>
        </div>
    </div>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
