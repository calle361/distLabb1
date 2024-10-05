package org.example.demo.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.demo.bo.models.Category;

public class CategoryDB {

    // Hämta alla kategorier från databasen
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

    // Lägg till en ny kategori
    public static void addCategory(Connection con, String name) throws SQLException {
        String query = "INSERT INTO Category (name) VALUES (?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, name);
            ps.executeUpdate();
        }
    }

    // Hämta en kategori genom id
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

    public static void updateCategory(Connection connection, int categoryId, String newName) throws SQLException {
        String query = "UPDATE Category SET name = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newName);
            ps.setInt(2, categoryId);
            ps.executeUpdate();
        }
    }

}
