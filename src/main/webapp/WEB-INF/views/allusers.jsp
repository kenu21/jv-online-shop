<jsp:useBean id="users" scope="request" type="java.util.List<mate.academy.internetshop.model.User>"/>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Users</title>
</head>
<body>
Users:
<table border="1">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Login</th>
    </tr>
    <c:forEach var = "user" items = "${users}">
        <tr>
            <td>
                <c:out value="${user.id}" />
            </td>
            <td>
                <c:out value="${user.name}" />
            </td>
            <td>
                <c:out value="${user.login}" />
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
