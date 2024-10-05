package org.example.demo.ui.controllers;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.demo.bo.handlers.OrderHandler;
import org.example.demo.bo.models.OrderItem;
import org.example.demo.ui.facades.OrderItemInfo;

/**
 * This servlet handles displaying and managing the items within an order.
 * It responds to both GET and POST requests related to order items.
 */
@WebServlet(name = "OrderItemsServlet", value = "/orderItems")
public class OrderItemsServlet extends HttpServlet {
    private String message;

    /**
     * Initializes the servlet with a welcome message.
     */
    public void init() {
        message = "Hello World!";
    }

    /**
     * Handles GET requests to display the items in an order. Retrieves the orderId from the request, fetches the order items,
     * and forwards the request to the JSP page for displaying the items.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws IOException if an I/O error occurs
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        // Get the orderId from the request
        String orderIdStr = request.getParameter("orderId");
        int orderId = Integer.parseInt(orderIdStr);
        try {
            List<OrderItemInfo> orderItems = OrderHandler.getOrderItems(orderId);
            request.setAttribute("orderItems", orderItems);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/orderItems.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException | ServletException e) {
            e.printStackTrace();
            // Handle errors (e.g., show an error page)
        }
    }

    /**
     * Handles POST requests to remove an order by its orderId. After removing the order, redirects the user to the index page.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws IOException      if an I/O error occurs
     * @throws ServletException if a servlet-specific error occurs
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String orderIdStr = request.getParameter("orderId");
        int orderId = Integer.parseInt(orderIdStr);
        try {
            OrderHandler.removeOrder(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp");

    }

    /**
     * This method is called when the servlet is destroyed. Currently, no cleanup is performed.
     */
    public void destroy() {
    }
}