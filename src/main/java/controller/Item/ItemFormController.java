package controller.Item;

import service.ServiceFactory;
import service.custom.ItemService;
import service.custom.impl.ItemServiceImpl;
import util.ServiceType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Item;
import model.TM.ItemTM;

import java.util.ArrayList;
import java.util.List;

public class ItemFormController {

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

    ItemService serviceType = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);

    @FXML
    void btnAddItemOnAction(ActionEvent event) {
        String itemCode = txtItemCode.getText();
        String description = txtDescription.getText();
        String packSize = txtPackSize.getText();
        Double price = Double.parseDouble(txtUnitPrice.getText());
        Integer qty = Integer.parseInt(txtQty.getText());

        Item item = new Item(itemCode,description,packSize,price,qty);
        System.out.println(item);

            if(serviceType.addItem(item)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Item Added");
                Stage stage = (Stage) txtItemCode.getScene().getWindow();
                alert.initOwner(stage);
                alert.show();
                loadItemTable();
            }else{
                new Alert(Alert.AlertType.ERROR, "Item Not Added!").show();
            }




    }

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) {

        if(serviceType.deleteItem(txtItemCode.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Item Deleted");
            Stage stage = (Stage) txtItemCode.getScene().getWindow();
            alert.initOwner(stage);
            alert.show();
            loadItemTable();
        }else{
            new Alert(Alert.AlertType.WARNING,"Item Not Found").show();
        }

    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadItemTable();

    }

    @FXML
    void btnSearchItemOnAction(ActionEvent event) {
        setTextToValues(serviceType.searchItemByID(txtItemCode.getText()));
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
