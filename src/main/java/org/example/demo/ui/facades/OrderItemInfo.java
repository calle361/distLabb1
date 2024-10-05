package org.example.demo.ui.facades;

public class OrderItemInfo {
    private int OrderItemid;
    private int Orderid;
    private int Itemid;
    private String Itemname;

    public OrderItemInfo(int orderItemid, int orderid, int itemid, String itemname) {
        this.OrderItemid = orderItemid;
        this.Orderid = orderid;
        this.Itemid = itemid;
        this.Itemname = itemname;
    }

    public int getOrderItemid() {
        return OrderItemid;
    }

    public void setOrderItemid(int orderItemid) {
        OrderItemid = orderItemid;
    }

    public int getOrderid() {
        return Orderid;
    }

    public void setOrderid(int orderid) {
        Orderid = orderid;
    }

    public int getItemid() {
        return Itemid;
    }

    public void setItemid(int itemid) {
        Itemid = itemid;
    }

    public String getItemname() {
        return Itemname;
    }

    public void setItemname(String itemname) {
        Itemname = itemname;
    }
}
