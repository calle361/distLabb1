package org.example.demo.ui.facades;

import org.example.demo.bo.models.Category;

public class ItemInfo {
    private int id;
    private String name;
    private String description;
    private int price;
    private int amount;
    private Category category;  // Added Category

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

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

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