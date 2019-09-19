<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>All Items</title>
</head>
<body>
<a href="/internet_shop_war_exploded/servlet/bucket">Bucket</a>
<h1>Items</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Buy</th>
    </tr>
    <c:forEach var="items" items="${items}">
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
                <a href="/internet_shop_war_exploded/servlet/buy?items_id=${items.id}">Buy</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
