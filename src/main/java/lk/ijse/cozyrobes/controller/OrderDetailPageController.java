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
import lk.ijse.cozyrobes.dto.OrderDetailsDto;
import lk.ijse.cozyrobes.dto.tm.OrderDetailsTM;
import lk.ijse.cozyrobes.model.OrderDetailsModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OrderDetailPageController implements Initializable {
    public AnchorPane ancORDetailsPage;

    private final static OrderDetailsModel orderDetailModel = new OrderDetailsModel();

    public TableView<OrderDetailsTM> tblORDetails;
    public TableColumn<OrderDetailsTM , String> colOrderDetailId;
    public TableColumn<OrderDetailsTM , String> colOrderId;
    public TableColumn<OrderDetailsTM , String> colProductId;
    public TableColumn<OrderDetailsTM , Integer>colOrderQuantity;
    public TableColumn<OrderDetailsTM , Double>colPriceAtPurchase;
    public TableColumn<OrderDetailsTM , Double> colUpdatePrice; // This column is not used in the provided code
    public Label lblOrderDetailId;

    public ComboBox<String> cmbOrderId;
    public ComboBox<String> cmbProductPlatform;
    public TextField txtOrderQuantity;
    public ComboBox<Double> cmbPriceAtPurchasePlatform; // Changed to ComboBox<Double>
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;
    public TextField txtSearch;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderDetailId.setCellValueFactory(new PropertyValueFactory<>("orderDetailId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colOrderQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPriceAtPurchase.setCellValueFactory(new PropertyValueFactory<>("priceAtPurchase"));

        cmbProductPlatform.setItems(FXCollections.observableArrayList("P001" , "P002"));
        try {
            cmbProductPlatform.setItems(FXCollections.observableArrayList(orderDetailModel.getAllOrderIds()));
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to load product Ids").show();

        }
        // Populate cmbPriceAtPurchasePlatform with actual Double values
        cmbPriceAtPurchasePlatform.setItems(FXCollections.observableArrayList(2100.00 , 8500.00));

        cmbOrderId.setItems(FXCollections.observableArrayList(
                IntStream.rangeClosed(1, 50)
                        .mapToObj(i -> String.format("O%03d", i))
                        .collect(Collectors.toList())
        ));

        try{
            cmbOrderId.setItems(FXCollections.observableArrayList(orderDetailModel.getAllOrderIds()));
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load table!").show();
        }

        try {
            loadTableData();
            loadNextId();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to load data!").show();
            ;
        }
    }

    private void loadNextId() throws SQLException {

        String nextId = orderDetailModel.getNextOrderDetailId();
        lblOrderDetailId.setText(nextId);

    }


    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this orderDetail ?",
                ButtonType.YES, ButtonType.NO
        );
        alert.setTitle("Confirm Delete");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String orderDetailId = lblOrderDetailId.getText();

            try {
                boolean isDeleted = orderDetailModel.deleteOrderDetails(orderDetailId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "OrderDetails deleted successfully!").show();
                    loadTableData();

                }else {
                    new Alert(Alert.AlertType.ERROR, "Error deleting order details!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Failed to delete order detail").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String orderDetailId = lblOrderDetailId.getText();
        String orderId = cmbOrderId.getValue();
        String productId = cmbProductPlatform.getValue(); // No cast needed if cmbProductPlatform is ComboBox<String>
        int quantity = 0;
        try {
            quantity = Integer.parseInt(txtOrderQuantity.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid quantity! Please enter a number.").show();
            return;
        }

        Double priceAtPurchase = cmbPriceAtPurchasePlatform.getValue(); // Directly get Double

        if (orderDetailId.isEmpty() || quantity <= 0 || priceAtPurchase == null) { // Check for null priceAtPurchase
            new Alert(Alert.AlertType.ERROR, "Please fill out all fields!").show();
            return;

        }

        OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                orderDetailId,
                orderId,
                productId,
                quantity,
                priceAtPurchase
        );

        try {
            boolean isUpdated = orderDetailModel.updateOrderDetails(orderDetailsDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "OrderDetails updated successfully!").show();
                loadTableData();
                resetPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "Error updating order details!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to update order details!").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String orderDetailId = lblOrderDetailId.getText();
        String orderId = cmbOrderId.getValue();
        String productId = cmbProductPlatform.getValue(); // No cast needed
        int quantity = 0;
        try {
            quantity = Integer.parseInt(txtOrderQuantity.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid quantity! Please enter a number.").show();
            return;
        }

        Double priceAtPurchase = cmbPriceAtPurchasePlatform.getValue(); // Directly get Double


        if (orderDetailId.isEmpty() || quantity <= 0 || priceAtPurchase == null) { // Check for null priceAtPurchase
            new Alert(Alert.AlertType.ERROR, "Please fill out all fields!").show();
            return;
        }

        OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                orderDetailId,
                orderId,
                productId,
                quantity,
                priceAtPurchase
        );

        try {
            boolean isSaved = orderDetailModel.saveOrderDetails(orderDetailsDto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "OrderDetails saved successfully!").show();
                loadTableData();
                loadNextId();
                resetPage(); // Added resetPage after save
            }else {
                new Alert(Alert.AlertType.ERROR, "Error adding order details!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to save order details!").show(); // Changed message to save
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblORDetails.setItems(FXCollections.observableArrayList(
                orderDetailModel.getAllOrderDetails()
                        .stream()
                        .map(orderDetailsDto -> new OrderDetailsTM(
                                orderDetailsDto.getOrderDetailId(),
                                orderDetailsDto.getOrderId(),
                                orderDetailsDto.getProductId(),
                                orderDetailsDto.getQuantity(),
                                orderDetailsDto.getPriceAtPurchase()
                        )).toList()
        ));
    }

    public void resetPage() {
        try{
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            cmbOrderId.getSelectionModel().clearSelection();
            cmbProductPlatform.getSelectionModel().clearSelection();
            txtOrderQuantity.setText("");
            cmbPriceAtPurchasePlatform.getSelectionModel().clearSelection();
            txtSearch.setText(""); // Clear search field on reset


        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to reset page!").show();

        }
    }

    public void goToDashboard(MouseEvent mouseEvent) throws IOException {
        ancORDetailsPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancORDetailsPage.getChildren().add(load);
    }

    public void search(KeyEvent keyEvent) {
        String search = txtSearch.getText();
        if (search.isEmpty()) {
            try {
                loadTableData();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Failed to search!").show();
            }
        }else {
            try {
                ArrayList<OrderDetailsDto> orderDetailsList = orderDetailModel.searchOrderDetails(search);
                tblORDetails.setItems(FXCollections.observableArrayList(
                        orderDetailsList.stream()
                                .map(orderDetailsDto -> new OrderDetailsTM(
                                        orderDetailsDto.getOrderDetailId(),
                                        orderDetailsDto.getOrderId(),
                                        orderDetailsDto.getProductId(),
                                        orderDetailsDto.getQuantity(),
                                        orderDetailsDto.getPriceAtPurchase()
                                )).toList()
                ));
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Failed to search!").show();
            }
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        OrderDetailsTM selectedItem = tblORDetails.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblOrderDetailId.setText(selectedItem.getOrderDetailId());
            cmbOrderId.setValue(selectedItem.getOrderId());
            cmbProductPlatform.setValue(selectedItem.getProductId());
            txtOrderQuantity.setText(String.valueOf(selectedItem.getQuantity()));
            cmbPriceAtPurchasePlatform.setValue(selectedItem.getPriceAtPurchase()); // Set the Double value directly


            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private void loadOrderIds() throws SQLException, ClassNotFoundException {
        cmbOrderId.setItems(FXCollections.observableArrayList(orderDetailModel.getAllOrderIds()));
    }

    private void loadProductIds() throws SQLException, ClassNotFoundException {
        cmbProductPlatform.setItems(FXCollections.observableArrayList(orderDetailModel.getAllOrderIds()));

    }
}