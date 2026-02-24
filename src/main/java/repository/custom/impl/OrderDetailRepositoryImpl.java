package repository.custom.impl;

import model.OrderDetail;
import repository.custom.OrderDetailRepository;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailRepositoryImpl implements OrderDetailRepository {


    @Override
    public boolean insertOrderDetail(List<OrderDetail> orderDetailList) throws SQLException {
       for(OrderDetail orderdetail : orderDetailList ){
           boolean isInsertOrderDetail = insertOrderDetail(orderdetail);
           if(!isInsertOrderDetail){
               return false;
           }

       }
       return true;

    }

    public boolean insertOrderDetail(OrderDetail orderDetail) throws SQLException {
        return CrudUtil.execute("INSERT INTO orderdetail VALUES(?,?,?,?)",
                orderDetail.getOrderId(),
                orderDetail.getItemCode(),
                orderDetail.getQtyOnHand(),
                orderDetail.getDiscount()
        );


    }
}
