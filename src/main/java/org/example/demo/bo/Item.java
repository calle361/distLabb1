package org.example.demo.bo;

import org.example.demo.db.CategoryDB;
import org.example.demo.db.DBManager;
import org.example.demo.db.ItemDB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class Item {
    private String name;
    private int price;
    private int id;
    private int amount; // Or consider renaming to stock
    private String description;
    private int categoryId;

    // Constructor to initialize Item object
    public Item(int id, String name, String description, int price, int amount, int categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.categoryId = categoryId;
    }
/*
    // Static method to get all items from the database
    public static Collection<Item> getItems() {
        Collection<Item> items = new ArrayList<>();
        try {
            items = ItemDB.getItems();  // Fetch items and catch any SQLException that occurs
        } catch (SQLException e) {
            e.printStackTrace();  // Print the stack trace or log it
            // You can also handle the error gracefully here
        }
        return items;  // Return an empty collection or partial results in case of an error
    }

 */

    /*
    public static Item getItem(int id) {
        Item item = null;
        try{
            item=ItemDB.getItem(,id);
        } catch (SQLException e) {
            e.printStackTrace();  // Print the stack trace or log it
            // You can also handle the error gracefully here
        }
        return item;
    }

     */
    public Category getCategory() {
        Category category = null;
        try {
            category = CategoryDB.getCategoryById(DBManager.getConnection(), this.categoryId);
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the error gracefully
        }
        return category;
    }

    // Getters and setters for the fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
