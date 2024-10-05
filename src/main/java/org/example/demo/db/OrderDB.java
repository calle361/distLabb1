package org.example.demo.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.demo.bo.models.Order;
import org.example.demo.bo.models.OrderItem;

/**
 * The OrderDB class provides static methods to interact with the database for operations
 * related to orders and order items, such as creating orders, retrieving them, and handling order items.
 */
public class OrderDB {

    /**
     * Creates a new order in the database.
     *
     * @param connection The active {@link Connection} to the database.
     * @param userId The ID of the user placing the order.
     * @param totalPrice The total price of the order.
     * @return The generated order ID.
     * @throws SQLException If there is an error creating the order or fetching the generated order ID.
     */
    public static int createOrder(Connection connection, int userId, double totalPrice) throws SQLException {
        String query = "INSERT INTO orders (user_id, total_price) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setDouble(2, totalPrice);
            ps.executeUpdate();

            // Get the generated order ID
            try (var generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the newly created order ID
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }
    }

    /**
     * Creates multiple order items for a specific order.
     *
     * @param connection The active {@link Connection} to the database.
     * @param orderId The ID of the order.
     * @param productIds A list of product IDs to add as order items.
     * @param itemName A list of product names corresponding to the product IDs.
     * @throws SQLException If there is an error inserting order items into the database.
     */
    public static void createOrderItems(Connection connection, int orderId, List<Integer> productIds, List<String> itemName) throws SQLException {
        String query = "INSERT INTO order_items (order_id, product_id, product_name) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int i = 0;
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

    /**
     * Creates a single order item for a specific order.
     *
     * @param connection The active {@link Connection} to the database.
     * @param orderId The ID of the order.
     * @param productId The ID of the product to add as an order item.
     * @throws SQLException If there is an error inserting the order item into the database.
     */
    public static void createOrderItem(Connection connection, int orderId, int productId) throws SQLException {
        String query = "INSERT INTO order_items (order_id, product_id) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.executeUpdate();  // Execute the insert for a single item
        }
    }

    /**
     * Retrieves all orders from the database.
     *
     * @param connection The active {@link Connection} to the database.
     * @return A list of {@link Order} objects representing all orders.
     * @throws SQLException If there is an error fetching orders from the database.
     */
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
                orders.add(new Order(orderId, totalPrice, userId, orderDate));
            }
        }
        return orders;
    }

    /**
     * Retrieves all order items for a specific order ID.
     *
     * @param connection The active {@link Connection} to the database.
     * @param orderId The ID of the order.
     * @return A list of {@link OrderItem} objects representing all items in the specified order.
     * @throws SQLException If there is an error fetching order items from the database.
     */
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
                    orderItems.add(new OrderItem(orderItemId, orderId, productId, productName));
                }
            }
        }
        return orderItems;
    }

    /**
     * Removes an order from the database based on the provided order ID.
     *
     * @param connection The active {@link Connection} to the database.
     * @param orderId The ID of the order to remove.
     * @return {@code true} if the order was successfully removed, {@code false} otherwise.
     * @throws SQLException If there is an error deleting the order from the database.
     */
    public static boolean removeOrder(Connection connection, int orderId) throws SQLException {
        String query = "DELETE FROM orders WHERE order_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            int rowsAffected = ps.executeUpdate();  // Number of rows deleted

            // Check if any rows were affected (i.e., if the order existed and was deleted)
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}