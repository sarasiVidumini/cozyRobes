package lk.ijse.cozyrobes.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cozyrobes.dto.CustomerDto;
import lk.ijse.cozyrobes.dto.ProductDto;
import lk.ijse.cozyrobes.dto.tm.CustomerTM;
import lk.ijse.cozyrobes.dto.tm.MaintenanceTM;
import lk.ijse.cozyrobes.dto.tm.ProductTM;
import lk.ijse.cozyrobes.model.ProductModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductPageController implements Initializable {
    public AnchorPane ancProductPage;
    public Label lblProductId;
    public TextField txtProductName;
    public TextField txtProductQty;
    public TextField txtCategory;
    public TextField txtUnitPrice;

    private final ProductModel productModel = new ProductModel();

    public TableView<ProductTM> tblProduct;
    public TableColumn<ProductTM , String> colProductId;
    public TableColumn<ProductTM , String> colProductName;
    public TableColumn <ProductTM, Integer> colQty;
    public TableColumn <ProductTM, String> colCategory;
    public TableColumn <ProductTM , Double> colUnitPrice;

    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;


//    public void btnGoDshBoardOnAction(ActionEvent actionEvent) throws IOException {
//        ancProductPage.getChildren().clear();
//        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
//        ancProductPage.getChildren().add(load);
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     colProductId.setCellValueFactory(new PropertyValueFactory<>("product_id"));
     colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
     colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
     colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
     colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

        try {
        loadTableData();
        loadNextId();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
             new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
        }
    }

    private void loadTableData() throws SQLException {
//        ArrayList<ProductDto> productDtoArrayList = productModel.getAllProduct();
//        ObservableList<ProductDto> productTMS = FXCollections.observableArrayList();
//
//        for (ProductDto productDto : productDtoArrayList) {
//                ProductTM productTM = new ProductTM(
//                        productDto.getProductId(),
//                        productDto.getName(),
//                        productDto.getQuantity(),
//                        productDto.getCategory(),
//                        productDto.getUnitPrice()
//            );
//
//                productTMS.add(productTM);
//        }
//
//        tblProduct.setItems(productTMS);

        tblProduct.setItems(FXCollections.observableArrayList(
                productModel.getAllProduct().stream()
                        .map(productDto -> new ProductTM(
                                productDto.getProduct_id(),
                                productDto.getName(),
                                productDto.getQuantity(),
                                productDto.getCategory(),
                                productDto.getUnit_price()
                        )).toList()
        ));
    }


    private void resetPage(){
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);

            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            txtProductName.setText("");
            txtProductQty.setText("");
            txtCategory.setText("");
            txtUnitPrice.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
        }
    }
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {

            String productId = lblProductId.getText();
            try {
                boolean isDeleted = productModel.deleteProduct(productId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Product deleted successfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete product.").show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete product").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String product_id = txtProductName.getText();
        String name = txtProductName.getText();
        int quantity = Integer.parseInt(txtProductQty.getText());
        String category = txtCategory.getText();
        double unit_price = Double.parseDouble(txtUnitPrice.getText());

        ProductDto productDto = new ProductDto(
                product_id,
                name,
                quantity,
                category,
                unit_price
        );

        try {
            boolean isUpdated = productModel.updateProduct(productDto);

            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION,"Product updated successfully!").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Fail to update product.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail to update product").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String product_id = lblProductId.getText();
        String name = txtProductName.getText();
        int quantity = Integer.parseInt(txtProductQty.getText());
        String category = txtCategory.getText();
        double unit_price = Double.parseDouble(txtUnitPrice.getText());

        ProductDto productDto = new ProductDto(
                product_id,
                name,
                quantity,
                category,
                unit_price
        );

        try {
            boolean isSaved = productModel.saveProduct(productDto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION,"Product saved successfully!").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Fail to save product").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail to save product").show();
        }
    }

    private void loadNextId() throws SQLException {
        try {
            String nextId = "P001"; // Dummy logic; replace with DB fetch if needed
            lblProductId.setText(nextId);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to generate next Product ID").show();
        }
    }

    public void btnGoDashBoardPageOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        ancProductPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancProductPage.getChildren().setAll(load);
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void onClickTable(MouseEvent mouseEvent) {
        ProductTM selectedItem = tblProduct.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblProductId.setText(selectedItem.getProduct_id());
            txtProductName.setText(selectedItem.getName());
            txtProductQty.setText(String.valueOf(selectedItem.getQuantity()));
            txtCategory.setText(selectedItem.getCategory());
            txtUnitPrice.setText(String.valueOf(selectedItem.getUnit_price()));


            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}


