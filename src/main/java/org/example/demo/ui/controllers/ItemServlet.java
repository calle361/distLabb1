package org.example.demo.ui.controllers;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 * This servlet handles adding items to the user's shopping cart and responding to both GET and POST requests.
 */
@WebServlet(name = "TestServlet", value = "/items")
public class ItemServlet extends HttpServlet {
    private String message;

    /**
     * Initializes the servlet. This method is called once when the servlet is first loaded.
     * It currently does nothing but can be used for initialization purposes.
     *
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void init() throws ServletException {
        super.init();
    }

    /**
     * Destroys the servlet when the server shuts down or the servlet is no longer needed.
     * This method currently just prints a shutdown message but could include cleanup code.
     */
    @Override
    public void destroy() {
        super.destroy();
        //Model.shutdown();

        System.out.println("SHUTDOWN");
    }

    /**
     * Handles GET requests and displays a simple HTML message.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws IOException if an I/O error occurs
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    /**
     * Handles POST requests, processes the item ID sent from a form, adds the item to the shopping cart,
     * and redirects the user to the index page.
     *
     * @param request  the HttpServletRequest object, containing the item ID
     * @param response the HttpServletResponse object
     * @throws IOException if an I/O error occurs
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int itemId = Integer.parseInt(request.getParameter("itemId"));

        HttpSession session = request.getSession();
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        cart.put(itemId, cart.getOrDefault(itemId, 0) + 1);

        System.out.println("Cart: " + cart);

        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}