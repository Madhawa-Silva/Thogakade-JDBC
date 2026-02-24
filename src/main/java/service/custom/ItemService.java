package service.custom;

import service.SuperService;
import model.Item;

import java.util.List;

public interface ItemService extends SuperService {
    boolean addItem(Item item);
    boolean updateItem(Item item);
    boolean deleteItem(String itemCode);
    Item searchItemByID(String itemCode);
    List<Item> getAll();
    List<String> getAllItemCode();

}
