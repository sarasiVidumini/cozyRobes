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
    public TableColumn<OrderDetailsTM , Double> colUpdatePrice;
    public Label lblOrderDetailId;
    public ComboBox<String> cmbOrderPlatform;
    public ComboBox<String> cmbProductPlatform;
    public TextField txtOrderQuantity;
    public TextField txtPricePurchase;
    public TextField txtUpdatePrice;
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
        colUpdatePrice.setCellValueFactory(new PropertyValueFactory<>("updatePrice"));

        cmbOrderPlatform.setItems(FXCollections.observableArrayList(
                IntStream.rangeClosed(1, 50)
                        .mapToObj(i -> String.format("O%03d", i))
                        .collect(Collectors.toList())
        ));

        cmbProductPlatform.setItems(FXCollections.observableArrayList("P001" , "P002"));

        try {
            loadTableData();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to load data!").show();
            ;
        }
    }

    private void loadNextId(){
        try {
            String nextId = orderDetailModel.getNextOrderDetailId();
            lblOrderDetailId.setText(nextId);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to load data!").show();
        }
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
                    resetPage();
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
        String orderId = (String) cmbOrderPlatform.getValue();
        String productId = (String) cmbProductPlatform.getValue();
        int quantity = Integer.parseInt(txtOrderQuantity.getText());
        double priceAtPurchase = Double.parseDouble(txtPricePurchase.getText());
        double updatePrice = Double.parseDouble(txtUpdatePrice.getText());

        if (orderDetailId.isEmpty() ||  quantity <= 0) {
            new Alert(Alert.AlertType.ERROR, "Please fill out all fields!").show();
            return;

        }

        OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                orderDetailId,
                orderId,
                productId,
                quantity,
                priceAtPurchase,
                updatePrice
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
            String orderId = (String) cmbOrderPlatform.getValue();
            String productId = (String) cmbProductPlatform.getValue();
            int quantity = Integer.parseInt(txtOrderQuantity.getText());
            double priceAtPurchase = Double.parseDouble(txtPricePurchase.getText());
            double updatePrice = Double.parseDouble(txtUpdatePrice.getText());

            if (orderDetailId.isEmpty() ||  quantity <= 0) {
                new Alert(Alert.AlertType.ERROR, "Please fill out all fields!").show();
                return;
            }

            OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                    orderDetailId,
                    orderId,
                    productId,
                    quantity,
                    priceAtPurchase,
                    updatePrice
            );

            try {
                boolean isSaved = orderDetailModel.saveOrderDetails(orderDetailsDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "OrderDetails saved successfully!").show();
                    loadTableData();
                    loadNextId();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Error adding order details!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Failed to update order details!").show();
                return;
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
                        orderDetailsDto.getPriceAtPurchase(),
                        orderDetailsDto.getUpdatePrice()
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

           cmbOrderPlatform.getSelectionModel().clearSelection();
           cmbProductPlatform.getSelectionModel().clearSelection();
           txtOrderQuantity.setText("");
           txtPricePurchase.setText("");
           txtUpdatePrice.setText("");

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
                                        orderDetailsDto.getPriceAtPurchase(),
                                        orderDetailsDto.getUpdatePrice()
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
            cmbOrderPlatform.setValue(selectedItem.getOrderId());
            cmbProductPlatform.setValue(selectedItem.getProductId());
            txtOrderQuantity.setText(String.valueOf(selectedItem.getQuantity()));
            txtPricePurchase.setText(String.valueOf(selectedItem.getPriceAtPurchase()));
            txtUpdatePrice.setText(String.valueOf(selectedItem.getUpdatePrice()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        System.out.println("Place order clicked");

        if (tblORDetails.getItems().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "There are no order details to place!").show();
            return;
        }

            try {
                System.out.println("Confirmed to place order");
                // Convert TableView items to a list of DTOs
                OrderDetailsDto dto = new OrderDetailsDto();
                for (OrderDetailsTM tm : tblORDetails.getItems()) {
                    dto = new OrderDetailsDto(
                            null,
                            tm.getOrderId(),
                            tm.getProductId(),
                            tm.getQuantity(),
                            tm.getPriceAtPurchase(),
                            tm.getUpdatePrice()
                    );
                }

                // Save order details via model
                boolean isPlaced = orderDetailModel.addOrderDetails(dto);
                System.out.println("Order placement returned: " + isPlaced);

                if (isPlaced) {
                    new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!").show();
                    resetPage(); // clear all fields and reload table
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to place order! Please try again.").show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while placing order!").show();
            }
    }


}
