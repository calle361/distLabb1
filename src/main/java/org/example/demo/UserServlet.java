package org.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.demo.db.UserDB;
import org.example.demo.db.ItemDB;
import org.example.demo.bo.PermissionLevel;
import org.example.demo.db.DBManager;
import org.example.demo.bo.*;

import java.io.IOException;
import java.sql.Connection;
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

            case "updateStock":
                handleUpdateStock(request, response);  // Ny action för att hantera lageruppdatering
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

        try (Connection connection = DBManager.getConnection()) {
            if (connection != null) {
                User user = UserDB.login(connection, username, password);
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);
                    session.setAttribute("permissionLevel", user.getPermissionLevel());  // Spara behörighetsnivån i sessionen

                    System.out.println("User logged in with permission level: " + user.getPermissionLevel());  // Logga behörigheten


                    // Skapa en cookie om "remember me" är markerat
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
            } else {
                response.getWriter().println("Database connection failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }


    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection connection = DBManager.getConnection()) {
            if (connection != null) {
                PermissionLevel permissionLevel = PermissionLevel.Customer;  // Sätt en standardbehörighet
                User user = UserDB.register(connection, username, password, permissionLevel);

                if (user != null) {
                    response.sendRedirect("login.jsp");  // Skicka till login-sidan efter registrering
                } else {
                    response.getWriter().println("Registration failed.");
                }
            } else {
                response.getWriter().println("Database connection failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }


    private void handleUpdateRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String newRole = request.getParameter("role");

        try (Connection connection = DBManager.getConnection()) {
            if (connection != null) {
                PermissionLevel newPermissionLevel = PermissionLevel.valueOf(newRole);
                boolean updated = UserDB.updateUserRole(connection, username, newPermissionLevel);

                if (updated) {
                    request.setAttribute("message", "User role updated successfully.");
                } else {
                    request.setAttribute("message", "Failed to update user role.");
                }
            } else {
                request.setAttribute("message", "Database connection failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error: " + e.getMessage());
        }

        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }


    private void handleSearchUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String searchUsername = request.getParameter("searchUsername");

        try (Connection connection = DBManager.getConnection()) {
            if (connection != null) {
                User foundUser = UserDB.findUserByUsername(connection, searchUsername);

                if (foundUser != null) {
                    request.setAttribute("foundUser", foundUser);  // Spara den hittade användaren i request-attribut
                } else {
                    request.setAttribute("message", "No user found with username: " + searchUsername);  // Skicka ett felmeddelande om användaren inte hittas
                }

                request.getRequestDispatcher("admin.jsp").forward(request, response);
            } else {
                response.getWriter().println("Database connection failed.");
            }
        } catch (SQLException | ServletException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }



    // Hantering av lageruppdatering
    private void handleUpdateStock(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int itemId = Integer.parseInt(request.getParameter("id"));
        int newStock = Integer.parseInt(request.getParameter("newStock"));

        try (Connection connection = DBManager.getConnection()) {
            if (connection != null) {
                ItemDB.updateStock(itemId, newStock);
                response.getWriter().println("Stock updated successfully.");
                response.sendRedirect("warehouse.jsp");
            } else {
                response.getWriter().println("Database connection failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
    private void handleAddItem(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String stockStr = request.getParameter("stock");

        try {

            double price = Double.parseDouble(priceStr);
            int stock = Integer.parseInt(stockStr);

            ItemDB.addItem(name, description, price, stock);

            response.sendRedirect("warehouse.jsp");
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }



}
