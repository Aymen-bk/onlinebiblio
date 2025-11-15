<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Users - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/includes/navbar.jsp" />
    
    <div class="container">
        <h1 style="margin-bottom: 2rem;">Manage Users</h1>
        
        <c:if test="${not empty param.success}">
            <div class="alert alert-success">${param.success}</div>
        </c:if>
        
        <c:if test="${not empty param.error}">
            <div class="alert alert-error">${param.error}</div>
        </c:if>
        
        <div style="margin-bottom: 2rem;">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-outline">Back to Dashboard</a>
        </div>
        
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Created At</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.fullName}</td>
                        <td>${user.email}</td>
                        <td>
                            <span class="badge ${user.role eq 'ADMIN' ? 'badge-warning' : 'badge-info'}">
                                ${user.role}
                            </span>
                        </td>
                        <td><fmt:formatDate value="${user.createdAt}" pattern="MMM dd, yyyy" /></td>
                        <td>
                            <c:if test="${user.role ne 'ADMIN'}">
                                <a href="${pageContext.request.contextPath}/admin/users?action=delete&id=${user.id}" 
                                   class="btn btn-danger btn-sm" 
                                   onclick="return confirm('Are you sure you want to delete this user?')">Delete</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
