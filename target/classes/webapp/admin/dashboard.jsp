<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Public Online Library</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/includes/navbar.jsp" />
    
    <div class="container">
        <h1 style="margin-bottom: 2rem;">Admin Dashboard</h1>
        
        <div class="stats-grid">
            <div class="stat-card">
                <h3>${totalBooks}</h3>
                <p>Total Books</p>
            </div>
            
            <div class="stat-card">
                <h3>${availableBooks}</h3>
                <p>Available Books</p>
            </div>
            
            <div class="stat-card">
                <h3>${totalUsers}</h3>
                <p>Total Users</p>
            </div>
            
            <div class="stat-card">
                <h3>${activeBorrowings}</h3>
                <p>Active Borrowings</p>
            </div>
            
            <div class="stat-card">
                <h3 style="color: var(--danger-color);">${overdueBorrowings}</h3>
                <p>Overdue Borrowings</p>
            </div>
        </div>
        
        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 2rem; margin-top: 3rem;">
            <div class="card">
                <div class="card-body">
                    <h3 class="card-title">Manage Books</h3>
                    <p class="card-text">Add, edit, or remove books from the library</p>
                    <a href="${pageContext.request.contextPath}/admin/books" class="btn btn-primary btn-sm">Manage Books</a>
                </div>
            </div>
            
            <div class="card">
                <div class="card-body">
                    <h3 class="card-title">Manage Users</h3>
                    <p class="card-text">View and manage user accounts</p>
                    <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-primary btn-sm">Manage Users</a>
                </div>
            </div>
            
            <div class="card">
                <div class="card-body">
                    <h3 class="card-title">Manage Borrowings</h3>
                    <p class="card-text">Track and manage all book borrowings</p>
                    <a href="${pageContext.request.contextPath}/admin/borrowings" class="btn btn-primary btn-sm">Manage Borrowings</a>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
