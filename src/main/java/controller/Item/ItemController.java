package controller.Item;

import DB.DBConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Item;
import model.TM.ItemTM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemController {

    @FXML
    private JFXButton btnAddItem;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReload;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private TableColumn colDescription;

    @FXML
    private TableColumn colItemCode;

    @FXML
    private TableColumn colPackSize;

    @FXML
    private TableColumn colQtyOnHand;

    @FXML
    private TableColumn colUnitPrice;

    @FXML
    private TableView tblItems;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    void btnAddItemOnAction(ActionEvent event) {
        String itemCode = txtItemCode.getText();
        String description = txtDescription.getText();
        String packSize = txtPackSize.getText();
        Double price = Double.parseDouble(txtUnitPrice.getText());
        Integer qty = Integer.parseInt(txtQty.getText());

        Item item = new Item(itemCode,description,packSize,price,qty);
        System.out.println(item);

        try {

            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement pstm = connection.prepareStatement("INSERT INTO item VALUES(?,?,?,?,?)");

            pstm.setString(1,item.getItemCode());
            pstm.setString(2,item.getDescription());
            pstm.setString(3,item.getPackSize());
            pstm.setDouble(4,item.getUnitPrice());
            pstm.setInt(5,item.getQty());


            int rowsAffected = pstm.executeUpdate();

            if(rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Item Added");
                Stage stage = (Stage) txtItemCode.getScene().getWindow();
                alert.initOwner(stage);
                alert.show();
                loadItemTable();
            }else{
                new Alert(Alert.AlertType.ERROR, "Item Not Added!").show();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement pstm = connection.prepareStatement("DELETE FROM item WHERE ItemCode = ?");
            pstm.setString(1,txtItemCode.getText());

            if(pstm.executeUpdate()>0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Item Deleted");
                Stage stage = (Stage) txtItemCode.getScene().getWindow();
                alert.initOwner(stage);
                alert.show();
                loadItemTable();
            }else{
                new Alert(Alert.AlertType.WARNING,"Item Not Found").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadItemTable();

    }

    @FXML
    void btnSearchItemOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM item WHERE ItemCode = ? ");

            pstm.setString(1,txtItemCode.getText());
            ResultSet resultSet = pstm.executeQuery();

            resultSet.next();

            Item item = new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            );

            setTextToValues(item);



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadItemTable(){

        ItemServiceImpl itemService = new ItemServiceImpl();

        List<Item> all = itemService.getAll();

        ArrayList<ItemTM> itemTMArrayList = new ArrayList<>();
        all.forEach(item ->{
            itemTMArrayList.add(new ItemTM(
                    item.getItemCode(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQty()
            ));
        });

        tblItems.setItems(FXCollections.observableArrayList(itemTMArrayList));

    }

    @FXML
    public void initialize(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qty"));

        loadItemTable();

        tblItems.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
            System.out.println(observableValue);
            System.out.println(oldValue);
            System.out.println(newValue);


            setTextToValuesTM((ItemTM) newValue);
        });

    }

    private void setTextToValues(Item item){
        txtItemCode.setText(item.getItemCode());
        txtDescription.setText(item.getDescription());
        txtPackSize.setText(item.getPackSize());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtQty.setText(String.valueOf(item.getQty()));
    }

    private void setTextToValuesTM(ItemTM itemTM){
        txtItemCode.setText(itemTM.getItemCode());
        txtDescription.setText(itemTM.getDescription());
        txtPackSize.setText(itemTM.getPackSize());
        txtUnitPrice.setText(String.valueOf(itemTM.getUnitPrice()));
        txtQty.setText(String.valueOf(itemTM.getQty()));
    }



}
