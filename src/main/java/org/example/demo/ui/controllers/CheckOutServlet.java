package org.example.demo.ui.controllers;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.demo.bo.handlers.OrderHandler;

/**
 * This servlet handles the checkout process for the user's cart. It processes
 * GET and POST requests related to checkout and transaction handling.
 */
@WebServlet(name = "CheckOutServlet", value = "/checkout")
public class CheckOutServlet extends HttpServlet {
    private String message;

    /**
     * Initializes the servlet with a welcome message.
     */
    public void init() {
        message = "Hello World!";
    }

    /**
     * Handles GET requests by displaying a simple "Hello World" message.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws IOException if an I/O error occurs
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    /**
     * Handles POST requests by processing the checkout of items and attempting to
     * complete a transaction.
     *
     * @param request  the HttpServletRequest object, containing the item IDs in the cart
     * @param response the HttpServletResponse object
     * @throws IOException      if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String[] itemIdsStr = request.getParameterValues("itemIds");
        String checkoutStatus;
        if (itemIdsStr != null) {

            List<Integer> itemIds = new ArrayList<>();

            for (String idStr : itemIdsStr) {
                try {
                    int itemId = Integer.parseInt(idStr);
                    itemIds.add(itemId);
                } catch (NumberFormatException e) {

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

            System.out.println("No items received.");
            response.getWriter().println("No items in cart.");
            checkoutStatus="Failed.No items in cart.";
        }
        request.setAttribute("checkoutStatus", checkoutStatus);

        request.getSession().removeAttribute("cart");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/checkedOut.jsp");
        dispatcher.forward(request, response);

    }

    /**
     * This method is called when the servlet is destroyed. Currently, no cleanup
     * is performed.
     */
    public void destroy() {
    }
}