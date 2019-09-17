<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Bucket</title>
</head>
<body>
<h1>Bucket</h1>

<a href="/internet_shop_war_exploded/servlet/completeorder">submit</a>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="items" items="${orders}">
        <tr>
            <td>
                <c:out value="${items.id}" />
            </td>
            <td>
                <c:out value="${items.name}" />
            </td>
            <td>
                <c:out value="${items.price}" />
            </td>
            <td>
                <a href="/internet_shop_war_exploded/servlet/delete?items_id=${items.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
