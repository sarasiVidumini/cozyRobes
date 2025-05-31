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
import lk.ijse.cozyrobes.dto.PaymentDto;
import lk.ijse.cozyrobes.dto.tm.PaymentTM;
import lk.ijse.cozyrobes.model.PaymentModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PaymentPageController implements Initializable {
    public AnchorPane ancPaymentPage;
    private final PaymentModel paymentModel = new PaymentModel();
    public TableView<PaymentTM> tblPayment;
    public TableColumn<PaymentTM , String> colPaymentId;
    public TableColumn<PaymentTM , String> colOrderId;
    public TableColumn<PaymentTM , String> colPaymentMethod;
    public TableColumn<PaymentTM , Double> colTotalAmount;
    public Label lblPaymentId;
    public ComboBox<String> cmbOrderPlatform;
    public TextField txtPaymentMethod;
    public TextField txtTotalAmount;

    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;
    public TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        cmbOrderPlatform.setItems(FXCollections.observableArrayList(
                IntStream.rangeClosed(1, 50)
                        .mapToObj(i -> String.format("O%03d", i))
                        .collect(Collectors.toList())
        ));

        try{
            loadTableData();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load table!").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = paymentModel.getNextPaymentId();
        lblPaymentId.setText(nextId);
    }

    private void loadTableData() throws SQLException {
        tblPayment.setItems(FXCollections.observableArrayList(
                paymentModel.getAllPayment().stream()
                        .map(paymentDto -> new PaymentTM(
                                paymentDto.getPaymentId(),
                                paymentDto.getOrderId(),
                                paymentDto.getPaymentMethod(),
                                paymentDto.getTotalAmount()
                        )).toList()
        ));
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
        "Are you sure you want delete this payment ?",
                ButtonType.YES, ButtonType.NO
        );
        alert.setTitle("Confirm Delete");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            String paymentId = lblPaymentId.getText();
            try {
                boolean isDeleted = paymentModel.deletePayment(paymentId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment deleted successfully!").show();
                    resetPage();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Error deleting payment!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR , "Failed to delete payment").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String paymentId = lblPaymentId.getText();
        String orderId = cmbOrderPlatform.getValue();
        String paymentMethod = txtPaymentMethod.getText();
        double totalAmount = Double.parseDouble(txtTotalAmount.getText());

        if (paymentId.isEmpty() || paymentMethod.isEmpty() || totalAmount==0.0) {
        new Alert(Alert.AlertType.ERROR, "Please fill out all fields!").show();
        return;
        }

        PaymentDto paymentDto = new PaymentDto(
                paymentId,
                orderId,
                paymentMethod,
                totalAmount
        );

        try {
            boolean isUpdated = paymentModel.updatePayment(paymentDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Payment updated successfully!").show();
                resetPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "Error updating payment!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to update payment!").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String paymentId = lblPaymentId.getText();
        String orderId = cmbOrderPlatform.getValue();
        String paymentMethod = txtPaymentMethod.getText();
        double totalAmount = Double.parseDouble(txtTotalAmount.getText());

        if (paymentId.isEmpty() || paymentMethod.isEmpty() || totalAmount==0.0) {
            new Alert(Alert.AlertType.ERROR, "Please fill out all fields!").show();
            return;
        }

        PaymentDto paymentDto = new PaymentDto(
                paymentId,
                orderId,
                paymentMethod,
                totalAmount
        );

        try {
            boolean isSaved = paymentModel.savePayment(paymentDto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Payment saved successfully!").show();
                resetPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "Fail to save payment!").show();
            }
        }catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save payment!").show();
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void onClickedTable(MouseEvent mouseEvent) {
        PaymentTM selectedItem = tblPayment.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblPaymentId.setText(selectedItem.getPaymentId());
            cmbOrderPlatform.setValue(selectedItem.getOrderId());
            txtPaymentMethod.setText(selectedItem.getPaymentMethod());
            txtTotalAmount.setText(String.valueOf(selectedItem.getTotalAmount()));

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            cmbOrderPlatform.getSelectionModel().clearSelection();
            txtPaymentMethod.clear();
            txtTotalAmount.clear();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to reset page!").show();
        }
    }

    public void goToDashboard(MouseEvent mouseEvent) throws IOException {
        ancPaymentPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancPaymentPage.getChildren().add(load);
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
                ArrayList<PaymentDto> paymentList = paymentModel.searchPayment(search);
                tblPayment.setItems(FXCollections.observableArrayList(
                        paymentList.stream()
                                .map(paymentDto -> new PaymentTM(
                                        paymentDto.getPaymentId(),
                                        paymentDto.getOrderId(),
                                        paymentDto.getPaymentMethod(),
                                        paymentDto.getTotalAmount()
                                )).toList()
                ));
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search!").show();
            }
        }
    }
}


