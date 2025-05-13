package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderPageController implements Initializable {

    public AnchorPane ancOrdersPage;
    public TableView tblOrders;
    public TableColumn colOrderId;
    public TableColumn colCustomerId;
    public TableColumn colOrderDate;
    public TableColumn colOrderStatus;
    public TableColumn colProductId;
    public Label lblOrderId;
    public TextField txtCustomerId;
    public TextField txtOrderDate;
    public TextField txtOrderStatus;
    public TextField txtProductId;
    public Button btnBack;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;


    public void btnGoDashBoardPageOnAction(ActionEvent actionEvent) throws IOException {
        ancOrdersPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancOrdersPage.getChildren().add(load);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
    }
}
