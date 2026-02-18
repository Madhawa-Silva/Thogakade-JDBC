package service.custom.impl;


import repository.RepositoryFactory;
import repository.custom.ItemRepository;
import service.custom.ItemService;
import model.Item;
import util.RepositoryType;

import java.sql.*;
import java.util.List;

public class ItemServiceImpl implements ItemService {

    ItemRepository itemRepository = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.ITEM);

    @Override
    public boolean addItem(Item item) {
        return itemRepository.create(item);
    }

    @Override
    public boolean updateItem(Item item) {
        return false;
    }

    @Override
    public boolean deleteItem(String itemCode) {
        return itemRepository.deleteById(itemCode);
    }

    @Override
    public Item searchItemByID(String itemCode) {
        return itemRepository.getById(itemCode);
    }

    @Override
    public List<Item> getAll() {
        return itemRepository.getAll();

    }
}
