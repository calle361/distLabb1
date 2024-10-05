package org.example.demo.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.demo.bo.models.Order;
import org.example.demo.bo.models.OrderItem;

public class OrderDB {

    // Method to create a new order
    public static int createOrder(Connection connection, int userId, double totalPrice) throws SQLException {
        String query = "INSERT INTO orders (user_id, total_price) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setDouble(2, totalPrice);
            ps.executeUpdate();

            // Get the generated order_id
            try (var generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the newly created order ID
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }
    }


    // Method to create order items
    public static void createOrderItems(Connection connection, int orderId, List<Integer> productIds,List<String> itemName) throws SQLException {
        String query = "INSERT INTO order_items (order_id, product_id, product_name) VALUES (?, ?, ?)";

        // Use a PreparedStatement to add each item to the order
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int i=0;
            for (int productId : productIds) {
                ps.setInt(1, orderId);
                ps.setInt(2, productId);
                ps.setString(3, itemName.get(i));
                ps.addBatch();  // Add to batch for batch execution
                i++;
            }
            ps.executeBatch();  // Execute the batch
        }
    }

    public static void createOrderItem(Connection connection, int orderId, int productId) throws SQLException {
        String query = "INSERT INTO order_items (order_id, product_id) VALUES (?, ?)";

        // Use a PreparedStatement to add item to the order
        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.executeUpdate();  // Execute the insert for a single item
        }
    }

    // Method to get all orders
    public static List<Order> getAllOrders(Connection connection) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int userId = rs.getInt("user_id");
                Timestamp orderDate = rs.getTimestamp("order_date");
                int totalPrice = rs.getInt("total_price");

                // Create an Order object and add it to the list
                orders.add(new Order(orderId,totalPrice, userId ,orderDate));
            }
        }
        return orders;
    }

    // Method to get all order_items for a specific order_id
    public static List<OrderItem> getOrderItemsByOrderId(Connection connection, int orderId) throws SQLException {
        List<OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT * FROM order_items WHERE order_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int orderItemId = rs.getInt("order_items_id");
                    int productId = rs.getInt("product_id");
                    String productName = rs.getString("product_name");

                    // Create an OrderItem object and add it to the list
                    orderItems.add(new OrderItem(orderItemId, orderId, productId,productName));
                }
            }
        }
        return orderItems;
    }
    public static boolean removeOrder(Connection connection, int orderId) throws SQLException {    String query = "DELETE FROM orders WHERE order_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            int rowsAffected = ps.executeUpdate();  // Number of rows deleted

            // Check if any rows were affected (i.e., if the order existed and was deleted)
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return false;
        }
    }
}
