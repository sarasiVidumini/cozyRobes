package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PaymentPageController {
    public AnchorPane ancPaymentPage;
    public TableView tblPayment;
    public TableColumn colPaymentId;
    public TableColumn colOrderId;
    public TableColumn colPaymentMethod;
    public TableColumn colTotalAmount;
    public Label lblPaymentId;
    public TextField txtOrderId;
    public TextField txtPaymentMethod;
    public TextField txtTotalAmount;
    public Button btnBack;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;


    public void btnGoDashBoardPageOnAction(ActionEvent actionEvent) throws IOException {
        ancPaymentPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancPaymentPage.getChildren().add(load);
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
