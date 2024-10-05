<%@ page import="org.example.demo.bo.ItemHandler" %>
<%@ page import="org.example.demo.ui.facades.ItemInfo" %>
<%@ page import="org.example.demo.bo.Category" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.List" %>

<h1 style="display: inline-block;">Warehouse</h1>
<a href="index.jsp" style="display: inline-block; margin-left: 20px;">Home page</a>


<%
  // Hämta alla varor och kategorier från ItemHandler
  Collection<ItemInfo> items = ItemHandler.getAllItems();
  List<Category> categories = ItemHandler.getAllCategories();
%>

<!-- Update Stock Section -->
<h2>Update Stock</h2>
<table>
  <tr>
    <th>Item</th>
    <th>Stock</th>
    <th>Category</th>
    <th>Update Stock</th>
  </tr>

  <% for (ItemInfo item : items) { %>
  <tr>
    <td><%= item.getName() %></td>
    <td><%= item.getAmount() %></td>
    <td><%= item.getCategoryName() %></td> <!-- Visar kategori med namn -->
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

<!-- Add New Item Section -->
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

  <label for="category">Category:</label>
  <select id="category" name="category" required>
    <% for (Category category : categories) { %>
    <option value="<%= category.getId() %>"><%= category.getName() %></option> <!-- Lista alla kategorier -->
    <% } %>
  </select><br><br>

  <button type="submit">Add Item</button>
</form>

<hr>

<!-- Add New Category Section -->
<h2>Add New Category</h2>
<form method="post" action="user-servlet">
  <input type="hidden" name="action" value="addCategory">

  <label for="categoryName">Category Name:</label>
  <input type="text" id="categoryName" name="categoryName" required><br><br>

  <button type="submit">Add Category</button>
</form>

<hr>

<!-- Edit Existing Items Section -->
<h2>Edit Existing Items</h2>
<table>
  <tr>
    <th>Item Name</th>
    <th>Category</th>
    <th>Actions</th>
  </tr>

  <% for (ItemInfo item : items) { %>
  <tr>
    <td>
      <!-- Form för att redigera varans namn -->
      <form method="post" action="user-servlet">
        <input type="hidden" name="action" value="editItem">
        <input type="hidden" name="id" value="<%= item.getId() %>">
        <input type="text" name="name" value="<%= item.getName() %>">
    </td>
    <td>
      <!-- Välj en ny kategori för varan -->
      <select name="category">
        <% for (Category category : categories) { %>
        <option value="<%= category.getId() %>" <%= category.getName().equals(item.getCategoryName()) ? "selected" : "" %>><%= category.getName() %></option>
        <% } %>
      </select>
    </td>
    <td>
      <button type="submit">Update Item</button>
      </form>
    </td>
  </tr>
  <% } %>
</table>

<hr>

<!-- Edit Existing Categories Section -->
<h2>Edit Existing Categories</h2>
<table>
  <tr>
    <th>Category Name</th>
    <th>Actions</th>
  </tr>

  <% for (Category category : categories) { %>
  <tr>
    <td>
      <!-- Form för att redigera kategorins namn -->
      <form method="post" action="user-servlet">
        <input type="hidden" name="action" value="editCategory">
        <input type="hidden" name="categoryId" value="<%= category.getId() %>">
        <input type="text" name="newName" value="<%= category.getName() %>">
    </td>
    <td>
      <button type="submit">Update Category</button>
      </form>
    </td>
  </tr>
  <% } %>
</table>

<!-- Visar meddelande om något har redigerats -->
<% if (request.getAttribute("message") != null) { %>
<p><%= request.getAttribute("message") %></p>
<% } %>
