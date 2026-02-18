package repository.custom.impl;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import repository.custom.OrderRepository;

import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {


    @Override
    public boolean create(MysqlxCrud.Order order) {
        return false;
    }

    @Override
    public boolean update(MysqlxCrud.Order order) {
        return false;
    }

    @Override
    public boolean deleteById(String s) {
        return false;
    }

    @Override
    public MysqlxCrud.Order getById(String s) {
        return null;
    }

    @Override
    public List<MysqlxCrud.Order> getAll() {
        return List.of();
    }
}
