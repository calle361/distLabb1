package org.example.demo.ui.facades;

import java.sql.Timestamp;

public class OrderInfo {
    int Orderid;
    int price;
    int userid;
    Timestamp date;

    public OrderInfo(int orderid, int price, int userid, Timestamp date) {
        Orderid = orderid;
        this.price = price;
        this.userid = userid;
        this.date = date;
    }

    public int getOid() {
        return Orderid;
    }

    public void setOid(int oid) {
        Orderid = oid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
