package org.example.demo.bo;

import org.example.demo.db.CategoryDB;
import org.example.demo.db.DBManager;
import org.example.demo.db.ItemDB;
import org.example.demo.ui.facades.ItemInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemHandler {
    static DBManager dbManager = Model.getDBManager();
    //static Connection conn=Model.getConnection();



    public static Collection<ItemInfo> getAllItems() throws SQLException {
        // Use generics to specify type safety
        Connection conn = dbManager.getConnection();

        Collection<Item> itemsFromDb = ItemDB.getItems(conn);
        List<ItemInfo> items = new ArrayList<>();

        // Use enhanced for-loop for better readability
        for (Item item : itemsFromDb) {
            // Hämta kategori för varje produkt
            Category category = CategoryDB.getCategoryById(conn, item.getCategoryId());
            items.add(new ItemInfo(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),  // Corrected to method call
                    item.getPrice(),
                    item.getAmount(),
                    category  // Lägg till kategori i ItemInfo
            ));
        }

        return items;
    }
    public static ItemInfo getItemById(int id) throws SQLException {
        Item itemFromDb = ItemDB.getItem(dbManager.getConnection(), id);
        Category category = CategoryDB.getCategoryById(dbManager.getConnection(), itemFromDb.getCategoryId());

        return new ItemInfo(itemFromDb.getId(), itemFromDb.getName(), itemFromDb.getDescription(), itemFromDb.getPrice(), itemFromDb.getAmount(), category);
    }
    public static int getstockById(int id) throws SQLException {
        Item itemFromDb=ItemDB.getItem(dbManager.getConnection(),id);
        int stock=itemFromDb.getAmount();
        return stock;

    }
    public static int getPriceById(int id) throws SQLException {
        Item itemFromDb=ItemDB.getItem(dbManager.getConnection(),id);
        int price=itemFromDb.getPrice();
        return price;
    }
    // Add a new item to the inventory
    public static void addItem(String name, String description, double price, int stock, int categoryId) throws SQLException {
        try (var connection = dbManager.getConnection()) {
            ItemDB.addItem(connection, name, description, price, stock, categoryId);
        }
    }

    // Update the stock of an existing item
    public static void updateStock(int itemId, int newStock) throws SQLException {
            try (var connection2 =dbManager.getConnection()) {
                ItemDB.updateStock(connection2, itemId, newStock);
            }
    }

    // Uppdatera lagret för en befintlig produkt (används internt)
    public static void updateStock2(Connection connection, int itemId, int newStock) throws SQLException {
        ItemDB.updateStock(connection, itemId, newStock);
    }

    // Hämta alla kategorier
    public static List<Category> getAllCategories() throws SQLException {
        try (Connection connection = dbManager.getConnection()) {
            return CategoryDB.getAllCategories(connection);
        }
    }

    // Lägg till en ny kategori
    public static void addCategory(String name) throws SQLException {
        try (Connection connection = dbManager.getConnection()) {
            CategoryDB.addCategory(connection, name);
        }
    }

    // Hämta en kategori baserat på ID
    public static Category getCategoryById(int id) throws SQLException {
        try (Connection connection = dbManager.getConnection()) {
            return CategoryDB.getCategoryById(connection, id);
        }
    }
    public static void updateItem(int itemId, String newName, int categoryId) throws SQLException {
        try (Connection connection = dbManager.getConnection()) {
            ItemDB.updateItem(connection, itemId, newName, categoryId);
        }
    }

    public static void updateCategory(int categoryId, String newName) throws SQLException {
        try (Connection connection = dbManager.getConnection()) {
            CategoryDB.updateCategory(connection, categoryId, newName);
        }
    }


}
