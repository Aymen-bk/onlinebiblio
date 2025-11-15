<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Page Not Found</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/includes/navbar.jsp" />
    
    <div class="container" style="text-align: center; padding: 4rem 2rem;">
        <h1 style="font-size: 4rem; color: var(--primary-color);">404</h1>
        <h2 style="margin-bottom: 1rem;">Page Not Found</h2>
        <p style="color: var(--text-secondary); margin-bottom: 2rem;">The page you're looking for doesn't exist.</p>
        <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">Go Home</a>
    </div>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
