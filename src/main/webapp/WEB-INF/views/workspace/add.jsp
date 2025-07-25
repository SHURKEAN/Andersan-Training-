<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Workspace</title>
</head>
<body>
<h2>Add Workspace</h2>

<form method="post" action="<c:url value='/workspaces/add'/>">
    Type: <input type="text" name="type" required><br>
    Available: <input type="checkbox" name="available" checked><br>
    <button type="submit">Save</button>
</form>

<p><a href="<c:url value='/workspaces'/>">Back to list</a></p>
</body>
</html>
