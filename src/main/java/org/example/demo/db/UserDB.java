package org.example.demo.db;

import org.example.demo.bo.PermissionLevel;
import org.example.demo.bo.User;

import java.sql.*;

public class UserDB {

    // Login method
    public static User login(Connection connection, String username, String password) throws SQLException {
        User result = null;

        // Update to refer to 'users' table
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

    // Register method
    public static User register(Connection connection, String username, String password,
                                PermissionLevel permissionLevel) throws SQLException {
        User result;

        // Update to refer to 'users' table
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

    public static boolean updateUserRole(Connection connection, String username, PermissionLevel newPermissionLevel) throws SQLException {
        String query = "UPDATE users SET permissionlevel = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPermissionLevel.toString());
            stmt.setString(2, username);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
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

}
