package org.example.demo.bo;

import org.example.demo.db.DBManager;
import org.example.demo.db.ItemDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderHandler {
    //static DBManager dbManager = Model.getDBManager();
    static Connection conn=Model.getConnection();
    public static boolean handleTransaktion(List<Integer> ids){

        try {

            // Step 2: Disable auto-commit for transaction handling
            conn.setAutoCommit(false);

            // Step 3: Check stock for each item and reduce stock if available
            for (int itemId : ids) {
                // Check stock using ItemHandler (which internally uses ItemDB)
                int stock = ItemHandler.getstockById(itemId);

                if (stock > 0) {
                    // Reduce the stock for this item
                    ItemHandler.updateStock(itemId, stock - 1);
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
                    conn.close();  // Close connection
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }


        return true;
    }

    public OrderHandler() throws SQLException {
    }
}
