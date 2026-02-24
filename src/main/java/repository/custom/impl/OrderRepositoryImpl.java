package repository.custom.impl;

import DB.DBConnection;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import model.Order;
import repository.custom.ItemRepository;
import repository.custom.OrderDetailRepository;
import repository.custom.OrderRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {

    OrderDetailRepository detailRepository = new OrderDetailRepositoryImpl();
    ItemRepository itemRepository = new ItemRepositoryImpl();

    @Override
    public boolean placeOrder(Order order) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try{
            connection.setAutoCommit(false);

            PreparedStatement psTM = connection.prepareStatement("INSERT INTO orders VALUES(?,?,?)");

            psTM.setString(1, order.getOrderId());
            psTM.setObject(2,order.getOrderDate()) ;
            psTM.setString(3, order.getCustID());

            boolean isOrderInsert = psTM.executeUpdate() > 0;

            if(isOrderInsert){
                boolean isOrderDetailsAdd = detailRepository.insertOrderDetail(order.getOrderDetailList());
                if(isOrderDetailsAdd){
                    boolean isStockUpdate = itemRepository.updateStock(order.getOrderDetailList());
                    if(isStockUpdate){
                        connection.commit();
                        return true;
                    }
                }
            }

            return false;
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }finally {
            connection.setAutoCommit(true); 
        }
    }
}
