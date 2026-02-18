package Service.custom.impl;

import DB.DBConnection;
import Service.custom.CustomerService;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public boolean addCustomer(Customer customer) {
        try {

            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)");

            pstm.setString(1,customer.getId());
            pstm.setString(2,customer.getTitle());
            pstm.setString(3,customer.getName());
            pstm.setObject(4, customer.getDate());
            pstm.setDouble(5,customer.getSalary());
            pstm.setString(6,customer.getAddress());
            pstm.setString(7,customer.getCity());
            pstm.setString(8,customer.getProvince());
            pstm.setString(9,customer.getPostalCode());

            return pstm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE CustID = ?");
            pstm.setString(1,id);

        return pstm.executeUpdate()>0 ;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer searchCustomerByID(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer WHERE CustID = ? ");

            pstm.setString(1,id);
            ResultSet resultSet = pstm.executeQuery();

            resultSet.next();

            Customer customer = new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)
            );

            System.out.println(customer);

            return customer;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> getAll() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");

            ArrayList<Customer> customerArrayList = new ArrayList<>();

            while(resultSet.next()){

                Customer customer = new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)
                );

                customerArrayList.add(customer);
            }

            return customerArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }}
