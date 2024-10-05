<%@ page import="java.util.List" %>
<%@ page import="org.example.demo.ui.facades.ItemInfo" %>
<%@ page import="org.example.demo.bo.ItemHandler" %>
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
        // Fetch cart items from session
        List<Integer> cart = (List<Integer>) session.getAttribute("cart");

        // Ensure cart is not null and contains items
        if (cart != null && !cart.isEmpty()) {
            for (int itemId : cart) {
                // Fetch item details based on itemId
                ItemInfo item = ItemHandler.getItemById(itemId); // Assuming you have this method to get item by ID
                if (item != null) {
    %>
    <li><b>Item Name:</b> <%= item.getName() %> | <b>Amount:</b> <%= item.getAmount() %></li>
    <%
            }
        }
    %>
</ul>

<!-- Form to send itemIds to a POST request -->
<form action="<%= request.getContextPath() %>/checkout" method="post">
    <%
        // Include each itemId in a hidden input field
        for (int itemId : cart) {
    %>
    <input type="hidden" name="itemIds" value="<%= itemId %>">
    <%
        }
    %>
    <button type="submit">Proceed to Checkout</button>
</form>

<%
} else {  // This 'else' is tied to the 'if (cart != null && !cart.isEmpty())' condition above
%>
<li>No items in your shopping cart.</li>
<%
    }  // Closing the 'if-else' block
%>
</body>
</html>