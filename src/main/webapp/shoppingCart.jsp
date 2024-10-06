<%@ page import="java.util.List" %>
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

        List<Integer> cart = (List<Integer>) session.getAttribute("cart");


        if (cart != null && !cart.isEmpty()) {
            for (int itemId : cart) {

                ItemInfo item = ItemHandler.getItemById(itemId);
                if (item != null) {
    %>
    <li><b>Item Name:</b> <%= item.getName() %> | <b>Amount:</b> <%= item.getAmount() %></li>
    <%
            }
        }
    %>
</ul>


<form action="<%= request.getContextPath() %>/checkout" method="post">
    <%

        for (int itemId : cart) {
    %>
    <input type="hidden" name="itemIds" value="<%= itemId %>">
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