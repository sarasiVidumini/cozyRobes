package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OrderDetailPageController{
    public AnchorPane ancORDetailsPage;
    public TableView tblORDetails;
    public TableColumn colOrderDetailId;
    public TableColumn colOrderId;
    public TableColumn colProductId;
    public TableColumn colOrderQuantity;
    public TableColumn colPriceAtPurchase;
    public TableColumn colUpdatePrice;
    public Label lblOrderDetailId;
    public TextField txtOrderId;
    public TextField txtProductId;
    public TextField txtOrderQuantity;
    public TextField txtPricePurchase;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;
    public Button btnBack;
    public TextField txtUpdatePrice;


    public void btnGoDashBoardPageOnAction(ActionEvent actionEvent) throws IOException {
        ancORDetailsPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancORDetailsPage.getChildren().add(load);
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
