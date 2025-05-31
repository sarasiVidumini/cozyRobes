package lk.ijse.cozyrobes.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import lk.ijse.cozyrobes.dto.DeliveryDto;
import lk.ijse.cozyrobes.dto.tm.DeliveryTM;
import lk.ijse.cozyrobes.model.DeliveryModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DeliveryPageController implements Initializable {
    public AnchorPane ancDeliveryPage;
    private final DeliveryModel deliveryModel = new DeliveryModel();

    public TableView<DeliveryTM> tblDelivery;
    public TableColumn<DeliveryTM, String> colId;
    public TableColumn<DeliveryTM, String> colOrdersPlatform;
    public TableColumn<DeliveryTM, String> colAddress;
    public TableColumn<DeliveryTM, String> colStatus;

    public Label lblDeliveryId;
    public ComboBox<String> cmbOrdersPlatform;
    public TextField txtAddress;
    public TextField txtStatus;

    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;
    public TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colOrdersPlatform.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Generate O001 to O050 dynamically
        cmbOrdersPlatform.setItems(FXCollections.observableArrayList(
                IntStream.rangeClosed(1, 50)
                        .mapToObj(i -> String.format("O%03d", i))
                        .collect(Collectors.toList())
        ));

        try {
            loadTableData();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to initialize delivery page").show();
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        /*try {
            String nextId = "D001"; // You can fetch max ID from DB and increment
            lblDeliveryId.setText(nextId);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to generate next Delivery ID").show();
        }*/
        String deliveryId = deliveryModel.getNextDeliveryId();
        lblDeliveryId.setText(deliveryId);

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this delivery?",
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirmation");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            String deliveryId = lblDeliveryId.getText();
            try {
                boolean isDeleted = deliveryModel.deleteDelivery(deliveryId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Delivery deleted successfully").show();
                    resetPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete Delivery").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to delete Delivery").show();
            }
        }
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String deliveryId = lblDeliveryId.getText();
        String orderId = cmbOrdersPlatform.getValue();
        String address = txtAddress.getText();
        String status = txtStatus.getText();

        if (deliveryId.isEmpty() || orderId == null || address.isEmpty() || status.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").show();
            return;
        }

        DeliveryDto deliveryDto = new DeliveryDto(deliveryId, orderId, address, status);

        try {
            boolean isUpdated = deliveryModel.updateDelivery(deliveryDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Delivery updated successfully").show();
                resetPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Delivery").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while updating").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String deliveryId = lblDeliveryId.getText();
        String orderId = cmbOrdersPlatform.getValue();
        String address = txtAddress.getText();
        String status = txtStatus.getText();

        if (deliveryId.isEmpty() || orderId == null || address.isEmpty() || status.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").show();
            return;
        }

        DeliveryDto deliveryDto = new DeliveryDto(deliveryId, orderId, address, status);

        try {
            boolean isSaved = deliveryModel.saveDelivery(deliveryDto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Delivery saved successfully").show();
                resetPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Delivery").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while saving").show();
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();
            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
            cmbOrdersPlatform.getSelectionModel().clearSelection();
            txtAddress.clear();
            txtStatus.clear();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to reset page").show();
        }
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblDelivery.setItems(FXCollections.observableArrayList(
                deliveryModel.getAllDelivery().stream()
                        .map(dto -> new DeliveryTM(
                                dto.getDeliveryId(),
                                dto.getOrderId(),
                                dto.getAddress(),
                                dto.getStatus()))
                        .toList()
        ));
    }

    public void onClickedTable(MouseEvent mouseEvent) {
        DeliveryTM selected = tblDelivery.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblDeliveryId.setText(selected.getDeliveryId());
            cmbOrdersPlatform.setValue(selected.getOrderId());
            txtAddress.setText(selected.getAddress());
            txtStatus.setText(selected.getStatus());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    public void goToDashboard(MouseEvent mouseEvent) throws IOException {
        ancDeliveryPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancDeliveryPage.getChildren().add(load);
    }

    public void search(KeyEvent keyEvent) {
        String search = txtSearch.getText();
        if (search.isEmpty()) {
            try {
                loadTableData();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search").show();
            }

        }else {
            try {
                ArrayList<DeliveryDto> deliveryList = deliveryModel.searchDelivery(search);
                 tblDelivery.setItems(FXCollections.observableArrayList(
                         deliveryList.stream()
                                 .map(deliveryDto -> new DeliveryTM(
                                         deliveryDto.getDeliveryId(),
                                         deliveryDto.getOrderId(),
                                         deliveryDto.getAddress(),
                                         deliveryDto.getStatus()
                                 )).toList()
                 ));

            }catch(Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search").show();
            }
        }
    }
}
