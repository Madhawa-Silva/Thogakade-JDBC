package controller;

import DB.DBConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.TM.CustomerTM;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class CustomerController {

    public JFXButton btnDelete;
    public JFXButton btnSearch;

    @FXML
    private JFXButton btnAddCustomer;

    @FXML
    private JFXButton btnReload;

    @FXML
    private JFXComboBox cmbTitle;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colCity;

    @FXML
    private TableColumn colDob;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colProvince;

    @FXML
    private TableColumn colSalary;

    @FXML
    private DatePicker dateDob;

    @FXML
    private TableView tblCustomer;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostal;

    @FXML
    private JFXTextField txtProvince;

    @FXML
    private JFXTextField txtSalary;

    ObservableList<CustomerTM> observableArrayList = FXCollections.observableArrayList();

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String id = txtId.getText();
        String title = cmbTitle.getValue().toString();
        String name = txtName.getText();
        String address = txtAddress.getText();
        LocalDate date = dateDob.getValue();
        Double salary = Double.parseDouble(txtSalary.getText());
        String city = txtCity.getText();
        String province = txtProvince.getText();
        String postalCode = txtPostal.getText();

        Customer customer = new Customer(id,title,name,date,salary,address,city,province,postalCode);
        System.out.println(customer);

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

            int rowsAffected = pstm.executeUpdate();

            if(rowsAffected > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Added!").show();
                loadTable();
            }else{
                new Alert(Alert.AlertType.ERROR, "Customer Not Added!").show();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnRealoadOnAction(ActionEvent event) {
        loadTable();

    }

    private void loadTable() {

        observableArrayList.clear();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));



        try {
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println(connection);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");

            System.out.println(resultSet);

            while(resultSet.next()){
                observableArrayList.add(
                        new CustomerTM(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDate(4),
                                resultSet.getDouble(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getString(8),
                                resultSet.getString(9)
                        )
                );
            }

            System.out.println(observableArrayList);

            tblCustomer.setItems(observableArrayList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize(){
        loadTable();

        cmbTitle.setItems(FXCollections.observableArrayList(Arrays.asList("Mr" , "Ms" , "Miss")));
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {


        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer WHERE CustID = ? ");

            pstm.setString(1,txtId.getText());
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
            setTextToValues(customer);



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTextToValues(Customer customer){
        txtId.setText(customer.getId());
        cmbTitle.setValue(customer.getTitle());
        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
        dateDob.setValue(customer.getDate());
        txtSalary.setText(customer.getSalary().toString());
        txtCity.setText(customer.getCity());
        txtProvince.setText(customer.getProvince());
        txtPostal.setText(customer.getPostalCode());

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE CustID = ?");
            pstm.setString(1,txtId.getText());

            if(pstm.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer Deleted").show();
                loadTable();
            }else{
                new Alert(Alert.AlertType.WARNING,"Customer Not Found").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
