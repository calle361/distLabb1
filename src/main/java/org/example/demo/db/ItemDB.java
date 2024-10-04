package org.example.demo.db;

import org.example.demo.bo.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemDB {

    // Method to retrieve all items from the database
    public static Collection<Item> getItems(Connection con) throws SQLException {
        List<Item> items = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;

        try {
            if (con != null && !con.isClosed()) {
                System.out.println("con is open in get items itemdb");
            }

            st = con.createStatement();
            // Lägg till categoryId i din SQL-fråga
            rs = st.executeQuery("SELECT id, name, description, price, stock, categoryid FROM products");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");
                int categoryId = rs.getInt("categoryid");  // Hämta categoryId

                // Skapa ett Item-objekt och lägg till det i listan
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
                int categoryId = rs.getInt("categoryid");  // Hämta categoryId

                // Skapa ett Item-objekt baserat på hämtade data
                item = new Item(id, name, description, price, stock, categoryId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching items from the database.", e);
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return item;
    }


    public static void updateStock(Connection connection, int itemId, int newStock) throws SQLException {
        System.out.println("inne i updateStock i itemDB");
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
            System.out.println("inne i updateStock i itemDB i try");
            ps.setInt(1, newStock);
            ps.setInt(2, itemId);
            int rowsUpdated = ps.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);  // Kontrollera att uppdatering sker
        }
    }


    public static void addItem(Connection connection, String name, String description, double price, int stock, int categoryId) throws SQLException {
        String query = "INSERT INTO products (name, description, price, stock, categoryid) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDouble(3, price);
            ps.setInt(4, stock);
            ps.setInt(5, categoryId);  // Lägg till categoryId i INSERT

            ps.executeUpdate();
        }
    }

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
