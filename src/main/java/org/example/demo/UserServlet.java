package org.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.demo.db.UserDB;
import org.example.demo.bo.PermissionLevel;
import org.example.demo.db.DBManager;
import org.example.demo.bo.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "userServlet", value = "/user-servlet")
public class UserServlet extends HttpServlet {

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
            default:
                response.sendRedirect("error.jsp");  // Handle unknown actions
                break;
        }
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

    // Registreringshantering
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
}
