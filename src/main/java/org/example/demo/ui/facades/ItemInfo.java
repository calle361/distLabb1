package org.example.demo.ui.facades;

import org.example.demo.bo.models.Category;

/**
 * The ItemInfo class represents detailed information about an item, including
 * its ID, name, description, price, quantity, and associated category.
 */
public class ItemInfo {
    private int id;
    private String name;
    private String description;
    private int price;
    private int amount;
    private Category category;

    /**
     * Constructor to initialize ItemInfo with its details including Category.
     *
     * @param id The unique identifier of the item.
     * @param name The name of the item.
     * @param description A description of the item.
     * @param price The price of the item.
     * @param amount The quantity of the item.
     * @param category The {@link Category} object representing the item's category.
     */
    public ItemInfo(int id, String name, String description, int price, int amount, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.category = category;
    }

    /**
     * Returns the unique identifier of the item.
     *
     * @return The item ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the item.
     *
     * @param id The item ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the item.
     *
     * @return The item name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item.
     *
     * @param name The item name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the item.
     *
     * @return The item description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the item.
     *
     * @param description The item description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the price of the item.
     *
     * @return The item price.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the price of the item.
     *
     * @param price The item price to set.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Returns the quantity of the item.
     *
     * @return The item quantity.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the quantity of the item.
     *
     * @param amount The item quantity to set.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Returns the category of the item.
     *
     * @return The {@link Category} object representing the item's category.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the category of the item.
     *
     * @param category The {@link Category} object to set for the item.
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Helper method to get the category name.
     * If the category is null, it returns "Unknown".
     *
     * @return The name of the category or "Unknown" if the category is not set.
     */
    public String getCategoryName() {
        return category != null ? category.getName() : "Unknown";
    }
}