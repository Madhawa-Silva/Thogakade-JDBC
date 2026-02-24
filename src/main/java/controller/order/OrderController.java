package controller.order;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.Customer;
import model.Item;
import model.Order;
import model.OrderDetail;
import model.TM.CartTM;
import service.ServiceFactory;
import service.custom.CustomerService;
import service.custom.ItemService;
import service.custom.OrderService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderController implements Initializable {
    public Label lblDate;
    public Label lblTime;
    public JFXComboBox cmbCustomerID;
    public JFXTextField txtOrderId;
    public Label lblName;
    public Label lblAddress;
    public JFXComboBox cmbItemCode;
    public Label lblDescription;
    public Label lblStock;
    public Label lblUnitPrice;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TableColumn colQtyOnHand;
    public TextField txtQtyOnHand;
    public JFXButton btnAddToCart;
    public TableView tblCart;
    public Label lblNetTotal;
    public JFXButton btnPlaceOrder;


    CustomerService customerService= ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
    ItemService itemService= ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);
    OrderService orderService= ServiceFactory.getInstance().getServiceType(ServiceType.ORDER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));


        loadDateAndTime();
        loadCustomerID();
        loadItemId();

        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newValue) -> {
            setCustomerDataToTables((String) newValue);
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newValue) -> {
            setItemDataToTables((String) newValue);
        });
    }

    private void setItemDataToTables(String itemCode) {
        Item item = itemService.searchItemByID(itemCode);

        lblDescription.setText(item.getDescription());
        lblStock.setText(String.valueOf(item.getQty()));
        lblUnitPrice.setText(String.valueOf(item.getUnitPrice()));
    }

    private void loadItemId() {
        List<String> allItemCodes = itemService.getAllItemCode();

        cmbItemCode.setItems(FXCollections.observableArrayList(allItemCodes));

    }

    private void setCustomerDataToTables(String id) {
        Customer customer = customerService.searchCustomerByID(id);

        lblName.setText(customer.getName());
        lblAddress.setText(customer.getAddress());

    }

    private void loadDateAndTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy - MM - dd");
        lblDate.setText(sdf.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO , e->{
            LocalTime now = LocalTime.now();
            lblTime.setText(now.getHour()+" : "+now.getMinute()+(" : ")+now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    private void loadCustomerID(){
        List<String> allCustomerID = customerService.getAllCustomerId();

        cmbCustomerID.setItems(FXCollections.observableArrayList(allCustomerID));

    }

    ArrayList<CartTM> cartItemArrayList  = new ArrayList<>();

    public void btnAddCartOnAction(ActionEvent actionEvent) {

        cartItemArrayList.add(new CartTM(
                cmbItemCode.getValue().toString() ,
                lblDescription.getText(),
                Double.parseDouble(lblUnitPrice.getText()) ,
                Integer.parseInt(txtQtyOnHand.getText()) ,
                Double.parseDouble(lblUnitPrice.getText())*Integer.parseInt(txtQtyOnHand.getText()),
                txtOrderId.getText()
                )
        );

        tblCart.setItems(FXCollections.observableArrayList(cartItemArrayList));

        netTotal();

    }

    private void netTotal(){
        Double total = 0.0;

        for(CartTM cartTM : cartItemArrayList){
            total+=cartTM.getTotal();
        }

        lblNetTotal.setText(total.toString());
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy - MM - dd");

        ArrayList<OrderDetail> orderDetailsArrayList = new ArrayList<>();

        cartItemArrayList.forEach(cartTM ->
            orderDetailsArrayList.add(new OrderDetail(
                    cartTM.getOrderId(),
                    cartTM.getItemCode(),
                    cartTM.getQtyOnHand(),
                    0.0
            ))
        );


        Order order = new Order(
                txtOrderId.getText(),
                LocalDate.now(),
                cmbCustomerID.getValue().toString(),
                orderDetailsArrayList
        );

        System.out.println(order);

        try {
            if(orderService.placeOrder(order)) {
                new Alert(Alert.AlertType.INFORMATION,"Order Placed").show();

            }else{
                new Alert(Alert.AlertType.INFORMATION,"Order Not Placed").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }
}
