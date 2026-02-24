package service.custom.impl;


import repository.RepositoryFactory;
import repository.custom.CustomerRepository;
import service.custom.CustomerService;
import model.Customer;
import util.RepositoryType;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.CUSTOMER);

    @Override
    public boolean addCustomer(Customer customer) {
        return customerRepository.create(customer);

    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {
        return customerRepository.deleteById(id);
    }

    @Override
    public Customer searchCustomerByID(String id) {
        return customerRepository.getById(id);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.getAll();
    }

    @Override
    public List<String> getAllCustomerId() {

        List<Customer> all = getAll();

        ArrayList<String> idList = new ArrayList<>();
        for(Customer customer : all){
            idList.add(customer.getId());
        }
        return idList;
    }
}
