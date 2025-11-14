<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Borrowings - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/includes/navbar.jsp" />
    
    <div class="container">
        <h1 style="margin-bottom: 2rem;">Manage Borrowings</h1>
        
        <div style="margin-bottom: 2rem;">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-outline">Back to Dashboard</a>
        </div>
        
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>User</th>
                    <th>Book</th>
                    <th>Author</th>
                    <th>Borrow Date</th>
                    <th>Due Date</th>
                    <th>Return Date</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="borrowing" items="${borrowings}">
                    <tr>
                        <td>${borrowing.id}</td>
                        <td>${borrowing.userName}</td>
                        <td>${borrowing.bookTitle}</td>
                        <td>${borrowing.bookAuthor}</td>
                        <td><fmt:formatDate value="${borrowing.borrowDate}" pattern="MMM dd, yyyy" /></td>
                        <td><fmt:formatDate value="${borrowing.dueDate}" pattern="MMM dd, yyyy" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty borrowing.returnDate}">
                                    <fmt:formatDate value="${borrowing.returnDate}" pattern="MMM dd, yyyy" />
                                </c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${borrowing.status eq 'BORROWED'}">
                                    <span class="badge badge-info">Borrowed</span>
                                </c:when>
                                <c:when test="${borrowing.status eq 'RETURNED'}">
                                    <span class="badge badge-success">Returned</span>
                                </c:when>
                                <c:when test="${borrowing.status eq 'OVERDUE'}">
                                    <span class="badge badge-danger">Overdue</span>
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
