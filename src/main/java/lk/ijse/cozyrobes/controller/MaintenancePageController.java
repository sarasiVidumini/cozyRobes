package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MaintenancePageController {
    public AnchorPane ancMaintenancePage;
    public TableView tblMaintenance;
    public TableColumn colMaintenanceId;
    public TableColumn colMaterialId;
    public TableColumn colSectionId;
    public TableColumn colMaintenanceDate;
    public TableColumn colMaintenanceStatus;
    public TableColumn colCost;
    public Button btnBack;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;
    public Label lblMaintenanceId;
    public TextField txtMaterialId;
    public TextField txtSectionId;
    public TextField txtMaintenanceDate;
    public TextField txtMaintenanceStatus;
    public TextField txtCost;

    public void btnGoDashBoardPageOnAction(ActionEvent actionEvent) throws IOException {
        ancMaintenancePage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancMaintenancePage.getChildren().setAll(load);
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
