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

public class OrderHandler {
    static DBManager dbManager = Model.getDBManager();


    public static boolean handleTransaktion(List<Integer> ids,String username){
        Connection conn = null;
        Boolean successFlag = false;
        try {
            System.out.println("wow nu ska vi bli av med varor");
            // Step 1: Get the connection
            conn = dbManager.getConnection();
            // Step 2: Disable auto-commit for transaction handling
            conn.setAutoCommit(false);
            int totPrice=0;
            List<String> itemNames = new ArrayList<>();
            // Step 3: Check stock for each item and reduce stock if available
            for (int itemId : ids) {
                // Check stock using ItemHandler (which internally uses ItemDB)
                int stock = ItemHandler.getstockById(itemId);
                totPrice+=ItemHandler.getPriceById(itemId);
                itemNames.add(ItemDB.getItemName(conn,itemId));
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
            int userId = UserDB.getUserIdByUsername(dbManager.getConnection(),username); // Assuming userId is stored in session
            int orderId=OrderDB.createOrder(dbManager.getConnection(),userId, totPrice);  // Create the order
            OrderDB.createOrderItems(dbManager.getConnection(),orderId,ids,itemNames);
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
    public static List<OrderInfo> getAllOrders(String username) throws SQLException {
        Connection conn = dbManager.getConnection();
        List<Order> orders = OrderDB.getAllOrders(conn);
        //ksk lägg till att de görs om till facade.
        List<OrderInfo>orderInfos=new ArrayList<OrderInfo>();
        for (Order order : orders) {
            orderInfos.add(new OrderInfo(order.getOid(), order.getPrice(), order.getUserid(),order.getDate()));
        }
        return orderInfos;
    }
    public static List<OrderItemInfo> getOrderItems(int orderId) throws SQLException {
        Connection conn = dbManager.getConnection();
        List<OrderItem> orderItems=OrderDB.getOrderItemsByOrderId(conn, orderId);
        List<OrderItemInfo>orderItemInfos=new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            orderItemInfos.add(new OrderItemInfo(orderItem.getOrderItemid(),
                    orderItem.getOrderid(),
                    orderItem.getOrderItemid(),
                    orderItem.getOrderItemname()));
        }
        return orderItemInfos;
    }
    public static void removeOrder(int orderId) throws SQLException {
        Connection conn = dbManager.getConnection();
        boolean successFlag = OrderDB.removeOrder(conn, orderId);
    }

    public OrderHandler() throws SQLException {
    }
}
