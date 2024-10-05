package org.example.demo.bo.models;

import java.sql.Timestamp;

public class Order {
    int Oid;
    int price;
    int userid;
    Timestamp date;

    public Order(int oid, int price, int userid,Timestamp date) {
        Oid = oid;
        this.price = price;
        this.userid = userid;
        this.date = date;

    }

    public Timestamp getDate() {
        return date;
    }

    public int getOid() {
        return Oid;
    }

    public void setOid(int oid) {
        Oid = oid;
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
}
