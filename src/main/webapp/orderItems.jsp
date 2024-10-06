<%@ page import="org.example.demo.bo.models.OrderItem" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.demo.ui.facades.OrderItemInfo" %><%--
  Created by IntelliJ IDEA.
  User: calle
  Date: 2024-10-04
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Order Items</h1>
<%
    List<OrderItemInfo> orderItems = (List<OrderItemInfo>) request.getAttribute("orderItems");

    if (orderItems != null && !orderItems.isEmpty()) {
%>
<table border="1">
    <tr>
        <th>Item Name</th>
        <th>item ID</th>

    </tr>

    <%
        // Loop through each OrderItem and display its details
        for (OrderItemInfo item : orderItems) {
    %>
    <tr>
        <td><%= item.getItemname() %></td>
        <td><%= item.getItemid() %></td>
    </tr>
    <%
        }
    %>
</table>

<!-- Form to submit the order (which will call doPost on the servlet) -->
<form method="post" action="${pageContext.request.contextPath}/orderItems">
    <input type="hidden" name="orderId" value="<%= request.getParameter("orderId") %>">
    <button type="submit">Send Order</button>
</form>

<%
} else {
%>
<p>No items found for this order.</p>
<%
    }
%>
</body>
</html>
