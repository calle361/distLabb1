package org.example.demo;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.demo.bo.Order;
import org.example.demo.bo.OrderHandler;
import org.example.demo.bo.OrderItem;

@WebServlet(name = "OrderItemsServlet", value = "/orderItems")
public class OrderItemsServlet extends HttpServlet {
    private String message;
    public void init() {
        message = "Hello World!";
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        // Get the orderId from the request
        String orderIdStr = request.getParameter("orderId");
        int orderId = Integer.parseInt(orderIdStr);
        try {
            List<OrderItem> orderItems = OrderHandler.getOrderItems(orderId);
            request.setAttribute("orderItems", orderItems);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/orderItems.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException | ServletException e) {
            e.printStackTrace();
            // Handle errors (e.g., show an error page)
        }

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


    }
    public void destroy() {
    }
}