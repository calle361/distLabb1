package org.example.demo.db;

import org.example.demo.bo.models.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The ItemDB class provides static methods to interact with the database for CRUD operations
 * related to the items (products) in the inventory. It includes methods to fetch, update,
 * and insert item data.
 */
public class ItemDB {

    /**
     * Retrieves all items from the database.
     *
     * @param con The active {@link Connection} to the database.
     * @return A collection of {@link Item} objects representing all items in the database.
     * @throws SQLException If there is an error fetching items from the database.
     */
    public static Collection<Item> getItems(Connection con) throws SQLException {
        List<Item> items = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;

        try {
            if (con != null && !con.isClosed()) {
                System.out.println("Connection is open in getItems.");
            }

            st = con.createStatement();
            rs = st.executeQuery("SELECT id, name, description, price, stock, categoryid FROM products");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");
                int categoryId = rs.getInt("categoryid");

                Item item = new Item(id, name, description, price, stock, categoryId);
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching items from the database.", e);
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
        }

        return items;
    }

    /**
     * Retrieves a single item from the database based on the provided item ID.
     *
     * @param connection The active {@link Connection} to the database.
     * @param idToSearch The ID of the item to search for.
     * @return An {@link Item} object representing the item, or {@code null} if the item is not found.
     * @throws SQLException If there is an error fetching the item from the database.
     */
    public static Item getItem(Connection connection, int idToSearch) throws SQLException {
        Item item = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            if (connection == null) {
                throw new SQLException("Unable to establish a database connection.");
            }

            String sql = "SELECT id, name, description, price, stock, categoryid FROM products WHERE id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idToSearch);
            rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");
                int categoryId = rs.getInt("categoryid");

                item = new Item(id, name, description, price, stock, categoryId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching the item from the database.", e);
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return item;
    }

    /**
     * Retrieves the name of an item based on the provided item ID.
     *
     * @param connection The active {@link Connection} to the database.
     * @param id The ID of the item to search for.
     * @return The name of the item.
     * @throws SQLException If there is an error fetching the item name from the database.
     */
    public static String getItemName(Connection connection, int id) throws SQLException {
        Item item = getItem(connection, id);
        return item.getName();
    }

    /**
     * Updates the stock for a specific item in the database.
     *
     * @param connection The active {@link Connection} to the database.
     * @param itemId The ID of the item to update.
     * @param newStock The new stock value to set.
     * @throws SQLException If there is an error updating the stock or if the connection is null/closed.
     */
    public static void updateStock(Connection connection, int itemId, int newStock) throws SQLException {
        System.out.println("Inside updateStock in ItemDB");
        if (connection == null) {
            System.out.println("Connection is null");
            throw new SQLException("Database connection is null");
        }
        if (connection.isClosed()) {
            System.out.println("Connection is closed");
            throw new SQLException("Database connection is closed");
        }

        String query = "UPDATE products SET stock = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, newStock);
            ps.setInt(2, itemId);
            int rowsUpdated = ps.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
        }
    }

    /**
     * Adds a new item to the database.
     *
     * @param connection The active {@link Connection} to the database.
     * @param name The name of the item.
     * @param description The description of the item.
     * @param price The price of the item.
     * @param stock The stock quantity of the item.
     * @param categoryId The category ID to associate the item with.
     * @throws SQLException If there is an error inserting the new item into the database.
     */
    public static void addItem(Connection connection, String name, String description, double price, int stock, int categoryId) throws SQLException {
        String query = "INSERT INTO products (name, description, price, stock, categoryid) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDouble(3, price);
            ps.setInt(4, stock);
            ps.setInt(5, categoryId);
            ps.executeUpdate();
        }
    }

    /**
     * Updates an item's name and category ID in the database.
     *
     * @param connection The active {@link Connection} to the database.
     * @param itemId The ID of the item to update.
     * @param newName The new name to assign to the item.
     * @param categoryId The new category ID to associate with the item.
     * @throws SQLException If there is an error updating the item in the database.
     */
    public static void updateItem(Connection connection, int itemId, String newName, int categoryId) throws SQLException {
        String query = "UPDATE products SET name = ?, categoryid = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newName);
            ps.setInt(2, categoryId);
            ps.setInt(3, itemId);
            ps.executeUpdate();
        }
    }
}
