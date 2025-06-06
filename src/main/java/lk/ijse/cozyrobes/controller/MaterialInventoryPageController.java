package lk.ijse.cozyrobes.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cozyrobes.dto.MaterialInventoryDto;
import lk.ijse.cozyrobes.dto.tm.MaterialInventoryTM;
import lk.ijse.cozyrobes.model.MaterialInventoryModel;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MaterialInventoryPageController implements Initializable {
    private final MaterialInventoryModel materialInventoryModel = new MaterialInventoryModel();

    private final String namePattern = "^[A-Za-z ]+$";

    public AnchorPane ancInventoryPage;
    public TableView<MaterialInventoryTM> tblMaterial;
    public TableColumn<MaterialInventoryTM, String> colMaterialId;
    public TableColumn<MaterialInventoryTM, String> colSupplierId;
    public TableColumn<MaterialInventoryTM, String> colMaterialName;
    public TableColumn<MaterialInventoryTM, Integer> colMaterialQuantity;
    public Label lblMaterialId;
    public ComboBox<String> cmbSupplierPlatform;
    public TextField txtMaterialName;
    public TextField txtMaterialQuantity;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;
    public TextField txtSearch;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMaterialId.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colMaterialName.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        colMaterialQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        cmbSupplierPlatform.setItems(FXCollections.observableArrayList("S001", "S002", "S003", "S004", "S005", "S006"));

        try {
            loadTableData();
            loadNextId();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load data!").show();
        }
    }

    private void loadNextId() throws SQLException {
       String nextId = materialInventoryModel.getNextInventoryId();
       lblMaterialId.setText(nextId);
    }


    private void loadTableData() throws SQLException {
        var materialList = materialInventoryModel.getAllMaterialInventory();

        if (materialList != null) {
            tblMaterial.setItems(FXCollections.observableArrayList(
                    materialList.stream()
                            .map(materialInventoryDto -> new MaterialInventoryTM(
                                    materialInventoryDto.getMaterialId(),
                                    materialInventoryDto.getSupplierId(),
                                    materialInventoryDto.getMaterialName(),
                                    materialInventoryDto.getQuantity()
                            )).toList()
            ));
        }
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {

        String materialId = lblMaterialId.getText();
        String supplierId = cmbSupplierPlatform.getValue();
        String name = txtMaterialName.getText();
        int quantity = Integer.parseInt(txtMaterialQuantity.getText());

        boolean isNameValid = name.matches(namePattern);

        if (materialId.isEmpty() || supplierId == null || name.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill out all fields!").show();
            return;
        }

        if (!isNameValid) {
            new Alert(Alert.AlertType.ERROR, "Invalid Material name!").show();
            return;
        }

        MaterialInventoryDto dto = new MaterialInventoryDto(materialId, supplierId, name, quantity);
        if (isNameValid) {
            try {
                boolean isSaved = materialInventoryModel.saveMaterialInventory(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Material saved successfully!").show();
                    loadTableData();
                    resetPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save material!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while saving material!").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            String materialId = lblMaterialId.getText();
            String supplierId = cmbSupplierPlatform.getValue();
            String name = txtMaterialName.getText();
            int quantity = Integer.parseInt(txtMaterialQuantity.getText());

            if (materialId.isEmpty() || supplierId == null || name.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please fill out all fields!").show();
                return;
            }

            MaterialInventoryDto dto = new MaterialInventoryDto(materialId, supplierId, name, quantity);

            boolean isUpdated = materialInventoryModel.updateMaterialInventory(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Material updated successfully!").show();
                loadTableData();
                resetPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update material!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while updating material!").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this material?",
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirm Delete");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                String materialId = lblMaterialId.getText();
                boolean isDeleted = materialInventoryModel.deleteMaterialInventory(materialId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Material deleted successfully!").show();
                    loadTableData();
                    resetPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete material!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while deleting material!").show();
            }
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void onClickedTable(MouseEvent mouseEvent) {
        MaterialInventoryTM selectedItem = tblMaterial.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblMaterialId.setText(selectedItem.getMaterialId());
            cmbSupplierPlatform.setValue(selectedItem.getSupplierId());
            txtMaterialName.setText(selectedItem.getMaterialName());
            txtMaterialQuantity.setText(String.valueOf(selectedItem.getQuantity()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }


    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            txtMaterialName.clear();
            txtMaterialQuantity.clear();
            cmbSupplierPlatform.getSelectionModel().clearSelection();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to reset page!").show();
        }
    }

    public void goToDashboard(MouseEvent mouseEvent) throws IOException {
        ancInventoryPage.getChildren().clear();
        Parent load  = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancInventoryPage.getChildren().add(load);
    }

    public void search(KeyEvent keyEvent) {
        String search = txtSearch.getText();
        if (search.isEmpty()) {
            try {
                loadTableData();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search!").show();
            }
        }else {
            try {
                ArrayList<MaterialInventoryDto> materialList = materialInventoryModel.searchMaterialInventory(search);
                tblMaterial.setItems(FXCollections.observableArrayList(
                        materialList.stream()
                                .map(materialInventoryDto -> new MaterialInventoryTM(
                                        materialInventoryDto.getMaterialId(),
                                        materialInventoryDto.getSupplierId(),
                                        materialInventoryDto.getMaterialName(),
                                        materialInventoryDto.getQuantity()
                                )).toList()
                ));
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search!").show();
            }
        }
    }
}
