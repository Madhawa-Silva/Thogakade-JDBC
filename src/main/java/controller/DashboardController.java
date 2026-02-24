package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class DashboardController {

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnItem;

    @FXML
    private JFXButton btnOrder;

    @FXML
    private AnchorPane viewPane;

    @FXML
    void btnCustomerOnAction(ActionEvent event) {
        try {
        URL resource = this.getClass().getResource("/view/customer_form.fxml");

        assert resource != null;

        Parent parent = FXMLLoader.load(resource);

        viewPane.getChildren().clear();
        viewPane.getChildren().add(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnItemOnAction(ActionEvent event) {
        try {
            URL resource = this.getClass().getResource("/view/Item_form.fxml");

            assert resource != null;

            Parent parent = FXMLLoader.load(resource);

            viewPane.getChildren().clear();
            viewPane.getChildren().add(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {
        try {
            URL resource = this.getClass().getResource("/view/Order_form.fxml");

            assert resource != null;

            Parent parent = FXMLLoader.load(resource);

            viewPane.getChildren().clear();
            viewPane.getChildren().add(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
