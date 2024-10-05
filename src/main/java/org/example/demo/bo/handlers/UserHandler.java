package org.example.demo.bo.handlers;

import org.example.demo.bo.PermissionLevel;
import org.example.demo.bo.models.User;
import org.example.demo.db.DBManager;
import org.example.demo.db.UserDB;
import org.example.demo.ui.facades.UserInfo;

import java.sql.Connection;
import java.sql.SQLException;

public class UserHandler {

    static DBManager dbManager;

    // Logga in en användare med username och password
    public static UserInfo loginUser(String username, String password) {
        User user = null;
        UserInfo userInfo = null;
        try (Connection connection = dbManager.getConnection()) {
            user = UserDB.login(connection, username, password);
            userInfo=new UserInfo(user.getUid(),user.getName(),user.getPermissionLevel());
        } catch (SQLException e) {
            System.err.println("Login fel: " + e.getMessage());
        }
        return userInfo;
    }

    // Registrera en ny användare
    public static UserInfo registerUser(String username, String password, PermissionLevel permissionLevel) {
        User user = null;
        UserInfo userInfo = null;
        try (Connection connection = dbManager.getConnection()) {
            user = UserDB.register(connection, username, password, permissionLevel);
            userInfo=new UserInfo(user.getUid(),user.getName(),user.getPermissionLevel());
        } catch (SQLException e) {
            System.err.println("Registreringsfel: " + e.getMessage());
        }
        return userInfo;
    }

    public static UserInfo findUserByUsername(String username) {
        User foundUser = null;
        UserInfo userInfo = null;
        try (Connection connection = DBManager.getConnection()) {
            foundUser = UserDB.findUserByUsername(connection, username);
            userInfo=new UserInfo(foundUser.getUid(),foundUser.getName(),foundUser.getPermissionLevel());
        } catch (SQLException e) {
            System.err.println("Error searching for user: " + e.getMessage());
        }
        return userInfo;
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
}
