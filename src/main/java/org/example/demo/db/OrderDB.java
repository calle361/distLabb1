package org.example.demo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
    public static void createOrderItems(Connection connection, int orderId, List<Integer> productIds) throws SQLException {
        String query = "INSERT INTO order_items (order_id, product_id) VALUES (?, ?)";

        // Use a PreparedStatement to add each item to the order
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            for (int productId : productIds) {
                ps.setInt(1, orderId);
                ps.setInt(2, productId);
                ps.addBatch();  // Add to batch for batch execution
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
}
