package org.example.demo.ui.controllers;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.demo.bo.models.Order;
import org.example.demo.bo.handlers.OrderHandler;
import org.example.demo.ui.facades.OrderInfo;

/**
 * This servlet handles retrieving and displaying the user's orders.
 * It processes GET requests to show all orders for the logged-in user.
 */
@WebServlet(name = "OrderServlet", value = "/order")
public class OrderServlet extends HttpServlet {
    private String message;

    /**
     * Initializes the servlet with a welcome message.
     */
    public void init() {
        message = "Hello World!";
    }

    /**
     * Handles GET requests to retrieve and display all orders associated with the logged-in user.
     * It forwards the request to the 'packOrder.jsp' page for rendering the orders.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws IOException if an I/O error occurs
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        String sessionUsername = (String) session.getAttribute("username");
        //int userId = (int) session.getAttribute("uid"); // Assuming userId is stored in session

        try {
            List<OrderInfo> orders = OrderHandler.getAllOrders(sessionUsername);
            request.setAttribute("orders", orders);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/packOrder.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException | ServletException e) {
            e.printStackTrace();
            // Handle errors (e.g., show an error page)
        }
    }

    /**
     * Handles POST requests. This method is currently not implemented.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet-specific error occurs
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    }

    /**
     * This method is called when the servlet is destroyed. Currently, no cleanup is performed.
     */
    public void destroy() {
    }
}