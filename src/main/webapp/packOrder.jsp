<%@ page import="org.example.demo.bo.models.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.demo.bo.models.OrderItem" %>
<%@ page import="org.example.demo.bo.handlers.OrderHandler" %>
<%@ page import="org.example.demo.ui.facades.OrderInfo" %><%--
  Created by IntelliJ IDEA.
  User: calle
  Date: 2024-10-04
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <h1>Pack orders</h1>
    <h2>Your Orders</h2>


    <table border="1">
        <tr>
            <th>Order ID</th>
            <th>Order Date</th>
            <th>Total Price</th>
            <th>View Items</th>
        </tr>

        <%
            List<OrderInfo> orders = (List<OrderInfo>) request.getAttribute("orders");
            if (orders != null) {
                for (OrderInfo order : orders) {
        %>
        <tr>
            <td><%= order.getOrderId() %></td>
            <td><%= order.getDate() %></td>
            <td><%= order.getPrice() %></td>
            <td>

                <form method="get" action="${pageContext.request.contextPath}/orderItems">
                    <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
                    <button type="submit">View Order</button>
                </form>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="4">You have no orders.</td>
        </tr>
        <% } %>
    </table>
</head>
<body>

</body>
</html>
