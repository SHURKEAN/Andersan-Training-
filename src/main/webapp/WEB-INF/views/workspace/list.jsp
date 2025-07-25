<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Workspaces</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
<h2>Workspaces</h2>

<p>
    <a href="<c:url value='/workspaces/add'/>">Add new workspace</a> |
    <a href="<c:url value='/'/>">Home</a>
</p>

<table border="1" cellpadding="5">
    <tr>
        <th>ID</th>
        <th>Type</th>
        <th>Available</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="w" items="${workspaces}">
        <tr>
            <td>${w.id}</td>
            <td>${w.type}</td>
            <td>${w.available}</td>
            <td>
                <c:if test="${w.available}">
                    <a href="<c:url value='/reservations/new?workspaceId=${w.id}'/>">Reserve</a>
                </c:if>
                <form method="post" action="<c:url value='/workspaces/${w.id}/delete'/>" style="display:inline">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
