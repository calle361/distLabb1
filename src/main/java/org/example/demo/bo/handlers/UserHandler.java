package org.example.demo.bo.handlers;

import org.example.demo.bo.PermissionLevel;
import org.example.demo.bo.models.User;
import org.example.demo.db.DBManager;
import org.example.demo.db.UserDB;
import org.example.demo.ui.facades.UserInfo;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The UserHandler class handles user-related operations such as logging in, registering users,
 * finding users by username, and updating user roles. It serves as an interface between the
 * application logic and the database operations defined in the {@link UserDB} class.
 */
public class  UserHandler {

    static DBManager dbManager;

    /**
     * Logs in a user by verifying the provided username and password.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return A {@link UserInfo} object containing the user's ID, name, and permission level,
     *         or {@code null} if the login fails.
     */
    public static UserInfo loginUser(String username, String password) {
        User user = null;
        UserInfo userInfo = null;
        try (Connection connection = dbManager.getConnection()) {
            user = UserDB.login(connection, username, password);
            userInfo = new UserInfo(user.getUid(), user.getName(), user.getPermissionLevel());
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
        }
        return userInfo;
    }

    /**
     * Registers a new user with the provided username, password, and permission level.
     *
     * @param username The username for the new user.
     * @param password The password for the new user.
     * @param permissionLevel The permission level assigned to the new user.
     * @return A {@link UserInfo} object containing the user's ID, name, and permission level,
     *         or {@code null} if the registration fails.
     */
    public static UserInfo registerUser(String username, String password, PermissionLevel permissionLevel) {
        User user = null;
        UserInfo userInfo = null;
        try (Connection connection = dbManager.getConnection()) {
            user = UserDB.register(connection, username, password, permissionLevel);
            userInfo = new UserInfo(user.getUid(), user.getName(), user.getPermissionLevel());
        } catch (SQLException e) {
            System.err.println("Registration error: " + e.getMessage());
        }
        return userInfo;
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to search for.
     * @return A {@link UserInfo} object containing the user's ID, name, and permission level,
     *         or {@code null} if the user is not found.
     */
    public static UserInfo findUserByUsername(String username) {
        User foundUser = null;
        UserInfo userInfo = null;
        try (Connection connection = DBManager.getConnection()) {
            foundUser = UserDB.findUserByUsername(connection, username);
            userInfo = new UserInfo(foundUser.getUid(), foundUser.getName(), foundUser.getPermissionLevel());
        } catch (SQLException e) {
            System.err.println("Error searching for user: " + e.getMessage());
        }
        return userInfo;
    }

    /**
     * Updates the role (permission level) of an existing user.
     *
     * @param username The username of the user whose role is being updated.
     * @param newPermissionLevel The new {@link PermissionLevel} to assign to the user.
     * @return {@code true} if the update is successful, {@code false} otherwise.
     */
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
