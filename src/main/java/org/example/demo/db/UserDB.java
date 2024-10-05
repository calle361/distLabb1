package org.example.demo.db;

import org.example.demo.bo.PermissionLevel;
import org.example.demo.bo.models.User;

import java.sql.*;

/**
 * The UserDB class provides static methods to interact with the database for operations
 * related to users, such as login, registration, updating user roles, and retrieving user details.
 */
public class UserDB {

    /**
     * Attempts to log in a user by checking the provided username and password against the database.
     *
     * @param connection The active {@link Connection} to the database.
     * @param username The username of the user trying to log in.
     * @param password The password of the user.
     * @return A {@link User} object representing the logged-in user, or {@code null} if the login fails.
     * @throws SQLException If there is an error during the login process or database interaction.
     */
    public static User login(Connection connection, String username, String password) throws SQLException {
        User result = null;

        String query = "SELECT id, permissionlevel FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement userStmt = connection.prepareStatement(query)) {
            userStmt.setString(1, username);
            userStmt.setString(2, password);

            ResultSet resultSet = userStmt.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("id");
                PermissionLevel permissionLevel = PermissionLevel.valueOf(resultSet.getString("permissionlevel"));
                result = new User(userId, username, permissionLevel);
            }
        }

        return result;
    }

    /**
     * Registers a new user in the database.
     *
     * @param connection The active {@link Connection} to the database.
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @param permissionLevel The permission level of the new user.
     * @return A {@link User} object representing the newly registered user.
     * @throws SQLException If there is an error during the registration process or database interaction.
     */
    public static User register(Connection connection, String username, String password,
                                PermissionLevel permissionLevel) throws SQLException {
        User result;

        String query = "INSERT INTO users (username, password, permissionlevel) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, permissionLevel.toString());
            stmt.executeUpdate();

            int userId;
            var keysResultSet = stmt.getGeneratedKeys();
            if (keysResultSet.next()) {
                userId = keysResultSet.getInt(1);
            } else {
                throw new SQLException("Failed to fetch generated user id");
            }
            result = new User(userId, username, permissionLevel);
            connection.commit();
        } catch (SQLException sqle) {
            connection.rollback();
            throw new SQLException(sqle.getMessage());
        } finally {
            connection.setAutoCommit(true);
        }

        return result;
    }

    /**
     * Updates the role (permission level) of a user based on their username.
     *
     * @param connection The active {@link Connection} to the database.
     * @param username The username of the user whose role needs to be updated.
     * @param newPermissionLevel The new {@link PermissionLevel} to assign to the user.
     * @return {@code true} if the user's role was successfully updated, {@code false} otherwise.
     * @throws SQLException If there is an error updating the user's role in the database.
     */
    public static boolean updateUserRole(Connection connection, String username, PermissionLevel newPermissionLevel) throws SQLException {
        String query = "UPDATE users SET permissionlevel = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPermissionLevel.toString());
            stmt.setString(2, username);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Finds a user in the database based on their username.
     *
     * @param connection The active {@link Connection} to the database.
     * @param username The username to search for.
     * @return A {@link User} object representing the found user, or {@code null} if no user is found.
     * @throws SQLException If there is an error during the search process or database interaction.
     */
    public static User findUserByUsername(Connection connection, String username) throws SQLException {
        User foundUser = null;

        String query = "SELECT id, username, permissionlevel FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                String foundUsername = rs.getString("username");
                PermissionLevel permissionLevel = PermissionLevel.valueOf(rs.getString("permissionlevel"));

                foundUser = new User(userId, foundUsername, permissionLevel);
            }
        }

        return foundUser;
    }

    /**
     * Retrieves the user ID of a user based on their username.
     *
     * @param connection The active {@link Connection} to the database.
     * @param username The username of the user.
     * @return The user ID of the user.
     * @throws SQLException If there is an error fetching the user ID from the database.
     */
    public static int getUserIdByUsername(Connection connection, String username) throws SQLException {
        User user = findUserByUsername(connection, username);
        return user.getUid();
    }
}
