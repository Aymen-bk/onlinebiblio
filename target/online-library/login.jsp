<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Public Online Library</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/includes/navbar.jsp" />
    
    <div class="auth-container">
        <div class="auth-card">
            <h2>Login to Your Account</h2>
            
            <c:if test="${not empty param.success}">
                <div class="alert alert-success">${param.success}</div>
            </c:if>
            
            <c:if test="${not empty param.error}">
                <div class="alert alert-error">${param.error}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" id="username" name="username" class="form-control" required>
                </div>
                
                <div class="form-group">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" id="password" name="password" class="form-control" required>
                </div>
                
                <button type="submit" class="btn btn-primary" style="width: 100%;">Login</button>
            </form>
            
            <p style="text-align: center; margin-top: 1.5rem; color: var(--text-secondary);">
                Don't have an account? 
                <a href="${pageContext.request.contextPath}/register.jsp" style="color: var(--primary-color); text-decoration: none;">Register here</a>
            </p>
        </div>
    </div>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
