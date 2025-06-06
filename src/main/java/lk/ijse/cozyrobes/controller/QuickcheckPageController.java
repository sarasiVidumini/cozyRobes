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
import lk.ijse.cozyrobes.dto.QuickcheckDto;
import lk.ijse.cozyrobes.dto.tm.QuickcheckTM;
import lk.ijse.cozyrobes.model.QuickcheckModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class QuickcheckPageController implements Initializable {
    public AnchorPane ancQuickcheckPage;
    private final QuickcheckModel quickcheckModel = new QuickcheckModel();

    public TableView<QuickcheckTM> tblQuickcheck;
    public TableColumn<QuickcheckTM , String> colCheckId;
    public TableColumn<QuickcheckTM , String> colMaintenanceId;
    public TableColumn<QuickcheckTM , String> colCheckType;
    public TableColumn<QuickcheckTM , String> colCKStatus;
    public Label lblCheckId;
    public ComboBox<String> cmbMaintenancePlatform;
    public TextField txtCheckType;
    public TextField txtCKStatus;
    
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;
    public TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCheckId.setCellValueFactory(new PropertyValueFactory<>("checkId"));
        colMaintenanceId.setCellValueFactory(new PropertyValueFactory<>("maintenanceId"));
        colCheckType.setCellValueFactory(new PropertyValueFactory<>("checkType"));
        colCKStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        cmbMaintenancePlatform.setItems(FXCollections.observableArrayList("MA001" , "MA002" , "MA003" , "MA004" , "MA005" , "MA006"));

        try {
            loadTableData();
            loadNextId();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to initialize the page").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = quickcheckModel.getNextCheckId();
        lblCheckId.setText(nextId);
    }

    private void loadTableData() throws Exception {
        tblQuickcheck.setItems(FXCollections.observableArrayList(
                quickcheckModel.getAllQuickcheck().stream()
                        .map(quickcheckDto -> new QuickcheckTM(
                                quickcheckDto.getCheckId(),
                                quickcheckDto.getMaintenanceId(),
                                quickcheckDto.getCheckType(),
                                quickcheckDto.getStatus()
                        )).toList()
        ));
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want delete this check ?",
                ButtonType.YES , ButtonType.NO);
        alert.setTitle("Confirm Delete");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            String checkId = lblCheckId.getText();
            try {
                boolean isDeleted = quickcheckModel.deleteQuickcheck(checkId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Check deleted successfully").show();
                    resetPage();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete check").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Failed to delete quickcheck").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String checkId = lblCheckId.getText();
        String maintenanceId = cmbMaintenancePlatform.getValue();
        String checkType = txtCheckType.getText();
        String status = txtCKStatus.getText();

        if (checkId.isEmpty() || maintenanceId.isEmpty() || checkType.isEmpty() || status.isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Empty fields.please fill the all fields.").show();
            return;
        }

        QuickcheckDto quickcheckDto = new QuickcheckDto(checkId, maintenanceId, checkType, status);

        try {
            boolean isUpdated = quickcheckModel.updateQuickCheck(quickcheckDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Check updated successfully").show();
                resetPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to update check").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to update check").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String checkId = lblCheckId.getText();
        String maintenanceId = cmbMaintenancePlatform.getValue();
        String checkType = txtCheckType.getText();
        String status = txtCKStatus.getText();

        if (checkId.isEmpty() || maintenanceId.isEmpty() || checkType.isEmpty() || status.isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Empty fields.please fill the all fields.").show();
            return;
        }

        QuickcheckDto quickcheckDto = new QuickcheckDto(checkId, maintenanceId, checkType, status);

        try {
            boolean isSaved = quickcheckModel.saveQuickcheck(quickcheckDto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Check saved successfully").show();
                resetPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to save check").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to save check").show();
        }
    }

    public void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
            cmbMaintenancePlatform.getSelectionModel().clearSelection();
            txtCheckType.clear();
            txtCKStatus.clear();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to reset the page").show();

        }
    }
    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void onClickedTable(MouseEvent mouseEvent) {
        QuickcheckTM selectedItem = tblQuickcheck.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblCheckId.setText(selectedItem.getCheckId());
            cmbMaintenancePlatform.setValue(selectedItem.getMaintenanceId());
            txtCheckType.setText(selectedItem.getCheckType());
            txtCKStatus.setText(selectedItem.getStatus());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }

    }

    public void goToDashboard(MouseEvent mouseEvent) throws IOException {
        ancQuickcheckPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancQuickcheckPage.getChildren().add(load);
    }

    public void search(KeyEvent keyEvent) {
        String search = txtSearch.getText();
        if (search.isEmpty()) {
            try {
                loadTableData();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Failed to search").show();
            }
        }else {
            try {
                ArrayList<QuickcheckDto> checkList = quickcheckModel.searchQuickcheck(search);
                tblQuickcheck.setItems(FXCollections.observableArrayList(
                        checkList.stream()
                                .map(quickcheckDto -> new QuickcheckTM(
                                        quickcheckDto.getCheckId(),
                                        quickcheckDto.getMaintenanceId(),
                                        quickcheckDto.getCheckType(),
                                        quickcheckDto.getStatus()
                                )).toList()
                ));
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Failed to search").show();
            }
        }
    }
}
