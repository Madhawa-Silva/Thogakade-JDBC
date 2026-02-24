package repository.custom;

import model.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailRepository {

    boolean insertOrderDetail(List<OrderDetail> orderDetails) throws SQLException;
}
