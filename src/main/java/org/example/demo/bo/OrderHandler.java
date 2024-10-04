package org.example.demo.bo;

import org.example.demo.db.DBManager;
import org.example.demo.db.ItemDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderHandler {
    static DBManager dbManager = Model.getDBManager();
    public static boolean handleTransaktion(List<Integer> ids){
        Connection conn = null;
        Boolean successFlag = false;
        try {
            System.out.println("wow nu ska vi bli av med varor");
            // Step 1: Get the connection
            conn = dbManager.getConnection();
            // Step 2: Disable auto-commit for transaction handling
            conn.setAutoCommit(false);

            // Step 3: Check stock for each item and reduce stock if available
            for (int itemId : ids) {
                if (conn == null) {
                    System.out.println("Connection is null1");
                    throw new SQLException("Database connection is null");
                }
                if (conn.isClosed()) {
                    System.out.println("Connection is closed1");
                    throw new SQLException("Database connection is closed");
                }
                // Check stock using ItemHandler (which internally uses ItemDB)
                int stock = ItemHandler.getstockById(itemId);
                if (conn == null) {
                    System.out.println("Connection is null2");
                    throw new SQLException("Database connection is null");
                }
                if (conn.isClosed()) {
                    System.out.println("Connection is closed2");
                    throw new SQLException("Database connection is closed");
                }
                if (stock > 0) {
                    // Reduce the stock for this item
                    System.out.println("wow nu ska vi bli av med ID:"+itemId);
                    ItemHandler.updateStock2(conn, itemId, stock - 1);
                    System.out.println("Stock updated for item ID: " + itemId);
                } else {

                    throw new Exception("Insufficient stock for item with ID: " + itemId);
                }
            }

            // Step 4: If all stock updates succeed, create an order
            //int userId = (int) request.getSession().getAttribute("userId");  // Assuming userId is stored in session
            //OrderDB.createOrder(userId, itemIds);  // Create the order

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
                    //conn.close();  // Close connection
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }


        return successFlag;
    }

    public OrderHandler() throws SQLException {
    }
}
