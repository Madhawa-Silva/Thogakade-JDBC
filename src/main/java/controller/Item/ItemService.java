package controller.Item;

import model.Item;

import java.util.List;

public interface ItemService {
    boolean addItem(Item item);
    boolean updateItem(Item item);
    boolean deleteItem(String itemCode);
    Item searchItemByID(String itemCode);
    List<Item> getAll();
}
