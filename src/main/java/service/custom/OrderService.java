package service.custom;

import model.Order;
import repository.RepositoryFactory;
import repository.custom.OrderRepository;
import service.SuperService;
import util.RepositoryType;

import java.sql.SQLException;

public interface OrderService extends SuperService  {

    boolean placeOrder(Order order) throws SQLException;
}
