package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cozyrobes.dto.CustomerDto;
import lk.ijse.cozyrobes.model.CustomerModel;

public class CustomerPopupController {
    public AnchorPane ancCustomerViewContainer;
    public Label customerIdLabel;
    public TextField txtName;
    public TextField txtNumber;
    public TextField txtEmail;
    public Button btnSave;
    public Button btnReset;

    private  final String namePattern = "^[a-zA-Z0-9_\\-\\.]+$";
    private  final String numberPattern = "^[0-9]*$";
    private final  String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private final CustomerModel customerModel = new CustomerModel();

    public void saveBtnOnAction(ActionEvent actionEvent) {
        String customerId = customerIdLabel.getText();
        String name = txtName.getText();
        String number = txtNumber.getText();
        String email = txtEmail.getText();

        boolean isValidName = name.matches(namePattern);
        boolean isValidNumber = number.matches(numberPattern);
        boolean isValidEmail = email.matches(emailPattern);

        txtName.setStyle(txtName.getStyle() +";-fx-border-color: #7367F0;");
        txtNumber.setStyle(txtNumber.getStyle() +";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() +";-fx-border-color: #7367F0;");

         if (customerId.isEmpty() || name.isEmpty() || number.isEmpty() || email.isEmpty()) {
             new Alert(Alert.AlertType.ERROR,"please fill out all fields!").show();
             return;
         }

         if (isValidName) {
             txtName.setStyle(txtName.getStyle() +"-fx-border-color: #7367F0;");
             new Alert(Alert.AlertType.ERROR,"Invalid name format").show();
             return;
         }
         if (isValidNumber) {
             txtNumber.setStyle(txtNumber.getStyle() +"-fx-border-color: #7367F0;");
             new Alert(Alert.AlertType.CONFIRMATION,"Invalid number format").show();
             return;
         }
         if (isValidEmail) {
             txtEmail.setStyle(txtEmail.getStyle() +"-fx-border-color: #7367F0;");
             new Alert(Alert.AlertType.CONFIRMATION,"Invalid email format").show();
             return;
         }
        CustomerDto customerDto = new CustomerDto(
                customerId,
                name,
                number,
                email
        );

         if (isValidName && isValidNumber && isValidEmail) {
             try {
                 boolean isSaved = customerModel.saveCustomer(customerDto);
                 if (isSaved) {
                     new Alert(Alert.AlertType.INFORMATION,"Customer saved successfully!").show();
                     resetPage();
                 }else {
                     new Alert(Alert.AlertType.ERROR,"Failed to save customer").show();
                 }

             } catch (Exception e) {
                 e.printStackTrace();
                 new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
             }
         }
    }

    public void resetBtnOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    private void resetPage() {
        loadNextId();
        txtName.clear();
        txtNumber.clear();
        txtEmail.clear();

    }

    private void loadNextId() {
        try {
            String nextId = customerModel.getNextCustomerId();
            customerIdLabel.setText(nextId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
