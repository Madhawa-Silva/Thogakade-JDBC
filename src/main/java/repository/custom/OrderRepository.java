package repository.custom;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import repository.CrudRepository;

public interface OrderRepository extends CrudRepository<MysqlxCrud.Order, String> {
}
