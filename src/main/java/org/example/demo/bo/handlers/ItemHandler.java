package org.example.demo.bo.handlers;

import org.example.demo.bo.models.Category;
import org.example.demo.bo.models.Item;
import org.example.demo.db.CategoryDB;
import org.example.demo.db.DBManager;
import org.example.demo.db.ItemDB;
import org.example.demo.ui.facades.CategoryInfo;
import org.example.demo.ui.facades.ItemInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * The ItemHandler class provides methods to manage items and categories in the inventory.
 * It interacts with the database to retrieve, add, and update items and categories.
 * This class acts as a handler between the business logic and the database layer (DBManager, ItemDB, CategoryDB).
 */
public class ItemHandler {
    static DBManager dbManager;

    /**
     * Retrieves all items from the database along with their associated category.
     *
     * @return A collection of {@link ItemInfo} containing details about all the items and their categories.
     * @throws SQLException If an SQL error occurs during the retrieval process.
     */
    public static Collection<ItemInfo> getAllItems() throws SQLException {
        Connection conn = dbManager.getConnection();

        Collection<Item> itemsFromDb = ItemDB.getItems(conn);
        List<ItemInfo> items = new ArrayList<>();

        for (Item item : itemsFromDb) {
            Category category = CategoryDB.getCategoryById(conn, item.getCategoryId());
            items.add(new ItemInfo(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getPrice(),
                    item.getAmount(),
                    category
            ));
        }
        return items;
    }

    /**
     * Retrieves a specific item by its ID.
     *
     * @param id The ID of the item to retrieve.
     * @return An {@link ItemInfo} object containing details about the specified item.
     * @throws SQLException If an SQL error occurs during the retrieval process.
     */
    public static ItemInfo getItemById(int id) throws SQLException {
        Item itemFromDb = ItemDB.getItem(dbManager.getConnection(), id);
        Category category = CategoryDB.getCategoryById(dbManager.getConnection(), itemFromDb.getCategoryId());

        return new ItemInfo(itemFromDb.getId(), itemFromDb.getName(), itemFromDb.getDescription(), itemFromDb.getPrice(), itemFromDb.getAmount(), category);
    }
    /**
     * Retrieves the stock quantity for a specific item by its ID.
     *
     * @param id The ID of the item.
     * @return The stock amount of the item.
     * @throws SQLException If an SQL error occurs during the retrieval process.
     */
    public static int getstockById(int id) throws SQLException {
        Item itemFromDb=ItemDB.getItem(dbManager.getConnection(),id);
        int stock=itemFromDb.getAmount();
        return stock;
    }

    /**
     * Retrieves the price for a specific item by its ID.
     *
     * @param id The ID of the item.
     * @return The price of the item.
     * @throws SQLException If an SQL error occurs during the retrieval process.
     */
    public static int getPriceById(int id) throws SQLException {
        Item itemFromDb=ItemDB.getItem(dbManager.getConnection(),id);
        int price=itemFromDb.getPrice();
        return price;
    }

    /**
     * Adds a new item to the inventory.
     *
     * @param name The name of the item.
     * @param description A description of the item.
     * @param price The price of the item.
     * @param stock The stock quantity of the item.
     * @param categoryId The ID of the category to which the item belongs.
     * @throws SQLException If an SQL error occurs during the insertion process.
     */
    // Add a new item to the inventory
    public static void addItem(String name, String description, double price, int stock, int categoryId) throws SQLException {
        try (var connection = dbManager.getConnection()) {
            ItemDB.addItem(connection, name, description, price, stock, categoryId);
        }
    }


    /**
     * Updates the stock quantity of an existing item.
     *
     * @param itemId The ID of the item to update.
     * @param newStock The new stock quantity for the item.
     * @throws SQLException If an SQL error occurs during the update process.
     */
    // Update the stock of an existing item
    public static void updateStock(int itemId, int newStock) throws SQLException {
            try (var connection2 =dbManager.getConnection()) {
                ItemDB.updateStock(connection2, itemId, newStock);
            }
    }


    /**
     * Updates the stock quantity of an existing item using an existing connection.
     *
     * @param connection The active database connection to use.
     * @param itemId The ID of the item to update.
     * @param newStock The new stock quantity for the item.
     * @throws SQLException If an SQL error occurs during the update process.
     */
    // Uppdatera lagret för en befintlig produkt (används internt)
    public static void updateStock2(Connection connection, int itemId, int newStock) throws SQLException {
        ItemDB.updateStock(connection, itemId, newStock);
    }


    /**
     * Retrieves all categories from the database.
     *
     * @return A list of {@link Category} objects containing details about all categories.
     * @throws SQLException If an SQL error occurs during the retrieval process.
     */
    // Hämta alla kategorier
    public static List<CategoryInfo> getAllCategories() throws SQLException {
        try (Connection connection = dbManager.getConnection()) {
            List<Category> category=CategoryDB.getAllCategories(connection);
            List<CategoryInfo> categoryInfos = new ArrayList<>();
            for (Category c : category) {
                categoryInfos.add(new CategoryInfo(c.getId(),c.getName()));
            }
            return categoryInfos;
        }
    }


    /**
     * Adds a new category to the database.
     *
     * @param name The name of the category.
     * @throws SQLException If an SQL error occurs during the insertion process.
     */
    // Lägg till en ny kategori
    public static void addCategory(String name) throws SQLException {
        try (Connection connection = dbManager.getConnection()) {
            CategoryDB.addCategory(connection, name);
        }
    }

    /**
     * Retrieves a specific category by its ID.
     *
     * @param id The ID of the category to retrieve.
     * @return A {@link Category} object containing details about the specified category.
     * @throws SQLException If an SQL error occurs during the retrieval process.
     */
    // Hämta en kategori baserat på ID
    public static CategoryInfo getCategoryById(int id) throws SQLException {
        try (Connection connection = dbManager.getConnection()) {
            Category category=CategoryDB.getCategoryById(connection, id);
            CategoryInfo c=new CategoryInfo(category.getId(), category.getName());
            return c;
        }
    }
    /**
     * Updates the name and category of an existing item.
     *
     * @param itemId The ID of the item to update.
     * @param newName The new name for the item.
     * @param categoryId The ID of the new category for the item.
     * @throws SQLException If an SQL error occurs during the update process.
     */
    public static void updateItem(int itemId, String newName, int categoryId) throws SQLException {
        try (Connection connection = dbManager.getConnection()) {
            ItemDB.updateItem(connection, itemId, newName, categoryId);
        }
    }

    /**
     * Updates the name of an existing category.
     *
     * @param categoryId The ID of the category to update.
     * @param newName The new name for the category.
     * @throws SQLException If an SQL error occurs during the update process.
     */
    public static void updateCategory(int categoryId, String newName) throws SQLException {
        try (Connection connection = dbManager.getConnection()) {
            CategoryDB.updateCategory(connection, categoryId, newName);
        }
    }
}
