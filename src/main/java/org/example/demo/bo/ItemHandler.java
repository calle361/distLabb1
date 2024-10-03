package org.example.demo.bo;

import org.example.demo.db.DBManager;
import org.example.demo.db.ItemDB;
import org.example.demo.ui.ItemInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemHandler {
    //static DBManager dbManager = Model.getDBManager();
    static Connection conn=Model.getConnection();
    public static Collection<ItemInfo> getAllItems() throws SQLException {
        // Use generics to specify type safety
        Collection<Item> itemsFromDb = ItemDB.getItems(conn);
        List<ItemInfo> items = new ArrayList<>();

        // Use enhanced for-loop for better readability
        for (Item item : itemsFromDb) {
            items.add(new ItemInfo(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),  // Corrected to method call
                    item.getPrice(),
                    item.getAmount()
            ));
        }

        return items;
    }
    public static ItemInfo getItemById(int id) throws SQLException {
        Item itemFromDb=ItemDB.getItem(conn,id);
        ItemInfo itemInfo=new ItemInfo(itemFromDb.getId(), itemFromDb.getName(), itemFromDb.getDescription(), itemFromDb.getPrice(), itemFromDb.getAmount());
        return itemInfo;
    }
    public static int getstockById(int id) throws SQLException {
        Item itemFromDb=ItemDB.getItem(conn,id);
        int stock=itemFromDb.getAmount();
        return stock;

    }
    // Add a new item to the inventory
    public static void addItem(String name, String description, double price, int stock) throws SQLException {
        try (var connection = conn) {
            ItemDB.addItem(connection, name, description, price, stock);
        }
    }

    // Update the stock of an existing item
    public static void updateStock(int itemId, int newStock) throws SQLException {
            try (var connection2 =conn) {
                ItemDB.updateStock(connection2, itemId, newStock);
            }
    }
    public static void updateStock2(Connection connection,int itemId, int newStock) throws SQLException {
        try (var connection2 =connection ) {
            ItemDB.updateStock(connection2, itemId, newStock);
        }
    }
}
