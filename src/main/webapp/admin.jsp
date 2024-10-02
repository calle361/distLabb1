<%@ page import="org.example.demo.bo.PermissionLevel" %>
<%@ page import="org.example.demo.bo.User" %>

<%
    User foundUser = (User) request.getAttribute("foundUser");
    String message = (String) request.getAttribute("message");
    String searchPerformed = request.getParameter("searchPerformed"); // Kontrollera om en sökning har genomförts
%>

<h1>Manage User Roles</h1>

<% if (message != null) { %>
<p class="message"><%= message %></p>
<% } %>

<form method="post" action="user-servlet">
    <input type="hidden" name="action" value="searchUser">
    <input type="hidden" name="searchPerformed" value="true">
    <label for="searchUsername">Search for user by username:</label>
    <input type="text" id="searchUsername" name="searchUsername" required>
    <button type="submit">Search</button>
</form>

<% if ("true".equals(searchPerformed)) { %>
<% if (foundUser != null) { %>
<h2>User Found: <%= foundUser.getName() %></h2>
<p>Current Role: <%= foundUser.getPermissionLevel() %></p>

<form method="post" action="user-servlet">
    <input type="hidden" name="action" value="updateRole"> <!-- Action för att uppdatera roller -->
    <input type="hidden" name="username" value="<%= foundUser.getName() %>">

    <label for="role">Update Role:</label>
    <select name="role" id="role">
        <option value="Customer" <%= foundUser.getPermissionLevel().toString().equals("Customer") ? "selected" : "" %>>Customer</option>
        <option value="Admin" <%= foundUser.getPermissionLevel().toString().equals("Admin") ? "selected" : "" %>>Admin</option>
        <option value="Worker" <%= foundUser.getPermissionLevel().toString().equals("Worker") ? "selected" : "" %>>Worker</option>
    </select>

    <button type="submit">Update Role</button>
</form>

<% }
%>
<% } %>

</body>
</html>
