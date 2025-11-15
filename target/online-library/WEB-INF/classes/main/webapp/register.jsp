<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Public Online Library</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/includes/navbar.jsp" />
    
    <div class="auth-container">
        <div class="auth-card">
            <h2>Create Your Account</h2>
            
            <c:if test="${not empty param.error}">
                <div class="alert alert-error">${param.error}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/register" method="post">
                <div class="form-group">
                    <label for="fullName" class="form-label">Full Name</label>
                    <input type="text" id="fullName" name="fullName" class="form-control" required>
                </div>
                
                <div class="form-group">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" id="username" name="username" class="form-control" required>
                </div>
                
                <div class="form-group">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" id="email" name="email" class="form-control" required>
                </div>
                
                <div class="form-group">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" id="password" name="password" class="form-control" required minlength="6">
                </div>
                
                <div class="form-group">
                    <label for="confirmPassword" class="form-label">Confirm Password</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required minlength="6">
                </div>
                
                <button type="submit" class="btn btn-primary" style="width: 100%;">Register</button>
            </form>
            
            <p style="text-align: center; margin-top: 1.5rem; color: var(--text-secondary);">
                Already have an account? 
                <a href="${pageContext.request.contextPath}/login.jsp" style="color: var(--primary-color); text-decoration: none;">Login here</a>
            </p>
        </div>
    </div>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
