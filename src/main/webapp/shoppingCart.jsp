<%@ page import="java.util.List" %>
<%@ page import="org.example.demo.ui.ItemInfo" %>
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
        // Fetch cart items from session (this assumes "cart" is stored in session)
        //HttpSession session = request.getSession();
        List<Integer> cart = (List<Integer>) session.getAttribute("cart");

        // Ensure cart is not null
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
    } else {
    %>
    <li>No items in your shopping cart.</li>
    <%
        }
    %>
</ul>

</body>
</html>