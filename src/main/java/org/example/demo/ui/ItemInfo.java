package org.example.demo.ui;

public class ItemInfo {
    private int id;
    private String name;
    private String description;
    private int price;
    private int amount;


    public ItemInfo(int id, String name,String description,int price,int amount){
        this.id=id;
        this.name=name;
        this.description=description;
        this.price=price;
        this.amount=amount;
    }

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
}
