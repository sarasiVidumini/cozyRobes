package lk.ijse.cozyrobes.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cozyrobes.dto.CustomerDto;
import lk.ijse.cozyrobes.dto.tm.CustomerTM;
import lk.ijse.cozyrobes.model.CustomerModel;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    public Label lblCustomerId;
    public TextField txtCustName;
    public TextField txtCustPhone;
    public TextField txtCustEmail;

    public TableView<CustomerTM> tblCustomer;
    public TableColumn<CustomerTM , String> colId;
    public TableColumn<CustomerTM , String> colName;
    public TableColumn<CustomerTM , String> colPhone;
    public TableColumn<CustomerTM , String>colMail;

    public AnchorPane ancCustomerPage;

    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;

    private CustomerModel customerModel = new CustomerModel();

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        colId.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("Email"));

        try {
           loadTableData();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong...").show();
        }
    }

    private void loadTableData()throws Exception{
        tblCustomer.setItems(FXCollections.observableArrayList(
                customerModel.getAllCustomer().stream()
                        .map(customerDto -> new CustomerTM(
                                customerDto.getCustomer_id(),
                                customerDto.getName(),
                                customerDto.getPhone(),
                                customerDto.getEmail()
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

            txtCustName.setText("");
            txtCustPhone.setText("");
            txtCustEmail.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }
    public void btnSaveOnAction(ActionEvent actionEvent) {
    String customerId = lblCustomerId.getText();
    String name = txtCustName.getText();
    String phone = txtCustPhone.getText();
    String email = txtCustEmail.getText();

    if(customerId.isEmpty() ||name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
        new Alert(Alert.AlertType.ERROR, "Empty fields , please fill all the fields").show();
        return;
    }

    CustomerDto customerDto = new CustomerDto(
            customerId,
            name,
            phone,
            email
    );

    try {
        boolean isSaved = customerModel.saveCustomer(customerDto);

        if (isSaved) {
            resetPage();
            new Alert(Alert.AlertType.INFORMATION, "Saved successfully!").show();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
    }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure ?",
                ButtonType.YES,
                ButtonType.NO
        );

        alert.setTitle("Confirmation");

        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES) {
            String customerId = lblCustomerId.getText();
            try {
                boolean isDeleted = customerModel.deleteCustomer(customerId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Deleted successfully!").show();

                }else{
                    new Alert(Alert.AlertType.ERROR, "Fail to delete customer").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String customer_id = lblCustomerId.getText();
        String name = txtCustName.getText();
        String phone = txtCustPhone.getText();
        String email = txtCustEmail.getText();

        if (customer_id.isEmpty() ||name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Empty fields , please fill all the fields").show();
            return;
        }
        CustomerDto customerDto = new CustomerDto(
                customer_id,
                name,
                phone,
                email
        );

        try {
            boolean isUpdated = customerModel.updateCustomer(customerDto);

            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Updated successfully!").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Fail to update customer").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to update customer.").show();
        }
    }

    private void loadNextId()throws Exception{
        String nextId = customerModel.getNextCustomerId();
        lblCustomerId.setText(nextId);
    }



    public void btnGoDashBoardPageOnAction(ActionEvent actionEvent) throws IOException {
        ancCustomerPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancCustomerPage.getChildren().add(load);
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void onClickTable(javafx.scene.input.MouseEvent mouseEvent) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblCustomerId.setText(selectedItem.getCustomer_id());
            txtCustName.setText(selectedItem.getName());
            txtCustPhone.setText(selectedItem.getPhone());
            txtCustEmail.setText(selectedItem.getEmail());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
}
