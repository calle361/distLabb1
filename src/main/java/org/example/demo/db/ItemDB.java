package org.example.demo.db;

import org.example.demo.bo.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemDB {

    // Method to retrieve all items from the database
    public static Collection<Item> getItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();  // Ensure DBManager.getConnection() is working and doesn't return null
            if (con == null) {
                throw new SQLException("Unable to establish a database connection.");
            }

            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM products");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");

                // Create an Item object and add it to the collection
                Item item = new Item(id, name, description, price, stock);
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching items from the database.", e);
        } finally {
            // Ensure that resources are closed properly
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return items;
    }

    public static Item getItem(int idToSearch) throws SQLException {
        Item item = null;
        Connection con = null;
        PreparedStatement ps = null;
        //Statement st = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();  // Ensure DBManager.getConnection() is working and doesn't return null
            if (con == null) {
                throw new SQLException("Unable to establish a database connection.");
            }
            // Prepare the SQL query with a parameter placeholder
            String sql = "SELECT * FROM products WHERE id = ?";
            ps = con.prepareStatement(sql);

            // Set the value for the placeholder
            ps.setInt(1, idToSearch);
            // Execute the query
            rs = ps.executeQuery();
            // Process the result
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");

                // Create an Item object based on the retrieved data
                item = new Item(id, name, description, price, stock);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching items from the database.", e);
        } finally {
            // Ensure that resources are closed properly
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
        return item;
    }
}
