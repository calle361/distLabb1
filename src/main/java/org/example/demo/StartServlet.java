package org.example.demo;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import org.example.demo.bo.Model;
import org.example.demo.db.DBManager;
import org.example.demo.db.ItemDB;
import org.example.demo.db.OrderDB;
import org.example.demo.bo.ItemHandler;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.demo.bo.OrderHandler;
import org.example.demo.db.DBManager;

@WebServlet(name = "StartServlet", value = "/start")
public class StartServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {

        System.out.println("INITIALIZE\n\n\n\n\n");

        Model.initialize();
        super.init();
    }

    @Override
    public void destroy() {
        super.destroy();
        Model.shutdown();

        System.out.println("SHUTDOWN");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

}