package org.example.demo.bo.handlers;

import org.example.demo.bo.models.Order;
import org.example.demo.bo.models.OrderItem;
import org.example.demo.db.DBManager;
import org.example.demo.db.ItemDB;
import org.example.demo.db.OrderDB;
import org.example.demo.db.UserDB;
import org.example.demo.ui.facades.OrderInfo;
import org.example.demo.ui.facades.OrderItemInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The OrderHandler class handles all operations related to orders, including creating orders,
 * managing transactions, retrieving orders, and deleting orders. It interacts with the database
 * using various DB classes like {@link ItemHandler}, {@link UserDB}, and {@link OrderDB}.
 */
public class OrderHandler {
    static DBManager dbManager;

    /**
     * Handles the transaction of an order by reducing stock, creating an order, and managing order items.
     * The process involves checking stock for each item in the order and rolling back the transaction
     * if any item has insufficient stock.
     *
     * @param ids The list of item IDs that are part of the order.
     * @param username The username of the user placing the order.
     * @return {@code true} if the transaction is successful, otherwise {@code false}.
     */
    public static boolean handleTransaktion(List<Integer> ids, String username) {
        Connection conn = null;
        Boolean successFlag = false;

        try {
            System.out.println("Processing the transaction...");
            // Step 1: Get the connection
            conn = dbManager.getConnection();
            // Step 2: Disable auto-commit for transaction handling
            conn.setAutoCommit(false);

            int totPrice = 0;
            List<String> itemNames = new ArrayList<>();

            // Step 3: Check stock for each item and reduce stock if available
            for (int itemId : ids) {
                int stock = ItemHandler.getstockById(itemId);
                totPrice += ItemHandler.getPriceById(itemId);
                itemNames.add(ItemDB.getItemName(conn, itemId));

                if (stock > 0) {
                    // Reduce stock for this item
                    ItemHandler.updateStock2(conn, itemId, stock - 1);
                    System.out.println("Stock updated for item ID: " + itemId);
                } else {
                    throw new Exception("Insufficient stock for item with ID: " + itemId);
                }
            }

            // Step 4: Create an order if all stock updates succeed
            int userId = UserDB.getUserIdByUsername(dbManager.getConnection(), username);
            int orderId = OrderDB.createOrder(dbManager.getConnection(), userId, totPrice); // Create the order
            OrderDB.createOrderItems(dbManager.getConnection(), orderId, ids, itemNames);  // Create order items

            // Step 5: Commit the transaction
            conn.commit();
            successFlag = true;

        } catch (Exception e) {
            // Handle rollback on failure
            if (conn != null) {
                try {
                    conn.rollback();  // Rollback transaction on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            // Always restore auto-commit and close the connection
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);  // Reset auto-commit
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return successFlag;
    }

    /**
     * Retrieves all orders placed by a specific user.
     *
     * @param username The username of the user whose orders are to be retrieved.
     * @return A list of {@link OrderInfo} objects containing order details.
     * @throws SQLException If an SQL error occurs during the retrieval process.
     */
    public static List<OrderInfo> getAllOrders(String username) throws SQLException {
        Connection conn = dbManager.getConnection();
        List<Order> orders = OrderDB.getAllOrders(conn);

        // Convert each order to OrderInfo (facade)
        List<OrderInfo> orderInfos = new ArrayList<>();
        for (Order order : orders) {
            orderInfos.add(new OrderInfo(order.getOid(), order.getPrice(), order.getUserid(), order.getDate()));
        }

        return orderInfos;
    }

    /**
     * Retrieves all items associated with a specific order.
     *
     * @param orderId The ID of the order for which to retrieve items.
     * @return A list of {@link OrderItemInfo} containing details of the items in the order.
     * @throws SQLException If an SQL error occurs during the retrieval process.
     */
    public static List<OrderItemInfo> getOrderItems(int orderId) throws SQLException {
        Connection conn = dbManager.getConnection();
        List<OrderItem> orderItems = OrderDB.getOrderItemsByOrderId(conn, orderId);

        List<OrderItemInfo> orderItemInfos = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            orderItemInfos.add(new OrderItemInfo(
                    orderItem.getOrderItemid(),
                    orderItem.getOrderid(),
                    orderItem.getOrderItemid(),
                    orderItem.getOrderItemname()
            ));
        }

        return orderItemInfos;
    }

    /**
     * Removes a specific order from the system.
     *
     * @param orderId The ID of the order to be removed.
     * @throws SQLException If an SQL error occurs during the removal process.
     */
    public static void removeOrder(int orderId) throws SQLException {
        Connection conn = dbManager.getConnection();
        boolean successFlag = OrderDB.removeOrder(conn, orderId);
    }

    /**
     * Default constructor for the {@link OrderHandler} class.
     *
     * @throws SQLException If a database connection issue occurs.
     */
    public OrderHandler() throws SQLException {
    }

}
