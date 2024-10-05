package org.example.demo.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.demo.bo.models.Category;

/**
 * The CategoryDB class provides database operations related to categories.
 * This class handles CRUD (Create, Read, Update, Delete) operations for
 * the "Category" table in the database.
 */
public class CategoryDB {

    /**
     * Retrieves all categories from the database.
     *
     * @param con The {@link Connection} object representing the database connection.
     * @return A {@link List} of {@link Category} objects containing all the categories in the database.
     * @throws SQLException If an error occurs while fetching data from the database.
     */
    public static List<Category> getAllCategories(Connection con) throws SQLException {
        String query = "SELECT id, name FROM Category";
        List<Category> categories = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                categories.add(new Category(id, name));
            }
        }
        return categories;
    }

    /**
     * Adds a new category to the database.
     *
     * @param con The {@link Connection} object representing the database connection.
     * @param name The name of the new category to be added.
     * @throws SQLException If an error occurs while inserting the new category.
     */
    public static void addCategory(Connection con, String name) throws SQLException {
        String query = "INSERT INTO Category (name) VALUES (?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, name);
            ps.executeUpdate();
        }
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param con The {@link Connection} object representing the database connection.
     * @param id The ID of the category to be fetched.
     * @return A {@link Category} object representing the category with the specified ID,
     *         or {@code null} if the category does not exist.
     * @throws SQLException If an error occurs while fetching the category.
     */
    public static Category getCategoryById(Connection con, int id) throws SQLException {
        String query = "SELECT id, name FROM Category WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                return new Category(id, name);
            }
        }
        return null;
    }

    /**
     * Updates the name of a category in the database.
     *
     * @param connection The {@link Connection} object representing the database connection.
     * @param categoryId The ID of the category to be updated.
     * @param newName The new name to update the category to.
     * @throws SQLException If an error occurs while updating the category.
     */
    public static void updateCategory(Connection connection, int categoryId, String newName) throws SQLException {
        String query = "UPDATE Category SET name = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newName);
            ps.setInt(2, categoryId);
            ps.executeUpdate();
        }
    }
}