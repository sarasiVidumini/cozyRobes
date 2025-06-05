package lk.ijse.cozyrobes.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.CartDto;
import lk.ijse.cozyrobes.dto.OrderDto;
import lk.ijse.cozyrobes.dto.PaymentDto;
import lk.ijse.cozyrobes.dto.ProductDto;
import lk.ijse.cozyrobes.dto.tm.CartTM;
import lk.ijse.cozyrobes.model.*;


import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CartController implements Initializable {

    public AnchorPane ancOrderPlacementPage;
    public Label homeLabel;
    public Label lblOrderId;
    public Label orderPlacementDate;
    public TextField txtCustomerContact;
    public Label lblCustomerName;
    public Label labelPopUpCustomer;
    public ComboBox<String> cmbProductId;
    public Label lblProductName;
    public Label lblProductPrice;
    public Label txtAddToCartQty;
    public TextField txtCartQty;
    public ComboBox<String> cmbPaymentMethod;
    public TableView<CartTM> tblOrderPlacement;
    public TableColumn<CartTM , String> colCustomerId;
    public TableColumn<CartTM , String> colProductId;
    public TableColumn<CartTM ,String> colProductName;
    public TableColumn<CartTM , Integer> colQuantity;
    public TableColumn<CartTM , Double> qtyPrice;
    public TableColumn<CartTM , Double> colTotal;
    public TableColumn<CartTM , String> colPaymentMethod;
    public TableColumn<?,?> colAction;

    private final OrderModel orderModel = new OrderModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final PaymentModel paymentModel = new PaymentModel();
    private final ProductModel productModel = new ProductModel();
    private final OrderDetailsModel orderDetailsModel = new OrderDetailsModel();

    private final ObservableList<CartTM> cartData = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "error , Fail to load data..!", ButtonType.OK).show();
        }
        cmbPaymentMethod.setItems(FXCollections.observableArrayList("Cash" , "Card Payment"));

        cmbProductId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadProductDetails((String) newValue);
            }
        });

    }

    public void setCellValues() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        qtyPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));

        tblOrderPlacement.setItems(cartData);
    }


    public void loadNextId() {
        try {
            String nextId = orderModel.getNextOrderId();
            lblOrderId.setText(nextId);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "error , Fail to load next order Id..!", ButtonType.OK).show();
        }
    }

    public void loadProductDetails(String productId){
        try {
            ProductDto product = productModel.getProductByIds(productId);
            if (product != null) {
                lblProductName.setText(product.getName());
                txtAddToCartQty.setText(String.valueOf(product.getQuantity()));
                lblProductPrice.setText(String.valueOf(product.getUnitPrice()));
            }else {
                lblProductName.setText("");
                txtCartQty.setText("");
                lblProductPrice.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load product details...!").show();
        }
    }


    public void goToDashBoardPage(MouseEvent mouseEvent) {
        navigateTo("/view/DashBoardPage.fxml");
    }

    public void searchCustomerContact(KeyEvent keyEvent) {
        String contact = txtCustomerContact.getText();
        try {
            if (contact.isEmpty()){
                lblCustomerName.setText("");
                return;
            }
            String customerId = customerModel.getCustomerIdByContact(contact);
            if (customerId != null) {
                lblCustomerName.setText(customerModel.getCustomerIdByContact(customerId));
            }else {
                lblCustomerName.setText("No customer found with this contact");
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "error , Fail to load data..!", ButtonType.OK).show();
        }
    }

    public void goToCustomerPopUp(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(""));
            AnchorPane anchorPane = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            stage.setTitle("Add New Customer");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "error , Fail to load customer pop-up..!", ButtonType.OK).show();
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void resetPage() {
        loadNextId();
        loadCustomerIds();
        loadProductIds();

        txtCustomerContact.setText("");
        lblCustomerName.setText("");
        cmbProductId.setValue("");
        lblProductName.setText("");
        txtCartQty.setText("");
        txtAddToCartQty.setText("");
        lblProductPrice.setText("");
        cartData.clear();
        tblOrderPlacement.refresh();
    }

    public void loadCustomerIds(){
        try {
            ArrayList<String> customerIdList = customerModel.getAllCustomerIds();
            ObservableList<String> customerIds = FXCollections.observableArrayList();
            customerIds.addAll(customerIdList);
            cmbProductId.setItems(customerIds);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "error , Fail to load data..!", ButtonType.OK).show();
        }
    }

    public void loadProductIds() {
        try {
            ArrayList<String> productIdsList =  productModel.getAllProductIds();
            ObservableList<String> productIds = FXCollections.observableArrayList();
            productIds.addAll(productIdsList);
            cmbProductId.setItems(productIds);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "error , Fail to load data..!", ButtonType.OK).show();
        }
    }


    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String orderId = lblOrderId.getText();
            String customerContact= txtCustomerContact.getText();
            String customerId = customerModel.getCustomerIdByContact(customerContact);
            String status = "Shipped";

            String paymentId = paymentModel.getNextPaymentId();
            String paymentMethod = (String) cmbPaymentMethod.getSelectionModel().getSelectedItem();
            double totalAmount = cartData.stream().mapToDouble(CartTM::getTotal).sum();

