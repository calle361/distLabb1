package org.example.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    private static final String DEFAULT_DB = "webshop";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    private static Connection connection;

    // Singleton-mönster: Ser till att vi bara har en anslutning.
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect(DEFAULT_DB);  // Om anslutningen är null eller stängd, försök att återansluta
        }
        return connection;
    }

    /**
     * Skapar en anslutning till databasen med angivet namn
     * @param database Namnet på databasen
     * @return true om anslutningen lyckades
     * @throws SQLException om anslutningsproblem uppstår
     */
    public static boolean connect(String database) throws SQLException {
        try {
            // Ladda JDBC-drivrutinen
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Etablera anslutningen
            String url = "jdbc:mysql://localhost:3306/" + database;
            connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
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
    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Anslutningen stängd.");
        }
    }

    /**
     * Returnerar standarddatabasnamnet
     * @return Namnet på standarddatabasen
     */
    public static String getDefaultDatabase() {
        return DEFAULT_DB;
    }
}
