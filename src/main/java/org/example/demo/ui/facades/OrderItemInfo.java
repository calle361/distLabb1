package org.example.demo.ui.facades;

/**
 * Represents an order item with associated details such as order item ID,
 * order ID, item ID, and item name.
 */
public class OrderItemInfo {

    private int orderItemId;
    private int orderId;
    private int itemId;
    private String itemName;

    /**
     * Constructs an OrderItemInfo object with the specified order item details.
     *
     * @param orderItemId The unique identifier for the order item.
     * @param orderId The ID of the associated order.
     * @param itemId The ID of the associated item.
     * @param itemName The name of the associated item.
     */
    public OrderItemInfo(int orderItemId, int orderId, int itemId, String itemName) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemName = itemName;
    }

    /**
     * Gets the order item ID.
     *
     * @return The unique identifier for the order item.
     */
    public int getOrderItemId() {
        return orderItemId;
    }

    /**
     * Sets the order item ID.
     *
     * @param orderItemId The unique identifier for the order item to set.
     */
    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    /**
     * Gets the order ID associated with this order item.
     *
     * @return The ID of the associated order.
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets the order ID associated with this order item.
     *
     * @param orderId The ID of the associated order to set.
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the item ID associated with this order item.
     *
     * @return The ID of the associated item.
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Sets the item ID associated with this order item.
     *
     * @param itemId The ID of the associated item to set.
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * Gets the name of the item associated with this order item.
     *
     * @return The name of the associated item.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the name of the item associated with this order item.
     *
     * @param itemName The name of the associated item to set.
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}