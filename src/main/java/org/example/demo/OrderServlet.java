package org.example.demo;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.demo.db.DBManager;
import org.example.demo.db.ItemDB;
import org.example.demo.db.OrderDB;
import org.example.demo.bo.ItemHandler;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.demo.bo.OrderHandler;
import org.example.demo.db.DBManager;

@WebServlet(name = "OrderServlet", value = "/checkout")
public class OrderServlet extends HttpServlet {
    private String message;
    public void init() {
        message = "Hello World!";
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve the itemIds from the POST request (they will be received as Strings)
        String[] itemIdsStr = request.getParameterValues("itemIds");

        if (itemIdsStr != null) {
            // Create a list to hold the integer item IDs
            List<Integer> itemIds = new ArrayList<>();

            // Convert each string in the array to an integer and add to the list
            for (String idStr : itemIdsStr) {
                try {
                    int itemId = Integer.parseInt(idStr);  // Convert string to integer
                    itemIds.add(itemId);                   // Add to the list
                } catch (NumberFormatException e) {
                    // Handle the case where the string is not a valid integer
                    e.printStackTrace();
                }
            }

            Connection conn = null;
            try {
                // Step 1: Get the connection
                conn = DBManager.getConnection();
                // Step 2: Disable auto-commit for transaction handling
                conn.setAutoCommit(false);

                // Step 3: Check stock for each item and reduce stock if available
                for (int itemId : itemIds) {
                    // Check stock using ItemHandler (which internally uses ItemDB)
                    int stock = ItemHandler.getstockById(itemId);

                    if (stock > 0) {
                        // Reduce the stock for this item
                        ItemHandler.updateStock( itemId, stock - 1);
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
                response.getWriter().println("Order placed successfully!");

            } catch (Exception e) {
                // Handle rollback on failure
                if (conn != null) {
                    try {
                        conn.rollback();  // Rollback transaction on error
                        response.getWriter().println("Transaction failed, rolled back.");
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

        } else {
            // No items received, handle this case
            System.out.println("No items received.");
            response.getWriter().println("No items in cart.");
        }

    }
    public void destroy() {
    }
}