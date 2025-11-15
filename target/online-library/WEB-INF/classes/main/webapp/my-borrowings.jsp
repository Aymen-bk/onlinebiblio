<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Borrowings - Public Online Library</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/includes/navbar.jsp" />
    
    <div class="container">
        <h1 style="margin-bottom: 2rem;">My Borrowings</h1>
        
        <c:if test="${not empty param.success}">
            <div class="alert alert-success">${param.success}</div>
        </c:if>
        
        <c:if test="${not empty param.error}">
            <div class="alert alert-error">${param.error}</div>
        </c:if>
        
        <c:choose>
            <c:when test="${empty borrowings}">
                <div style="text-align: center; padding: 3rem; color: var(--text-secondary);">
                    <h3>No borrowings yet</h3>
                    <p>Start browsing books and borrow your favorites!</p>
                    <a href="${pageContext.request.contextPath}/books" class="btn btn-primary" style="margin-top: 1rem;">Browse Books</a>
                </div>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <thead>
                        <tr>
                            <th>Book Title</th>
                            <th>Author</th>
                            <th>Borrow Date</th>
                            <th>Due Date</th>
                            <th>Return Date</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="borrowing" items="${borrowings}">
                            <tr>
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
                                <td>
                                    <c:if test="${borrowing.status eq 'BORROWED' or borrowing.status eq 'OVERDUE'}">
                                        <form action="${pageContext.request.contextPath}/return" method="post" style="display: inline;">
                                            <input type="hidden" name="borrowingId" value="${borrowing.id}">
                                            <button type="submit" class="btn btn-success btn-sm">Return Book</button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
