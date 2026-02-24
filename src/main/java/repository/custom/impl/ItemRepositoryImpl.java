package repository.custom.impl;

import DB.DBConnection;
import model.Item;
import model.OrderDetail;
import repository.custom.ItemRepository;
import util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {

    @Override
    public boolean create(Item item) {
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
    public boolean update(Item item) {
        return false;
    }

    @Override
    public boolean deleteById(String itemCode) {
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
    public Item getById(String itemCode) {
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

    @Override
    public List<String> getItemCode() throws SQLException {
        ArrayList<String> itemCodeList = new ArrayList<>();

        List<Item> all = getAll();

        all.forEach(item -> itemCodeList.add(item.getItemCode()));

        return itemCodeList;
    }

    @Override
    public boolean updateStock(List<OrderDetail> orderDetailList) throws SQLException {
        for(OrderDetail orderDetails : orderDetailList){
            boolean isUpdateStock = updateStock(orderDetails);
            if(!isUpdateStock){
                return false;
            }
        }
        return true;
    }

    private boolean updateStock(OrderDetail orderDetail ) throws SQLException {
        return CrudUtil.execute("UPDATE item SET Stock  = Stock - ? WHERE ItemCode = ? " , orderDetail.getQtyOnHand() , orderDetail.getItemCode());
    }
}
