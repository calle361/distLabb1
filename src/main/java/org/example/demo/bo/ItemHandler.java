package org.example.demo.bo;

import org.example.demo.ui.ItemInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ItemHandler {
    public static Collection<ItemInfo> getAllItems(){
        Collection c=Item.getItems();
        ArrayList<ItemInfo> items=new ArrayList<ItemInfo>();
        for (Iterator it=c.iterator(); it.hasNext();){
            Item item=(Item) it.next();
            items.add(new ItemInfo(item.getId(),item.getName(),item.getDescription,item.getPrice(),item.getAmount()));
        }
        return items;
    }
}
