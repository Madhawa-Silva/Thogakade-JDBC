package controller.customer;

import service.ServiceFactory;
import service.custom.CustomerService;
import service.custom.impl.CustomerServiceImpl;
import util.ServiceType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.TM.CustomerTM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerFormController {

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


    CustomerService serviceType = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);

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

        if(serviceType.addCustomer(customer)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Customer Added");
            Stage stage = (Stage) txtAddress.getScene().getWindow();
            alert.initOwner(stage);
            alert.show();
            loadTable();
        }else{
            new Alert(Alert.AlertType.WARNING,"Customer Not Added").show();
        }
    }

    @FXML
    void btnRealoadOnAction(ActionEvent event) {
        loadTable();

    }

    private void loadTable() {

        CustomerServiceImpl customerService = new CustomerServiceImpl();

        List<Customer> all = customerService.getAll();

        ArrayList<CustomerTM> customerTMArrayList = new ArrayList<>();
        all.forEach(customer ->{
            customerTMArrayList.add(new CustomerTM(
                    customer.getId(),
                    customer.getTitle(),
                    customer.getName(),
                    customer.getDate(),
                    customer.getSalary(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode()
            ));
        });

        tblCustomer.setItems(FXCollections.observableArrayList(customerTMArrayList));
    }

    @FXML
    public void initialize(){

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("fullCity"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));

        loadTable();

        cmbTitle.setItems(FXCollections.observableArrayList(Arrays.asList("Mr" , "Ms" , "Miss")));

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
            System.out.println(observableValue);
            System.out.println(oldValue);
            System.out.println(newValue);


            setTextToValuesTM((CustomerTM) newValue);
        });
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        setTextToValues(serviceType.searchCustomerByID(txtId.getText()));
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

    private void setTextToValuesTM(CustomerTM customertm){
        txtId.setText(customertm.getId());
        cmbTitle.setValue(customertm.getTitle());
        txtName.setText(customertm.getName());
        txtAddress.setText(customertm.getAddress());
        dateDob.setValue(customertm.getDob());
        txtSalary.setText(customertm.getSalary().toString());
        txtCity.setText(customertm.getCity());
        txtProvince.setText(customertm.getProvince());
        txtPostal.setText(customertm.getPostalCode());


    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
            if(serviceType.deleteCustomer(txtId.getText())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Customer Deleted");
                Stage stage = (Stage) txtAddress.getScene().getWindow();
                alert.initOwner(stage);
                alert.show();
                loadTable();
            }else{
                new Alert(Alert.AlertType.WARNING,"Customer Not Found").show();
            }
    }
}
