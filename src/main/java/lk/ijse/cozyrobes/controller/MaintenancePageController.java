package lk.ijse.cozyrobes.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cozyrobes.dto.MaintenanceDto;
import lk.ijse.cozyrobes.dto.tm.MaintenanceTM;
import lk.ijse.cozyrobes.model.MaintenanceModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MaintenancePageController implements Initializable {
    public AnchorPane ancMaintenancePage;
    private final MaintenanceModel maintenanceModel = new MaintenanceModel();

    public TableView<MaintenanceTM> tblMaintenance;
    public TableColumn<MaintenanceTM, String> colMaintenanceId;
    public TableColumn<MaintenanceTM, String> colMaterialId;
    public TableColumn<MaintenanceTM, String> colSectionId;
    public TableColumn<MaintenanceTM, String> colMaintenanceDate;
    public TableColumn<MaintenanceTM, String> colMaintenanceStatus;
    public TableColumn<MaintenanceTM, Double> colCost;

    public Button btnBack;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;

    public Label lblMaintenanceId;
    public ComboBox<String> cmbMaterialPlatform;
    public ComboBox<String> cmbSectionPlatform;
    public TextField txtMaintenanceDate;
    public TextField txtMaintenanceStatus;
    public TextField txtCost;

    public void btnGoDashBoardPageOnAction(ActionEvent actionEvent) throws IOException {
        ancMaintenancePage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancMaintenancePage.getChildren().add(load);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this maintenance?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setTitle("Confirmation");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            String maintenanceId = lblMaintenanceId.getText();
            try {
                boolean isDeleted = maintenanceModel.deleteMaintenance(maintenanceId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Maintenance deleted successfully").show();
                    resetPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete Maintenance").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to delete Maintenance").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String maintenanceId = lblMaintenanceId.getText();
        String materialId = cmbMaterialPlatform.getValue();
        String sectionId = cmbSectionPlatform.getValue();
        String maintenanceDate = txtMaintenanceDate.getText();
        String maintenanceStatus = txtMaintenanceStatus.getText();
        double cost = Double.parseDouble(txtCost.getText());

        if (maintenanceId.isEmpty() || materialId == null || sectionId == null || maintenanceDate.isEmpty() || maintenanceStatus.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Empty fields, please fill all the fields").show();
            return;
        }

        MaintenanceDto maintenanceDto = new MaintenanceDto(maintenanceId, materialId, sectionId, maintenanceDate, maintenanceStatus, cost);

        try {
            boolean isUpdated = maintenanceModel.updateMaintenance(maintenanceDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Maintenance updated successfully").show();
                resetPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Maintenance").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to update Maintenance").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String maintenanceId = lblMaintenanceId.getText();
        String materialId = cmbMaterialPlatform.getValue();
        String sectionId = cmbSectionPlatform.getValue();
        String maintenanceDate = txtMaintenanceDate.getText();
        String maintenanceStatus = txtMaintenanceStatus.getText();
        double cost = Double.parseDouble(txtCost.getText());

        if (maintenanceId.isEmpty() || materialId == null || sectionId == null || maintenanceDate.isEmpty() || maintenanceStatus.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Empty fields, please fill all the fields").show();
            return;
        }

        MaintenanceDto maintenanceDto = new MaintenanceDto(maintenanceId, materialId, sectionId, maintenanceDate, maintenanceStatus, cost);

        try {
            boolean isSaved = maintenanceModel.saveMaintenance(maintenanceDto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Maintenance saved successfully").show();
                resetPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Maintenance").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save Maintenance").show();
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void loadTableData() throws SQLException {
        tblMaintenance.setItems(FXCollections.observableArrayList(
                maintenanceModel.getAllMaintenance()
                        .stream()
                        .map(maintenanceDto -> new MaintenanceTM(
                                maintenanceDto.getMaintenance_id(),
                                maintenanceDto.getMaterial_id(),
                                maintenanceDto.getSection_id(),
                                maintenanceDto.getMaintenance_date(),
                                maintenanceDto.getMaintenance_status(),
                                maintenanceDto.getCost()
                        )).toList()
        ));
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();
            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
            cmbMaterialPlatform.getSelectionModel().clearSelection();
            cmbSectionPlatform.getSelectionModel().clearSelection();
            txtMaintenanceDate.clear();
            txtMaintenanceStatus.clear();
            txtCost.clear();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to reset page").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        MaintenanceTM selectedItem = tblMaintenance.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblMaintenanceId.setText(selectedItem.getMaintenance_id());
            cmbMaterialPlatform.setValue(selectedItem.getMaterial_id());
            cmbSectionPlatform.setValue(selectedItem.getSection_id());
            txtMaintenanceDate.setText(selectedItem.getMaintenance_date());
            txtMaintenanceStatus.setText(selectedItem.getMaintenance_status());
            txtCost.setText(String.valueOf(selectedItem.getCost()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMaintenanceId.setCellValueFactory(new PropertyValueFactory<>("maintenance_id"));
        colMaterialId.setCellValueFactory(new PropertyValueFactory<>("material_id"));
        colSectionId.setCellValueFactory(new PropertyValueFactory<>("section_id"));
        colMaintenanceDate.setCellValueFactory(new PropertyValueFactory<>("maintenance_date"));
        colMaintenanceStatus.setCellValueFactory(new PropertyValueFactory<>("maintenance_status"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        cmbMaterialPlatform.setItems(FXCollections.observableArrayList("M001", "M002", "M003", "M004", "M005", "M006"));
        cmbSectionPlatform.setItems(FXCollections.observableArrayList("Section_A", "Section_B"));

        try {
            loadTableData();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load maintenance data").show();
        }
    }

    private void loadNextId() {
        try {
            String nextId = "MA001"; // Dummy logic; replace with DB fetch if needed
            lblMaintenanceId.setText(nextId);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to generate next Maintenance ID").show();
        }
    }
}
