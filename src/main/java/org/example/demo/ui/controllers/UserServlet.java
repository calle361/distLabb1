package org.example.demo.ui.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.demo.bo.PermissionLevel;
import org.example.demo.bo.handlers.ItemHandler;
import org.example.demo.bo.handlers.UserHandler;
import org.example.demo.bo.models.User;
import org.example.demo.bo.handlers.ItemHandler;

import org.example.demo.bo.models.User;
import org.example.demo.ui.facades.UserInfo;

import java.io.IOException;
import java.sql.SQLException;

/**
 * This servlet handles user-related actions such as login, registration, updating roles,
 * searching for users, and managing items and categories.
 */
@WebServlet(name = "userServlet", value = "/user-servlet")
public class UserServlet extends HttpServlet {


    /**
     * Handles GET requests for user actions, such as logout.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "login";
        }

        switch (action) {
            case "logout":
                handleLogout(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                break;
        }
    }

    /**
     * Handles POST requests for various user actions like login, register, updating roles,
     * managing items, and handling categories.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "login";
        }

        switch (action) {
            case "login":
                handleLogin(request, response);
                break;
            case "register":
                handleRegister(request, response);
                break;
            case "updateRole":
                handleUpdateRole(request, response);
                break;
            case "searchUser":
                handleSearchUser(request, response);
                break;
            case "addItem":
                handleAddItem(request, response);
                break;
            case "editItem":
                handleEditItem(request, response);
                break;
            case "updateStock":
                handleUpdateStock(request, response);
                break;
            case "addCategory":
                handleAddCategory(request, response);
                break;
            case "editCategory":
                handleEditCategory(request, response);
                break;
            default:
                response.sendRedirect("error.jsp");
                break;
        }
    }

    /**
     * Handles user logout by invalidating the session and removing cookies.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws IOException if an I/O error occurs
     */
    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username") || cookie.getName().equals("password")) {
                    cookie.setMaxAge(0);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        response.sendRedirect("index.jsp");
    }

    /**
     * Handles user login and sets session attributes and cookies if 'remember me' is enabled.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws IOException if an I/O error occurs
     */
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        UserInfo user = UserHandler.loginUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("permissionLevel", user.getPermissionLevel());

            System.out.println("User logged in with permission level: " + user.getPermissionLevel());

            if ("on".equals(rememberMe)) {
                Cookie usernameCookie = new Cookie("username", username);
                usernameCookie.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(usernameCookie);

                Cookie passwordCookie = new Cookie("password", password);
                passwordCookie.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(passwordCookie);
            }
            response.sendRedirect("index.jsp");
        } else {
            response.getWriter().println("Login failed! Please try again.");
        }
    }

    /**
     * Handles user registration by creating a new user with default permission level.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws IOException if an I/O error occurs
     */
    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        PermissionLevel permissionLevel = PermissionLevel.Customer;

        UserInfo user = UserHandler.registerUser(username, password, permissionLevel);

        if (user != null) {
            response.sendRedirect("login.jsp");
        } else {
            response.getWriter().println("Registration failed.");
        }
    }


    /**
     * Handles updating a user's role by modifying their permission level.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    private void handleUpdateRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String newRole = request.getParameter("role");

        PermissionLevel newPermissionLevel = PermissionLevel.valueOf(newRole);

        boolean updated = UserHandler.updateUserRole(username, newPermissionLevel);

        if (updated) {
            request.setAttribute("message", "User role updated successfully.");
        } else {
            request.setAttribute("message", "Failed to update user role.");
        }

        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }

    /**
     * Handles searching for a user by their username.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    private void handleSearchUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String searchUsername = request.getParameter("searchUsername");

        UserInfo foundUser = UserHandler.findUserByUsername(searchUsername);

        if (foundUser != null) {
            request.setAttribute("foundUser", foundUser);
        } else {
            request.setAttribute("message", "No user found with username: " + searchUsername);
        }

        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }

    /**
     * Handles updating stock for an item.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    private void handleUpdateStock(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int itemId = Integer.parseInt(request.getParameter("id"));
        int newStock = Integer.parseInt(request.getParameter("newStock"));

        try {

            ItemHandler.updateStock(itemId, newStock);
            System.out.println("Stock for item " + itemId + " updated to " + newStock);
            response.sendRedirect("warehouse.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error updating stock: " + e.getMessage());
            request.getRequestDispatcher("warehouse.jsp").forward(request, response);
        }
    }

    /**
     * Handles adding a new item to the inventory.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    private void handleAddItem(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        int categoryId = Integer.parseInt(request.getParameter("category"));

        try {
            ItemHandler.addItem(name, description, price, stock, categoryId);
            request.setAttribute("message", "Item added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error adding item: " + e.getMessage());
        }

        request.getRequestDispatcher("warehouse.jsp").forward(request, response);
    }

    /**
     * Handles adding a new category to the inventory.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    private void handleAddCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String categoryName = request.getParameter("categoryName");

        try {
            ItemHandler.addCategory(categoryName);
            request.setAttribute("message", "Category added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error adding category: " + e.getMessage());
        }

        request.getRequestDispatcher("warehouse.jsp").forward(request, response);
    }

    /**
     * Handles editing an existing item in the inventory.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    private void handleEditItem(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int itemId = Integer.parseInt(request.getParameter("id"));
        String newName = request.getParameter("name");
        int categoryId = Integer.parseInt(request.getParameter("category"));

        try {
            ItemHandler.updateItem(itemId, newName, categoryId);
            request.setAttribute("message", "Item updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error updating item: " + e.getMessage());
        }

        request.getRequestDispatcher("warehouse.jsp").forward(request, response);
    }

    /**
     * Handles editing an existing category in the inventory.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    private void handleEditCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String newName = request.getParameter("newName");

        try {
            ItemHandler.updateCategory(categoryId, newName);
            request.setAttribute("message", "Category updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error updating category: " + e.getMessage());
        }
        request.getRequestDispatcher("warehouse.jsp").forward(request, response);
    }
}
