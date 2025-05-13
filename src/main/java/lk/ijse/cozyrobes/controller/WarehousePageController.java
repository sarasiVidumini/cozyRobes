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

public class WarehousePageController implements Initializable {
    public AnchorPane ancWarehousePage;
    public TableView tblWarehouse;
    public TableColumn colSectionId;
    public TableColumn colProductId;
    public TableColumn colCapacity;
    public TableColumn colLocation;
    public Label lblSectionId;
    public TextField txtProductId;
    public TextField txtCapacity;
    public TextField txtLocation;
    public Button btnBack;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;

    public void btnGoDashBoardPageOnAction(ActionEvent actionEvent) throws IOException {
        ancWarehousePage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancWarehousePage.getChildren().add(load);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
