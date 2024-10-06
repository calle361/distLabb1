<%@ page import="java.util.Map" %>
<%@ page import="org.example.demo.ui.facades.ItemInfo" %>
<%@ page import="org.example.demo.bo.handlers.ItemHandler" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shoppingcart</title>
</head>
<body>

<h1>Your Shopping Cart</h1>

<h2>Items in Cart:</h2>
<ul>
    <%

        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        if (cart != null && !cart.isEmpty()) {
            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                int itemId = entry.getKey();
                int selectedAmount = entry.getValue();

                ItemInfo item = ItemHandler.getItemById(itemId);
                if (item != null) {
    %>
    <li><b>Item Name:</b> <%= item.getName() %> | <b>Quantity:</b> <%= selectedAmount %></li>
    <%
            }
        }
    %>
</ul>

<form action="<%= request.getContextPath() %>/checkout" method="post">
    <%
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            int itemId = entry.getKey();
            int selectedAmount = entry.getValue();
    %>
    <input type="hidden" name="itemIds" value="<%= itemId %>">
    <input type="hidden" name="itemAmounts" value="<%= selectedAmount %>">
    <%
        }
    %>
    <button type="submit">Proceed to Checkout</button>
</form>

<%
} else {
%>
<li>No items in your shopping cart.</li>
<%
    }
%>

</body>
</html>
