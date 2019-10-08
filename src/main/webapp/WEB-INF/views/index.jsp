<%--
  Created by IntelliJ IDEA.
  User: Iurii
  Date: 9/19/2019
  Time: 9:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/inject">Inject</a>
<a href="${pageContext.request.contextPath}/registration">Registration</a>
<a href="${pageContext.request.contextPath}/login">Login</a>
<a href="${pageContext.request.contextPath}/servlet/getallusers">Users</a>
<a href="${pageContext.request.contextPath}/servlet/getallitems">Items</a>
<a href="${pageContext.request.contextPath}/servlet/bucket">Bucket</a>
<a href="${pageContext.request.contextPath}/servlet/getallorders">Orders</a>
<a href="${pageContext.request.contextPath}/logout">Logout</a>
</body>
</html>
