<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Public Online Library - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/includes/navbar.jsp" />
    
    <div class="hero">
        <h1>Welcome to Public Online Library</h1>
        <p>Discover thousands of books, borrow your favorites, and embark on endless reading adventures</p>
        <div style="display: flex; gap: 1rem; justify-content: center; margin-top: 2rem;">
            <a href="${pageContext.request.contextPath}/books" class="btn btn-primary">Browse Books</a>
            <c:if test="${empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-outline">Login</a>
            </c:if>
        </div>
    </div>
    
    <div class="container">
        <c:if test="${not empty param.success}">
            <div class="alert alert-success">${param.success}</div>
        </c:if>
        
        <c:if test="${not empty param.error}">
            <div class="alert alert-error">${param.error}</div>
        </c:if>
        
        <h2 style="text-align: center; margin-bottom: 2rem;">Featured Categories</h2>
        
        <div class="grid">
            <div class="card">
                <div class="card-body">
                    <h3 class="card-title">Fiction</h3>
                    <p class="card-text">Explore captivating stories and imaginative worlds</p>
                    <a href="${pageContext.request.contextPath}/books?category=Fiction" class="btn btn-primary btn-sm">Browse Fiction</a>
                </div>
            </div>
            
            <div class="card">
                <div class="card-body">
                    <h3 class="card-title">Science Fiction</h3>
                    <p class="card-text">Journey into the future and beyond</p>
                    <a href="${pageContext.request.contextPath}/books?category=Science Fiction" class="btn btn-primary btn-sm">Browse Sci-Fi</a>
                </div>
            </div>
            
            <div class="card">
                <div class="card-body">
                    <h3 class="card-title">Fantasy</h3>
                    <p class="card-text">Discover magical realms and epic adventures</p>
                    <a href="${pageContext.request.contextPath}/books?category=Fantasy" class="btn btn-primary btn-sm">Browse Fantasy</a>
                </div>
            </div>
            
            <div class="card">
                <div class="card-body">
                    <h3 class="card-title">Romance</h3>
                    <p class="card-text">Fall in love with heartwarming tales</p>
                    <a href="${pageContext.request.contextPath}/books?category=Romance" class="btn btn-primary btn-sm">Browse Romance</a>
                </div>
            </div>
        </div>
        
        <div style="text-align: center; margin-top: 3rem;">
            <h2 style="margin-bottom: 1rem;">Ready to Start Reading?</h2>
            <p style="color: var(--text-secondary); margin-bottom: 2rem;">Join our library today and get access to thousands of books</p>
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <a href="${pageContext.request.contextPath}/books" class="btn btn-primary">Browse All Books</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/register.jsp" class="btn btn-primary">Sign Up Now</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
