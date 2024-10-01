package org.example.demo.db;

import org.example.demo.bo.PermissionLevel;
import org.example.demo.bo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class UserDB {
    public static User login(Connection connection, String username, String password) throws SQLException {
        User result = null;

        String query = "SELECT id, permissionlevel FROM user WHERE name = ? AND password = ?";
        try(PreparedStatement userStmt = connection.prepareStatement(query)) {
          userStmt.setString(1,username);
          userStmt.setString(2,password);

          ResultSet resultSet = userStmt.executeQuery();
          if(resultSet.next()) {
              int userId = resultSet.getInt("id");
              PermissionLevel permissionLevel = PermissionLevel.valueOf(resultSet.getString("permissionlevel"));
              result = new User(userId, username, permissionLevel);
          }
        }

        return result;
    }

    public static User register(Connection connection, String username, String password,
                             PermissionLevel permissionLevel) throws SQLException {
        User result;

        String query = "INSERT INTO user (name, password, permissionlevel) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, permissionLevel.toString());
            stmt.executeUpdate();

            int userId;
            var keysResultSet = stmt.getGeneratedKeys();
            if(keysResultSet.next()) {
                userId = keysResultSet.getInt(1);
            }
            else {
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
}
