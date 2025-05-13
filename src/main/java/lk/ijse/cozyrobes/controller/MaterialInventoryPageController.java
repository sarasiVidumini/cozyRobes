package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MaterialInventoryPageController {
    public AnchorPane ancInventoryPage;
    public TableView tblMaterial;
    public TableColumn colMaterialId;
    public TableColumn colSupplierId;
    public TableColumn colMaterialName;
    public TableColumn colMaterialQuantity;
    public Label lblMaterialId;
    public TextField txtSupplierId;
    public TextField txtMaterialName;
    public TextField txtMaterialQuantity;
    public Button btnBack;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;

    public void btnGoDashBoardPageOnAction(ActionEvent actionEvent) throws IOException {
        ancInventoryPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancInventoryPage.getChildren().add(load);

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
