<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>All Items</title>
</head>
<body>
<a href="/internet_shop_war_exploded/servlet/bucket">Bucket</a>
<h1>Orders</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>UserId</th>
        <th>Items</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="orders" items="${orders}">
        <tr>
            <td>
                <c:out value="${orders.id}" />
            </td>
            <td>
                <c:out value="${orders.userId}" />
            </td>
            <td>
                <c:out value="${orders.items}" />
            </td>
            <td>
                <a href="/internet_shop_war_exploded/servlet/deleteorder?orders_id=${orders.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>