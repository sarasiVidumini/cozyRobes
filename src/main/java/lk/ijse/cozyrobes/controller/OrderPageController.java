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
import lk.ijse.cozyrobes.dto.OrderDto;
import lk.ijse.cozyrobes.dto.tm.OrdersTM;
import lk.ijse.cozyrobes.model.OrderModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OrderPageController implements Initializable {
    private final OrderModel orderModel = new OrderModel();
    public AnchorPane ancOrdersPage;
    public TableView<OrdersTM> tblOrders;
    public TableColumn<OrdersTM ,String> colOrderId;
    public TableColumn<OrdersTM , String> colCustomerId;
    public TableColumn<OrdersTM , String> colOrderDate;
    public TableColumn<OrdersTM , String> colOrderStatus;
    public TableColumn<OrdersTM , String> colProductId;
    public Label lblOrderId;
    public TextField txtOrderDate;
    public TextField txtOrderStatus;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;
    public TextField txtSearch;
    public ComboBox<String> cmbCustomerPlatform;
    public ComboBox<String> cmbProductPlatform;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colOrderStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));

        cmbCustomerPlatform.setItems(FXCollections.observableArrayList(
                IntStream.rangeClosed(1,50)
                        .mapToObj(i -> String.format("0%03d" , i))
                        .collect(Collectors.toList())
        ));

        cmbProductPlatform.setItems(FXCollections.observableArrayList("P001" , "P002"));

        try {
            loadTableData();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR , "Failed to load orders data").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = orderModel.getNextOrderId();
        lblOrderId.setText(nextId);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this order ?",
                ButtonType.YES,
                ButtonType.NO
                );
        alert.setTitle("Confirmation");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            String orderId = lblOrderId.getText();
            try {
                boolean isDeleted = orderModel.deleteOrder(orderId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Order deleted successfully").show();
                    resetPage();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete order").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR , "Failed to load orders data").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String orderId = lblOrderId.getText();
        String customerId = cmbCustomerPlatform.getValue();
        String orderDate = txtOrderDate.getText();
        String status = txtOrderStatus.getText();
        String productId = cmbProductPlatform.getValue();

        if(orderId.isEmpty() || orderDate.isEmpty() || status.isEmpty() || productId.isEmpty()){
            new Alert(Alert.AlertType.ERROR , "Please fill out all fields").show();
            return;
        }

        OrderDto orderDto = new OrderDto(orderId, customerId, orderDate, status, productId);

        try {
            boolean isUpdated = orderModel.updateOrder(orderDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Order updated successfully").show();
                resetPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to update order").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR , "Failed to load orders data").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String orderId = lblOrderId.getText();
        String customerId = cmbCustomerPlatform.getValue();
        String orderDate = txtOrderDate.getText();
        String status = txtOrderStatus.getText();
        String productId = cmbProductPlatform.getValue();

        if(orderId.isEmpty() || orderDate.isEmpty() || status.isEmpty() || productId.isEmpty()){
            new Alert(Alert.AlertType.ERROR , "Please fill out all fields").show();
            return;
        }

        OrderDto orderDto = new OrderDto(orderId, customerId, orderDate, status, productId);

        try {
            boolean isSaved = orderModel.saveOrder(orderDto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Order saved successfully").show();
                resetPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to save order").show();

            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR , "Failed to load orders data").show();
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void loadTableData() throws Exception {
        tblOrders.setItems(FXCollections.observableArrayList(
                orderModel.getAllOrder()
                        .stream()
                        .map(orderDto -> new OrdersTM(
                                orderDto.getOrderId(),
                                orderDto.getCustomerId(),
                                orderDto.getOrderDate(),
                                orderDto.getStatus(),
                                orderDto.getProductId()
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
            cmbCustomerPlatform.getSelectionModel().clearSelection();
            cmbCustomerPlatform.getSelectionModel().clearSelection();
            txtOrderDate.clear();
            txtOrderStatus.clear();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR , "Failed to load orders data").show();

        }
    }

    public void goToDashboard(MouseEvent mouseEvent) throws IOException {
        ancOrdersPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancOrdersPage.getChildren().add(load);
    }

    public void search(KeyEvent keyEvent) {
        String search = txtSearch.getText();
        if (search.isEmpty()) {
            try {
                loadTableData();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR , "Failed to load orders data").show();
            }
        }else {
            try {
                ArrayList<OrderDto> orderList = orderModel.searchOrder(search);
                tblOrders.setItems(FXCollections.observableArrayList(
                        orderList.stream()
                                .map(orderDto -> new OrdersTM(
                                        orderDto.getOrderId(),
                                        orderDto.getCustomerId(),
                                        orderDto.getOrderDate(),
                                        orderDto.getStatus(),
                                        orderDto.getProductId()
                                )).toList()
                ));
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR , "Failed to load orders data").show();
            }
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        OrdersTM selectedItem = tblOrders.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblOrderId.setText(selectedItem.getOrderId());
            cmbCustomerPlatform.setValue(selectedItem.getCustomerId());
            txtOrderDate.setText(selectedItem.getOrderDate());
            txtOrderStatus.setText(selectedItem.getStatus());
            cmbProductPlatform.setValue(selectedItem.getProductId());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
