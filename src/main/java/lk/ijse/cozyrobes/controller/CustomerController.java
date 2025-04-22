package lk.ijse.cozyrobes.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cozyrobes.dto.CustomerDto;
import lk.ijse.cozyrobes.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    public Label lblCustomerId;
    public TextField txtCustName;
    public TextField txtCustPhone;
    public TextField txtCustMail;

    public TableView tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colPhone;
    public TableColumn colMail;

    private CustomerModel customerModel = new CustomerModel();

    @Override
    public void initialize(URL location, ResourceBundle resource) {
    col
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }
}
