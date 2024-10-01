<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h2>Login</h2>
<form method="post" action="user-servlet?action=login">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required>
    <br><br>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required>
    <input type="checkbox" id="rememberMe" name="rememberMe"> Remember me<br><br>
    <input type="submit" value="Login">
</form>
</body>
</html>
