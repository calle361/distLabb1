package org.example.demo.ui.controllers;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.demo.bo.Order;
import org.example.demo.bo.OrderHandler;

@WebServlet(name = "OrderServlet", value = "/order")
public class OrderServlet extends HttpServlet {
    private String message;
    public void init() {
        message = "Hello World!";
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        String sessionUsername = (String) session.getAttribute("username");
        //int userId = (int) session.getAttribute("uid"); // Assuming userId is stored in session

        try {
            List<Order> orders = OrderHandler.getAllOrders(sessionUsername);
            request.setAttribute("orders", orders);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/packOrder.jsp");
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