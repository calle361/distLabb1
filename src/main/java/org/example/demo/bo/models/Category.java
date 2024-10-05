package org.example.demo.bo.models;

/**
 * Represents a category with an id and a name.
 */
public class Category {
    private int id;
    private String name;

    /**
     * Constructs a Category with the specified id and name.
     *
     * @param id    the unique identifier of the category
     * @param name  the name of the category
     */
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the id of the category.
     *
     * @return the id of the category
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the category.
     *
     * @param id the new id for the category
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the category.
     *
     * @return the name of the category
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the category.
     *
     * @param name the new name for the category
     */
    public void setName(String name) {
        this.name = name;
    }
}
