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
import lk.ijse.cozyrobes.dto.CustomerDto;
import lk.ijse.cozyrobes.dto.tm.CustomerTM;
import lk.ijse.cozyrobes.model.CustomerModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    public Label lblCustomerId;
    public TextField txtCustName;
    public TextField txtCustPhone;
    public TextField txtCustEmail;

    public TableView<CustomerTM> tblCustomer;
    public TableColumn<CustomerTM, String> colId;
    public TableColumn<CustomerTM, String> colName;
    public TableColumn<CustomerTM, String> colPhone;
    public TableColumn<CustomerTM, String> colMail;

    public AnchorPane ancCustomerPage;

    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;

    private final CustomerModel customerModel = new CustomerModel();
    public TextField txtSearch;


    @Override
    public void initialize(URL location, ResourceBundle resource) {
        colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("email"));

        try {
            loadTableData();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to initialize data!").show();
        }

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void loadTableData() throws Exception {
        var customerList = customerModel.getAllCustomer();

        if (customerList != null) {
            tblCustomer.setItems(FXCollections.observableArrayList(
                    customerList.stream()
                            .map(customerDto -> new CustomerTM(
                                    customerDto.getCustomerId(),
                                    customerDto.getName(),
                                    customerDto.getPhone(),
                                    customerDto.getEmail()
                            )).toList()
            ));
        }
    }

    private void loadNextId() throws Exception {
        String nextId = customerModel.getNextCustomerId();
        lblCustomerId.setText(nextId);
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            txtCustName.clear();
            txtCustPhone.clear();
            txtCustEmail.clear();
            tblCustomer.getSelectionModel().clearSelection();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong while resetting!").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String customerId = lblCustomerId.getText();
        String name = txtCustName.getText();
        String phone = txtCustPhone.getText();
        String email = txtCustEmail.getText();

        if (customerId.isEmpty() || name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Empty fields, please fill all the fields").show();
            return;
        }

        if (!email.matches("^\\S+@\\S+\\.\\S+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid email address").show();
            return;
        }

        if (!phone.matches("^\\d{10}$")) {
            new Alert(Alert.AlertType.ERROR, "Phone number must be 10 digits").show();
            return;
        }

        CustomerDto customerDto = new CustomerDto(customerId, name, phone, email);

        try {
            boolean isSaved = customerModel.saveCustomer(customerDto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Saved successfully!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save customer.").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setTitle("Confirmation");

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String customerId = lblCustomerId.getText();
            try {
                boolean isDeleted = customerModel.deleteCustomer(customerId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Deleted successfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete customer").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to delete customer").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String customerId = lblCustomerId.getText();
        String name = txtCustName.getText();
        String phone = txtCustPhone.getText();
        String email = txtCustEmail.getText();

        if (customerId.isEmpty() || name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Empty fields, please fill all the fields").show();
            return;
        }

        if (!email.matches("^\\S+@\\S+\\.\\S+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid email address").show();
            return;
        }

        if (!phone.matches("^\\d{10}$")) {
            new Alert(Alert.AlertType.ERROR, "Phone number must be 10 digits").show();
            return;
        }

        CustomerDto customerDto = new CustomerDto(customerId, name, phone, email);

        try {
            boolean isUpdated = customerModel.updateCustomer(customerDto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Updated successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update customer").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to update customer").show();
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void onClickedTable(MouseEvent mouseEvent) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblCustomerId.setText(selectedItem.getCustomerId());
            txtCustName.setText(selectedItem.getName());
            txtCustPhone.setText(selectedItem.getPhone());
            txtCustEmail.setText(selectedItem.getEmail());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void goToDashboard(MouseEvent mouseEvent) throws IOException {
        ancCustomerPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancCustomerPage.getChildren().add(load);
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
                ArrayList<CustomerDto> customerList = customerModel.searchCustomer(search);
                tblCustomer.setItems(FXCollections.observableArrayList(
                        customerList.stream()
                                .map(customerDto -> new CustomerTM(
                                        customerDto.getCustomerId(),
                                        customerDto.getName(),
                                        customerDto.getPhone(),
                                        customerDto.getEmail()
                                )).toList()
                ));
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search").show();
            }
        }
    }
}
