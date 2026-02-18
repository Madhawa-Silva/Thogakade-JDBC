package service.custom.impl;


import repository.RepositoryFactory;
import repository.custom.CustomerRepository;
import service.custom.CustomerService;
import model.Customer;
import util.RepositoryType;

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
    }}
