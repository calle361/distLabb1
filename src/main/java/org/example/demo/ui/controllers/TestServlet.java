package org.example.demo.ui.controllers;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "TestServlet", value = "/items")
public class TestServlet extends HttpServlet {
    private String message;
    @Override
    public void init() throws ServletException {
    /*
        System.out.println("INITIALIZE\n\n\n\n\n");

        Model.initialize();

     */
        super.init();
    }

    @Override
    public void destroy() {
        super.destroy();
        //Model.shutdown();

        System.out.println("SHUTDOWN");
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
        // Get the itemId from the form (this comes from the hidden input field)
        int itemId = Integer.parseInt(request.getParameter("itemId"));

        // Print the itemId to the console (for debugging purposes)
        System.out.println("Item ID: " + itemId);

        // You can also send a response back to the client to confirm
        response.getWriter().println("Received item ID: " + itemId);

        // Get or create the shopping cart (stored in the session)
        HttpSession session = request.getSession();
        List<Integer> cart = (List<Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        // Add the new item to the cart
        cart.add(itemId);

        // Optionally, print cart contents for debugging
        System.out.println("Cart: " + cart);

        // Send response
        response.sendRedirect(request.getContextPath() + "/index.jsp");

    }

}