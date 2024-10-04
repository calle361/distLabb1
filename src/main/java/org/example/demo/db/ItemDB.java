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
                    //Connection con = null;
                    Statement st = null;
                    ResultSet rs = null;

                    try {
                        if (con!=null&&!con.isClosed()){
                            System.out.println("con is open in get items itemdb");
                        }
                        //con = connection;  // Ensure DBManager.getConnection() is working and doesn't return null
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
            //if (con != null) con.close();
        }

        return items;
    }

    public static Item getItem(Connection connection,int idToSearch) throws SQLException {
        Item item = null;
        Connection con = null;
        PreparedStatement ps = null;
        //Statement st = null;
        ResultSet rs = null;

        try {
            con = connection;  // Ensure DBManager.getConnection() is working and doesn't return null
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
            //if (con != null) con.close();
        }
        return item;
    }
    public static String getItemName(Connection connection, int id) throws SQLException {
        Item item = getItem(connection,id);
        return item.getName();
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



    public static void addItem(Connection connection, String name, String description, double price, int stock) throws SQLException {
        String query = "INSERT INTO products (name, description, price, stock) VALUES (?, ?, ?, ?)";

        try (Connection con = connection;
             PreparedStatement ps = con.prepareStatement(query)) {

            // Ställ in parametrarna för INSERT
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDouble(3, price);
            ps.setInt(4, stock);

            ps.executeUpdate();
        }
    }




}
