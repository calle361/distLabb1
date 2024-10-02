<%@ page import="org.example.demo.db.UserDB" %>
<%@ page import="org.example.demo.db.DBManager" %>
<%@ page import="org.example.demo.bo.User" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.Collection" %>
<%@ page import="org.example.demo.ui.ItemInfo" %>
<%@ page import="org.example.demo.bo.ItemHandler" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>

<%!
    int amountInShoppingCart = 0;
%>

<%
    // Kontrollera om användaren redan är inloggad via session
    String sessionUsername = (String) session.getAttribute("username");
    if (sessionUsername == null) {
        String username = null;
        String password = null;

        // Kolla cookies för att logga in användaren automatiskt
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
            // Använd cookies för att automatiskt logga in användaren
            try (Connection connection = DBManager.getConnection()) {
                if (connection != null) {
                    User user = UserDB.login(connection, username, password);
                    if (user != null) {
                        session.setAttribute("username", username);
                        sessionUsername = username; // För att visa välkomstmeddelande
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
<a href="user-servlet?action=logout">Logout</a>
<% } else { %>
<a href="login.jsp">Login</a><br>
<a href="register.jsp">Register</a><br>
<% } %>


<%
    List<String> cart = (List<String>) session.getAttribute("cart");
    if (cart != null) {
        amountInShoppingCart = cart.size();
    }
%>

<a href="shoppingCart.jsp">Kundvagn (<%= amountInShoppingCart %>)</a><br>



<%
    Collection<ItemInfo> items = ItemHandler.getAllItems();
    Iterator<ItemInfo> it = items.iterator();
    while (it.hasNext()) {
        ItemInfo item = it.next();
%>
<%= item.getName() %>( <%= item.getAmount() %>)
<form method="post" action="${pageContext.request.contextPath}/items" style="display: inline;">
    <input type="hidden" name="itemId" value="<%=item.getId()%>">
    <button type="submit">add to cart</button>

</form><br>
<% } %>

<a href="login.jsp">login</a><br>
</body>
</html>
