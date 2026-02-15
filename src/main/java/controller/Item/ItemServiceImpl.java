package controller.Item;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import model.TM.ItemTM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements ItemService{
    @Override
    public boolean addItem(Item item) {
        return false;
    }

    @Override
    public boolean updateItem(Item item) {
        return false;
    }

    @Override
    public boolean deleteItem(String itemCode) {
        return false;
    }

    @Override
    public Item searchItemByID(String itemCode) {
        return null;
    }

    @Override
    public List<Item> getAll() {

        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item");

            ArrayList<Item> itemArrayList = new ArrayList<>();

            while(resultSet.next()){
                Item item = new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)

                );

                itemArrayList.add(item);
            }

            return itemArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
