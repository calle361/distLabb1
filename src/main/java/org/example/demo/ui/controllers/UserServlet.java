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

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "userServlet", value = "/user-servlet")
public class UserServlet extends HttpServlet {



    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "login";  // Standardaction
        }

        switch (action) {
            case "logout":
                handleLogout(request, response);  // Hantera utloggning vid GET
                break;
            default:
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                break;
        }


    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "login";  // Default action
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
                handleUpdateStock(request, response);  // Ny action för att hantera lageruppdatering
                break;
            case "addCategory":
                handleAddCategory(request, response);  // Ny action för att hantera lageruppdatering
                break;
            case "editCategory":
                handleEditCategory(request, response);
                break;
            default:
                response.sendRedirect("error.jsp");  // Handle unknown actions
                break;
        }
    }

    // Utloggningshantering
    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);  // Hämta nuvarande session
        if (session != null) {
            session.invalidate();  // Ogiltigförklara sessionen
        }

        // Ta bort cookies om de finns
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username") || cookie.getName().equals("password")) {
                    cookie.setMaxAge(0);  // Sätt cookie-livslängden till 0 för att ta bort dem
                    cookie.setMaxAge(0);  // Sätt cookie-livslängden till 0 för att ta bort dem
                    response.addCookie(cookie);
                }
            }
        }

        // Skicka tillbaka användaren till index-sidan efter utloggning
        response.sendRedirect("index.jsp");
    }

    // Inloggningshantering med cookies
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        // Använd Model för att hantera inloggning
        User user = UserHandler.loginUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("permissionLevel", user.getPermissionLevel());  // Spara behörighetsnivån i sessionen

            System.out.println("User logged in with permission level: " + user.getPermissionLevel());  // Logga behörigheten

            // Hantera 'remember me' - cookie
            if ("on".equals(rememberMe)) {
                Cookie usernameCookie = new Cookie("username", username);
                usernameCookie.setMaxAge(60 * 60 * 24 * 7);  // Sätt cookie-livslängd till 7 dagar
                response.addCookie(usernameCookie);

                Cookie passwordCookie = new Cookie("password", password);
                passwordCookie.setMaxAge(60 * 60 * 24 * 7);  // Sätt cookie-livslängd till 7 dagar
                response.addCookie(passwordCookie);
            }

            response.sendRedirect("index.jsp");
        } else {
            response.getWriter().println("Login failed! Please try again.");
        }
    }



    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Sätt en standardbehörighet (Customer)
        PermissionLevel permissionLevel = PermissionLevel.Customer;

        // Använd Model för att hantera registrering
        User user = UserHandler.registerUser(username, password, permissionLevel);

        if (user != null) {
            response.sendRedirect("login.jsp");  // Skicka till login-sidan efter registrering
        } else {
            response.getWriter().println("Registration failed.");
        }
    }



    private void handleUpdateRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String newRole = request.getParameter("role");

        // Konvertera strängen "role" till PermissionLevel
        PermissionLevel newPermissionLevel = PermissionLevel.valueOf(newRole);

        // Använd Model för att uppdatera användarroll
        boolean updated = UserHandler.updateUserRole(username, newPermissionLevel);

        if (updated) {
            request.setAttribute("message", "User role updated successfully.");
        } else {
            request.setAttribute("message", "Failed to update user role.");
        }

        // Ladda om samma sida (admin.jsp)
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }



    private void handleSearchUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String searchUsername = request.getParameter("searchUsername");

        // Använd Model för att hitta användaren
        User foundUser = UserHandler.findUserByUsername(searchUsername);

        if (foundUser != null) {
            request.setAttribute("foundUser", foundUser);  // Spara den hittade användaren i request-attribut
        } else {
            request.setAttribute("message", "No user found with username: " + searchUsername);  // Skicka ett felmeddelande om användaren inte hittas
        }

        // Vid fel eller framgång, ladda om samma sida (admin.jsp)
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }




    private void handleUpdateStock(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int itemId = Integer.parseInt(request.getParameter("id"));
        int newStock = Integer.parseInt(request.getParameter("newStock"));

        try {
            // Använd business-lagret (ItemHandler) för att uppdatera lagerstatus
            ItemHandler.updateStock(itemId, newStock);

            // Logga för felsökning (valfritt)
            System.out.println("Stock for item " + itemId + " updated to " + newStock);

            // Omdirigera till warehouse.jsp för att ladda om sidan med uppdaterade data
            response.sendRedirect("warehouse.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error updating stock: " + e.getMessage());

            // Vid fel, ladda om sidan med felmeddelande
            request.getRequestDispatcher("warehouse.jsp").forward(request, response);
        }
    }


    // Lägg till ny produkt
    private void handleAddItem(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        int categoryId = Integer.parseInt(request.getParameter("category"));

        try {
            ItemHandler.addItem(name, description, price, stock, categoryId);  // Använd business-lagret
            request.setAttribute("message", "Item added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error adding item: " + e.getMessage());
        }

        request.getRequestDispatcher("warehouse.jsp").forward(request, response);  // Ladda om sidan utan omdirigering
    }

    private void handleAddCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String categoryName = request.getParameter("categoryName");

        try {
            ItemHandler.addCategory(categoryName);  // Använd ItemHandler för att lägga till kategori
            request.setAttribute("message", "Category added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error adding category: " + e.getMessage());
        }

        request.getRequestDispatcher("warehouse.jsp").forward(request, response);  // Ladda om sidan utan omdirigering
    }

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
