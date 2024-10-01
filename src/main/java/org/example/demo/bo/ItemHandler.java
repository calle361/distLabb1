package org.example.demo.bo;

import org.example.demo.ui.ItemInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemHandler {
    public static Collection<ItemInfo> getAllItems() {
        // Use generics to specify type safety
        Collection<Item> itemsFromDb = Item.getItems();
        List<ItemInfo> items = new ArrayList<>();

        // Use enhanced for-loop for better readability
        for (Item item : itemsFromDb) {
            items.add(new ItemInfo(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),  // Corrected to method call
                    item.getPrice(),
                    item.getAmount()
            ));
        }

        return items;
    }
}
