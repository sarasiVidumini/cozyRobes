package lk.ijse.cozyrobes.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cozyrobes.dto.CartDto;
import lk.ijse.cozyrobes.dto.OrderDto;
import lk.ijse.cozyrobes.dto.ProductDto;
import lk.ijse.cozyrobes.dto.tm.CartTM;
import lk.ijse.cozyrobes.model.*;


import java.net.URL;
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

    public void loadProductDetails(String productId){
        try {

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load product details...!").show();
        }
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

    public void goToDashBoardPage(MouseEvent mouseEvent) {
    }

    public void searchCustomerContact(KeyEvent keyEvent) {
        String contact = txtCustomerContact.getText();
        try {
            if (contact.isEmpty()){
//                lblCustomerName.setText();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "error , Fail to load data..!", ButtonType.OK).show();
        }
    }

    public void goToCustomerPopUp(MouseEvent mouseEvent) {
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

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
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



}
