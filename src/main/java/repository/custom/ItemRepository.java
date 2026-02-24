package repository.custom;

import model.Item;
import model.OrderDetail;
import repository.CrudRepository;

import java.sql.SQLException;
import java.util.List;

public interface ItemRepository extends CrudRepository<Item,String> {

    List<String> getItemCode() throws SQLException;


    boolean updateStock(List<OrderDetail> orderDetailList) throws SQLException;
}
