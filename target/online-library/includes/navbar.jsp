<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar">
    <div class="navbar-container">
        <a href="${pageContext.request.contextPath}/index.jsp" class="navbar-brand">Public Library</a>
        
        <ul class="navbar-menu">
            <li><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/books">Books</a></li>
            
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <c:if test="${sessionScope.user.admin}">
                        <li><a href="${pageContext.request.contextPath}/admin/dashboard">Admin</a></li>
                    </c:if>
                    <c:if test="${not sessionScope.user.admin}">
                        <li><a href="${pageContext.request.contextPath}/my-borrowings">My Borrowings</a></li>
                    </c:if>
                    <li><span style="color: var(--text-secondary);">Welcome, ${sessionScope.user.fullName}</span></li>
                    <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageContext.request.contextPath}/login.jsp">Login</a></li>
                    <li><a href="${pageContext.request.contextPath}/register.jsp">Register</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>
