package org.example.demo.bo;

import org.example.demo.db.ItemDB;

import java.util.Collection;

public class Item {
    public String getDescription;
    private String name;
    private int price;
    private int id;
    private int amount;
    private String description;

    static public Collection getItems(){
        return ItemDB.getItems();
    }

    protected Item(int id, String name,String description, int price, int amount) {
        this.id = id;
        this.name = name;
        this.description=description;
        this.price = price;
        this.amount = amount;
    }

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
}