//            boolean orderSaved = orderModel.saveNewOrder(
//                    orderId,
//                    customerId,
//                    orderDate,
//                    status,
//                    productId
//            );

//            if (!orderSaved) {
//                connection.rollback();
//                new Alert(Alert.AlertType.ERROR, "error , Fail to save order data..!", ButtonType.OK).show();
//                return;
//            }

            boolean allProductSaved = true;
            for (CartTM cartTM : cartData) {
                String orderDetailId = orderDetailsModel.getNextOrderDetailId();
                boolean productSaved = orderDetailsModel.saveNewOrderDetails(
                        orderDetailId,
                        orderId,
                        cartTM.getProductId(),
                        cartTM.getCartQty(),
                        cartTM.getUnitPrice() * cartTM.getCartQty()
                );
                boolean productUpdated = productModel.reduceQty(
                        cartTM.getProductId(),
                        cartTM.getCartQty()
                );
                if (!productSaved || !productUpdated) {
                    allProductSaved = false;
                    break;
                }
            }

            if (!allProductSaved) {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "error , Fail to save order details or update product..!", ButtonType.OK).show();
                return;
            }

            boolean paymentSaved = paymentModel.savePayment(
                    new PaymentDto(
                            paymentId,
                            orderId,
                            paymentMethod,
                            totalAmount
                    )
            );

            if (!paymentSaved) {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "error , Fail to save payment..!", ButtonType.OK).show();
                return;
            }
            connection.commit();
            new Alert(Alert.AlertType.INFORMATION, "Order placed successfully..!", ButtonType.OK).show();
            resetPage();

        } catch (Exception e) {
            try {
                if (connection != null) {}
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "error , placing order..!", ButtonType.OK).show();
        }finally {
            try {
                if (connection != null) connection.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String selectedItem = cmbProductId.getSelectionModel().getSelectedItem();
        String cartQtyString = txtCartQty.getText();

        if (selectedItem == null) {
            new Alert(Alert.AlertType.ERROR, "please select item", ButtonType.OK).show();
            return;
        }

        if (!cartQtyString.matches("^[0-9]+$")){
            new Alert(Alert.AlertType.ERROR, "invalid quantity", ButtonType.OK).show();
            return;
        }

        String productQtyStockString = txtAddToCartQty.getText();
        int cartQty = Integer.parseInt(cartQtyString);
        int productQtyOnStock = Integer.parseInt(productQtyStockString);

        if (productQtyOnStock <= cartQty) {
            new Alert(Alert.AlertType.ERROR, "invalid quantity", ButtonType.OK).show();
            return;
        }

        String selectedCustomerId = lblCustomerName.getText();
        String prductName = lblProductName.getText();
        String productUnitPriceString = lblProductPrice.getText();

        double productUnitPrice = Double.parseDouble(productUnitPriceString);
        double total = productUnitPrice*cartQty;
        String paymentMethod = cmbPaymentMethod.getSelectionModel().getSelectedItem();

        if (paymentMethod == null) {
            new Alert(Alert.AlertType.ERROR, "please select paymentMethod", ButtonType.OK).show();
            return;
        }

        for(CartTM cartTM : cartData){
            if (cartTM.getProductId().equals(selectedItem)){
                int newQty = cartTM.getCartQty() + cartQty;
                if (productQtyOnStock < newQty){
                    new Alert(Alert.AlertType.ERROR, "Not enough product quantity...!", ButtonType.OK).show();
                    return;
                }
                cartTM.setCartQty(newQty);
                cartTM.setTotal(newQty*productUnitPrice);
                tblOrderPlacement.refresh();
                txtAddToCartQty.setText(String.valueOf(productQtyOnStock - cartQty));
                return;
            }
        }

        Button removeBtn = new Button("Remove");
        CartTM cartTM = new CartTM(
                selectedCustomerId,
                selectedItem,
                prductName,
                cartQty,
                productUnitPrice,
                total,
                paymentMethod,
                removeBtn
        );

        removeBtn.setOnAction(action -> {
            cartData.remove(cartTM);
            tblOrderPlacement.refresh();
        });

        cartData.add(cartTM);

        txtAddToCartQty.setText(String.valueOf(productQtyOnStock - cartQty));
    }

     private void refreshPage() throws SQLException {
        lblOrderId.setText(orderModel.getNextOrderId());

        loadCustomerIds();
        loadProductIds();
     }

     private void resetWhenAddToCart() {
        cmbProductId.getSelectionModel().clearSelection();
        txtCartQty.clear();
        lblProductName.setText("");
        txtAddToCartQty.setText("");
        lblProductPrice.setText("");
        cmbPaymentMethod.getSelectionModel().clearSelection();
     }
     private void navigateTo(String path) {
            try {
                ancOrderPlacementPage.getChildren().clear();

                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

                anchorPane.prefWidthProperty().bind(ancOrderPlacementPage.widthProperty());
                anchorPane.prefHeightProperty().bind(ancOrderPlacementPage.heightProperty());

                ancOrderPlacementPage.getChildren().add(anchorPane);
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "error , Fail to load data..!", ButtonType.OK).show();
            }
    }
}