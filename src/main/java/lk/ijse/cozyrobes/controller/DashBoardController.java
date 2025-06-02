package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DashBoardController {

    public AnchorPane ancDashBoardPage;

    public void btnGoCustomerPageOnAction(ActionEvent actionEvent) {
       navigateTo("/view/CustomerView.fxml");
    }

    public void btnGoUserViewOnAction(ActionEvent actionEvent)  {
        navigateTo("/view/UserView.fxml");
    }

    public void btnGoEmployeePageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/EmployeePage.fxml");
    }

    public void btnGoOrderPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/OrderPage.fxml");
    }

    public void btnGoSupplierViewOnAction(ActionEvent actionEvent) {
        navigateTo("/view/SupplierView.fxml");
    }

    public void btnGoProductPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/ProductPage.fxml");
    }

    public void btnGoDeliveryPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/DeliveryPage.fxml");
    }

    public void btnGoPaymentPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/PaymentView.fxml");
    }

    public void btnGoOrderDetailsPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/OrderDetailsPage.fxml");
    }

    public void btnGoWarehouseViewOnAction(ActionEvent actionEvent) {
        navigateTo("/view/WarehouseView.fxml");
    }

    public void btnGoMaterialInventoryViewOnAction(ActionEvent actionEvent) {
        navigateTo("/view/MaterialInventoryView.fxml");
    }

    public void btnGoMaintenanceViewOnAction(ActionEvent actionEvent) {
        navigateTo("/view/MaintenanceView.fxml");
    }

    public void btnGoQuickcheckPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/QuickcheckPage.fxml");
    }

    public void GoToLoginPage(MouseEvent mouseEvent) {
        navigateTo("/view/LoginPage.fxml");
    }

    private void navigateTo(String path) {
        try {
            ancDashBoardPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancDashBoardPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancDashBoardPage.heightProperty());

            ancDashBoardPage.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
            e.printStackTrace();
        }
    }

}
