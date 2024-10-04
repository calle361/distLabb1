package org.example.demo;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Retrieve the itemIds from the POST request (they will be received as Strings)
        String[] itemIdsStr = request.getParameterValues("itemIds");
        String checkoutStatus;
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

            if (OrderHandler.handleTransaktion(itemIds,(String) request.getSession().getAttribute("username"))){
                response.getWriter().println("Order placed successfully!");
                checkoutStatus="Order placed successfully!";
            }else {
                response.getWriter().println("Transaction failed, rolled back.");
                checkoutStatus="Transaction failed, rolled back.";
            }

        } else {
            // No items received, handle this case
            System.out.println("No items received.");
            response.getWriter().println("No items in cart.");
            checkoutStatus="Failed.No items in cart.";
        }
        request.setAttribute("checkoutStatus", checkoutStatus);
        // Forward the request to checkedOut.jsp
        request.getSession().removeAttribute("cart");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/checkedOut.jsp");
        dispatcher.forward(request, response);

    }
    public void destroy() {
    }
}