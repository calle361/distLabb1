package org.example.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager{

    private static final String DEFAULT_DB = "webshop";

    private String username = "root";
    private String password = "123";
    private static Connection connection;

    public static String getDefaultDatabase() {
        return DEFAULT_DB;
    }

    /**
     * Skapar en anslutning till databasen med angivet namn
     * @param database Namnet på databasen
     * @return true om anslutningen lyckades
     * @throws SQLException om anslutningsproblem uppstår
     */
    public boolean connect(String database) throws SQLException {
        try {
            // Ladda JDBC-drivrutinen
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Etablera anslutningen
            String url = "jdbc:mysql://localhost:3306/" + database;
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Ansluten till databasen: " + database);
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC-drivrutin saknas: " + e.getMessage());
            return false;
        }
    }

    /**
     * Kopplar från databasen om anslutningen är öppen
     * @throws SQLException om problem uppstår vid stängning
     */
    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Anslutningen stängd.");
        }
    }

    /**
     * Returnerar den nuvarande anslutningen
     * @return Databasanslutningen
     */
    public static Connection getConnection() {
        return connection;
    }
}
