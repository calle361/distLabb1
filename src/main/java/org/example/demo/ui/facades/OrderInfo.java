package org.example.demo.ui.facades;

import java.sql.Timestamp;

/**
 * Represents an order with associated details such as order ID, price,
 * user ID, and the date when the order was placed.
 */
public class OrderInfo {

    private int orderId;
    private int price;
    private int userId;
    private Timestamp date;

    /**
     * Constructs an OrderInfo object with the specified order details.
     *
     * @param orderId The unique identifier for the order.
     * @param price The total price of the order.
     * @param userId The ID of the user who placed the order.
     * @param date The timestamp of when the order was placed.
     */
    public OrderInfo(int orderId, int price, int userId, Timestamp date) {
        this.orderId = orderId;
        this.price = price;
        this.userId = userId;
        this.date = date;
    }

    /**
     * Gets the order ID.
     *
     * @return The unique identifier for the order.
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets the order ID.
     *
     * @param orderId The unique identifier for the order to set.
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the total price of the order.
     *
     * @return The total price of the order.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the total price of the order.
     *
     * @param price The total price to set for the order.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Gets the user ID of the user who placed the order.
     *
     * @return The ID of the user who placed the order.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID of the user who placed the order.
     *
     * @param userId The user ID to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the timestamp of when the order was placed.
     *
     * @return The timestamp indicating when the order was placed.
     */
    public Timestamp getDate() {
        return date;
    }

    /**
     * Sets the timestamp of when the order was placed.
     *
     * @param date The timestamp to set for the order.
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }
}