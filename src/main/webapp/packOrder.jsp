<%@ page import="org.example.demo.bo.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.demo.bo.OrderItem" %>
<%@ page import="org.example.demo.bo.OrderHandler" %><%--
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
            List<Order> orders = (List<Order>) request.getAttribute("orders");
            if (orders != null) {
                for (Order order : orders) {
        %>
        <tr>
            <td><%= order.getOid() %></td>
            <td><%= order.getDate() %></td>
            <td><%= order.getPrice() %></td>
            <td>
                <!-- Form to view items of the specific order -->
                <form method="get" action="${pageContext.request.contextPath}/orderItems">
                    <input type="hidden" name="orderId" value="<%= order.getOid() %>">
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
