<%@ page import="org.example.demo.bo.Item" %>
<%@ page import="org.example.demo.db.ItemDB" %>
<%@ page import="java.util.Collection" %>

<h1>Warehouse</h1>

<%
  Collection<Item> items = ItemDB.getItems();
%>


<table>
  <tr>
    <th>Item</th>
    <th>Stock</th>
    <th>Update Stock</th>
  </tr>

  <% for (Item item : items) { %>
  <tr>
    <td><%= item.getName() %></td>
    <td><%= item.getAmount() %></td>
    <td>
      <form method="post" action="user-servlet">
        <input type="hidden" name="action" value="updateStock">
        <input type="hidden" name="id" value="<%= item.getId() %>">
        <input type="number" name="newStock" min="0" value="<%= item.getAmount() %>">
        <button type="submit">Update</button>
      </form>
    </td>
  </tr>
  <% } %>
</table>

<hr>


<h2>Add New Item</h2>
<form method="post" action="user-servlet">
  <input type="hidden" name="action" value="addItem">

  <label for="name">Item Name:</label>
  <input type="text" id="name" name="name" required><br><br>

  <label for="description">Description:</label>
  <input type="text" id="description" name="description" required><br><br>

  <label for="price">Price:</label>
  <input type="text" id="price" name="price" required><br><br>

  <label for="stock">Stock:</label>
  <input type="number" id="stock" name="stock" required min="0"><br><br>

  <button type="submit">Add Item</button>
</form>
