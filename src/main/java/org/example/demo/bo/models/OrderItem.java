package org.example.demo.bo.models;

/**
 * Represents an item within an order, including details such as
 * order item ID, order ID, item ID, and the item name.
 */
public class OrderItem {
    private int OrderItemid;
    private int Orderid;
    private int Itemid;
    private String Itemname;

    /**
     * Constructs an OrderItem with the specified attributes.
     *
     * @param orderItemid the unique identifier for the order item
     * @param orderid     the ID of the associated order
     * @param itemid      the ID of the associated item
     * @param itemname    the name of the item
     */
    public OrderItem(int orderItemid, int orderid, int itemid, String itemname) {
        this.OrderItemid = orderItemid;
        this.Orderid = orderid;
        this.Itemid = itemid;
        this.Itemname = itemname;
    }

    /**
     * Returns the unique identifier of the order item.
     *
     * @return the order item's ID
     */
    public int getOrderItemid() {
        return OrderItemid;
    }

    /**
     * Sets the unique identifier of the order item.
     *
     * @param orderItemid the new ID for the order item
     */
    public void setOrderItemid(int orderItemid) {
        OrderItemid = orderItemid;
    }

    /**
     * Returns the ID of the associated order.
     *
     * @return the order's ID
     */
    public int getOrderid() {
        return Orderid;
    }

    /**
     * Returns the name of the item.
     *
     * @return the item's name
     */
    public String getOrderItemname() {
        return Itemname;
    }

    /**
     * Sets the ID of the associated order.
     *
     * @param orderid the new order ID
     */
    public void setOrderid(int orderid) {
        Orderid = orderid;
    }

    /**
     * Returns the ID of the associated item.
     *
     * @return the item's ID
     */
    public int getItemid() {
        return Itemid;
    }

    /**
     * Sets the ID of the associated item.
     *
     * @param itemid the new item ID
     */
    public void setItemid(int itemid) {
        Itemid = itemid;
    }
}
