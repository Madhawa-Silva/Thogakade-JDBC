package Service.custom.impl;

import DB.DBConnection;
import Service.custom.ItemService;
import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements ItemService {
    @Override
    public boolean addItem(Item item) {
        try {

            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement pstm = connection.prepareStatement("INSERT INTO item VALUES(?,?,?,?,?)");

            pstm.setString(1,item.getItemCode());
            pstm.setString(2,item.getDescription());
            pstm.setString(3,item.getPackSize());
            pstm.setDouble(4,item.getUnitPrice());
            pstm.setInt(5,item.getQty());

            return pstm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateItem(Item item) {
        return false;
    }

    @Override
    public boolean deleteItem(String itemCode) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement pstm = connection.prepareStatement("DELETE FROM item WHERE ItemCode = ?");
            pstm.setString(1,itemCode);

            return pstm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item searchItemByID(String itemCode) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM item WHERE ItemCode = ? ");

            pstm.setString(1,itemCode);
            ResultSet resultSet = pstm.executeQuery();

            resultSet.next();

            Item item = new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            );

            return item;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
