package org.example.demo.bo.models;

import org.example.demo.db.CategoryDB;
import org.example.demo.db.DBManager;

import java.sql.SQLException;

/**
 * Represents an item with various attributes such as name, price, description,
 * and category. It also contains a reference to the item's stock (amount).
 */
public class Item {
    private String name;
    private int price;
    private int id;
    private int amount; // Or consider renaming to stock
    private String description;
    private int categoryId;

    /**
     * Constructs an Item with the specified attributes.
     *
     * @param id          the unique identifier of the item
     * @param name        the name of the item
     * @param description the description of the item
     * @param price       the price of the item
     * @param amount      the amount or stock of the item
     * @param categoryId  the ID of the category this item belongs to
     */
    public Item(int id, String name, String description, int price, int amount, int categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.categoryId = categoryId;
    }

    /**
     * Retrieves the category associated with this item by using the categoryId.
     *
     * @return the category of the item, or null if the category could not be fetched
     */
    public Category getCategory() {
        Category category = null;
        try {
            category = CategoryDB.getCategoryById(DBManager.getConnection(), this.categoryId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    /**
     * Returns the name of the item.
     *
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item.
     *
     * @param name the new name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the price of the item.
     *
     * @return the price of the item
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the price of the item.
     *
     * @param price the new price of the item
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Returns the unique identifier of the item.
     *
     * @return the id of the item
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the item.
     *
     * @param id the new id of the item
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the amount or stock of the item.
     *
     * @return the amount of the item
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the amount or stock of the item.
     *
     * @param amount the new amount or stock of the item
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Returns the description of the item.
     *
     * @return the description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the item.
     *
     * @param description the new description of the item
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the categoryId of the item.
     *
     * @return the categoryId of the item
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the categoryId of the item.
     *
     * @param categoryId the new categoryId of the item
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
