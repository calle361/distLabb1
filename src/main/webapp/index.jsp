<%@ page import="org.example.demo.db.UserDB" %>
<%@ page import="org.example.demo.db.DBManager" %>
<%@ page import="org.example.demo.bo.models.User" %>
<%@ page import="org.example.demo.bo.PermissionLevel" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.Collection" %>
<%@ page import="org.example.demo.ui.facades.ItemInfo" %>
<%@ page import="org.example.demo.bo.handlers.ItemHandler" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Store</title>
</head>
<body>

<%!
    int amountInShoppingCart = 0;
%>

<%
    String sessionUsername = (String) session.getAttribute("username");
    PermissionLevel permissionLevel = (PermissionLevel) session.getAttribute("permissionLevel");

    if (sessionUsername == null) {
        String username = null;
        String password = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                } else if ("password".equals(cookie.getName())) {
                    password = cookie.getValue();
                }
            }
        }

        if (username != null && password != null) {
            try (Connection connection = DBManager.getConnection()) {
                if (connection != null) {
                    User user = UserDB.login(connection, username, password);
                    if (user != null) {
                        session.setAttribute("username", username);
                        session.setAttribute("permissionLevel", user.getPermissionLevel());
                        sessionUsername = username;
                        permissionLevel = user.getPermissionLevel();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
%>

<h1>Welcome to our store</h1>
<p>The time on the server is <%= new java.util.Date() %></p>
<br/>

<% if (sessionUsername != null) { %>
<p>Welcome, <%= sessionUsername %>!</p>
<% } else { %>
<a href="login.jsp">Login</a><br>
<a href="register.jsp">Register</a><br>
<% } %>


<% if (permissionLevel == PermissionLevel.Admin) { %>

<a href="admin.jsp">Manage Users</a><br>
<% } else if (permissionLevel == PermissionLevel.Worker) { %>

<a href="warehouse.jsp">Warehouse</a><br>
<% } %>

<% if (permissionLevel == PermissionLevel.Customer) { %>
<%
    List<String> cart = (List<String>) session.getAttribute("cart");
    if (cart != null) {
        amountInShoppingCart = cart.size();
    }else {
        amountInShoppingCart=0;
    }
%>
<a href="shoppingCart.jsp">Kundvagn (<%= amountInShoppingCart %>)</a><br>
<% } %>

<hr>

<%
    Collection<ItemInfo> items = ItemHandler.getAllItems();
    Iterator<ItemInfo> it = items.iterator();
%>
<table>
    <tr>
        <th>Item</th>
        <th>Stock</th>
    </tr>
    <%
        while (it.hasNext()) {
            ItemInfo item = it.next();
    %>
    <tr>
        <td><%= item.getName() %></td>
        <td><%= item.getAmount() %></td>
        <% if (sessionUsername != null && permissionLevel == PermissionLevel.Customer) { %>
        <td>

            <form method="post" action="${pageContext.request.contextPath}/items" style="display: inline;">
                <input type="hidden" name="itemId" value="<%= item.getId() %>">
                <button type="submit">Add to cart</button>
            </form>
        </td>
        <% } %>
    </tr>
    <%
        }
    %>
</table>

<% if (sessionUsername != null) { %>
<br>
<a href="user-servlet?action=logout">Logout</a><br>

<a href="${pageContext.request.contextPath}/order">pack orders</a>
<% } %>

</body>
</html>
