<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>New Reservation</title>
</head>
<body>
<h2>Make Reservation</h2>

<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>

<form method="post" action="<c:url value='/reservations/new'/>">
    Name: <input name="customerName" required><br>

    Workspace:
    <select name="workspaceId" required>
        <c:forEach var="w" items="${workspaces}">
            <option value="${w.id}" <c:if test="${selectedId == w.id}">selected</c:if>>
                    ${w.id} - ${w.type} (${w.available ? 'free' : 'busy'})
            </option>
        </c:forEach>
    </select><br>

    Date: <input name="resDate" type="date" required><br>
    Start: <input name="startTime" type="time" required><br>
    End: <input name="endTime" type="time" required><br>

    <button type="submit">Reserve</button>
</form>

<p><a href="<c:url value='/workspaces'/>">Back to list</a></p>
</body>
</html>
