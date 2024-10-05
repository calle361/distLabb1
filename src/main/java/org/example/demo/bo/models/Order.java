package org.example.demo.bo.models;

import java.sql.Timestamp;

/**
 * Represents an order with attributes such as order ID, total price, user ID, and date.
 */
public class Order {
    int Oid;
    int price;
    int userid;
    Timestamp date;

    /**
     * Constructs an Order with the specified attributes.
     *
     * @param oid    the unique identifier for the order
     * @param price  the total price of the order
     * @param userid the ID of the user who placed the order
     * @param date   the date and time when the order was placed
     */
    public Order(int oid, int price, int userid,Timestamp date) {
        Oid = oid;
        this.price = price;
        this.userid = userid;
        this.date = date;

    }

    /**
     * Returns the date and time when the order was placed.
     *
     * @return the order's timestamp
     */
    public Timestamp getDate() {
        return date;
    }

    /**
     * Returns the unique identifier of the order.
     *
     * @return the order's ID
     */
    public int getOid() {
        return Oid;
    }

    /**
     * Sets the unique identifier of the order.
     *
     * @param oid the new ID for the order
     */
    public void setOid(int oid) {
        Oid = oid;
    }

    /**
     * Returns the total price of the order.
     *
     * @return the order's price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the total price of the order.
     *
     * @param price the new total price of the order
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Returns the ID of the user who placed the order.
     *
     * @return the user's ID
     */
    public int getUserid() {
        return userid;
    }

    /**
     * Sets the ID of the user who placed the order.
     *
     * @param userid the new ID of the user
     */
    public void setUserid(int userid) {
        this.userid = userid;
    }
}
