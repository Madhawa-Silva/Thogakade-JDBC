package Service.custom;

import Service.SuperService;
import model.Customer;

import java.util.List;

public interface CustomerService extends SuperService {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(String id);
    Customer searchCustomerByID(String id);
    List<Customer> getAll();
}
