package org.example.demo.bo;

import org.example.demo.bo.PermissionLevel;
import org.example.demo.bo.User;
import org.example.demo.db.DBManager;
import org.example.demo.db.UserDB;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Model-klass som hanterar applikationens affärslogik och databasoperationer
 */
public class Model {

    static DBManager dbManager;

    // Initiera databasen vid uppstart
    public static boolean initialize() {
        dbManager = new DBManager();
        try {
            return dbManager.connect(DBManager.getDefaultDatabase());
        } catch (SQLException e) {
            System.err.println("Kunde inte ansluta till databasen: " + e.getMessage());
            return false;
        }
    }

    // Stäng anslutningen till databasen vid nedstängning
    public static void shutdown() {
        try {
            dbManager.disconnect();
        } catch (SQLException e) {
            System.err.println("Kunde inte stänga anslutningen: " + e.getMessage());
        }
    }

    // Logga in en användare med username och password
    public static User loginUser(String username, String password) {
        User user = null;
        try (Connection connection = dbManager.getConnection()) {
            user = UserDB.login(connection, username, password);
        } catch (SQLException e) {
            System.err.println("Login fel: " + e.getMessage());
        }
        return user;
    }

    // Registrera en ny användare
    public static User registerUser(String username, String password, PermissionLevel permissionLevel) {
        User user = null;
        try (Connection connection = dbManager.getConnection()) {
            user = UserDB.register(connection, username, password, permissionLevel);
        } catch (SQLException e) {
            System.err.println("Registreringsfel: " + e.getMessage());
        }
        return user;
    }

    public static User findUserByUsername(String username) {
        User foundUser = null;
        try (Connection connection = DBManager.getConnection()) {
            foundUser = UserDB.findUserByUsername(connection, username);
        } catch (SQLException e) {
            System.err.println("Error searching for user: " + e.getMessage());
        }
        return foundUser;
    }

    public static boolean updateUserRole(String username, PermissionLevel newPermissionLevel) {
        boolean isUpdated = false;
        try (Connection connection = DBManager.getConnection()) {
            isUpdated = UserDB.updateUserRole(connection, username, newPermissionLevel);
        } catch (SQLException e) {
            System.err.println("Error updating user role: " + e.getMessage());
        }
        return isUpdated;
    }

    public static DBManager getDBManager() {
        return dbManager;
    }
}

