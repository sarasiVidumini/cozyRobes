package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class QuickcheckPageController {
    public AnchorPane ancQuickcheckPage;
    public TableView tblQuickcheck;
    public TableColumn colCheckId;
    public TableColumn colMaintenanceId;
    public TableColumn colCheckType;
    public TableColumn colCKStatus;
    public Label lblCheckId;
    public TextField txtMainId;
    public TextField txtCheckType;
    public TextField txtCKStatus;
    public Button btnBack;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;

    public void btnGoDashBoardPageOnAction(ActionEvent actionEvent) throws IOException {
        ancQuickcheckPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancQuickcheckPage.getChildren().add(load);
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
