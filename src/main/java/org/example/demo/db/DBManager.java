package org.example.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DBManager class is responsible for managing the database connection.
 * It uses a Singleton pattern to ensure only one connection is used throughout the application.
 * This class supports connecting, disconnecting, and retrieving the connection to a MySQL database.
 */
public class DBManager {

    private static final String DEFAULT_DB = "webshop";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123";
    private static Connection connection;

    /**
     * Returns the active database connection. If the connection is closed or null, it attempts to reconnect to the default database.
     *
     * @return The active {@link Connection} object to the database.
     * @throws SQLException If a database access error occurs or the connection cannot be established.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect(DEFAULT_DB);  // Attempt to reconnect if the connection is null or closed.
        }
        return connection;
    }

    /**
     * Establishes a connection to the specified database.
     *
     * @param database The name of the database to connect to.
     * @return {@code true} if the connection was successfully established, {@code false} if the JDBC driver is missing.
     * @throws SQLException If a database access error occurs or the connection cannot be established.
     */
    public static boolean connect(String database) throws SQLException {
        try {
            // Load the JDBC driver for MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            String url = "jdbc:mysql://localhost:3306/" + database;
            connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
            System.out.println("Connected to database: " + database);
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found: " + e.getMessage());
            return false;
        }
    }

    /**
     * Disconnects from the database if the connection is open.
     *
     * @throws SQLException If an error occurs while closing the connection.
     */
    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Connection closed.");
        }
    }

    /**
     * Returns the default database name.
     *
     * @return The default database name as a {@link String}.
     */
    public static String getDefaultDatabase() {
        return DEFAULT_DB;
    }
}
