package org.example.demo.db;

import org.example.demo.bo.Item;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Vector;


public class ItemDB extends Item {
    public static Collection getItems() {

        Vector v=new Vector();
        try{
            Connection con=DBManager.getConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from products");
            while (rs.next()){
                int i=rs.getInt("id");
                String name=rs.getString("name");
                String description=rs.getString("description");
                int price=rs.getInt("price");
                int stock=rs.getInt("stock");
                v.addElement(new ItemDB(i,name,description,price,stock));
            }
        }catch(SQLException e){e.printStackTrace();}

        return v;
    }




    private ItemDB(int id, String name,String description, int price, int amount) {
        super(id, name,description, price, amount);
    }
}
