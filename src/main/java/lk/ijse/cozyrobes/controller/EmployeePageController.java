package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EmployeePageController {
    public AnchorPane ancEmployeePage;
    public TableView tblEmployee;
    public TableColumn colEmployeeId;
    public TableColumn colUserId;
    public TableColumn colEmployeeName;
    public TableColumn colEmployeeRole;
    public TableColumn colEmployeeSalary;
    public Label lblEmployeeId;
    public TextField txtUserId;
    public TextField txtEmployeeName;
    public TextField txtEmployeeRole;
    public TextField txtEmployeeSalary;
    public Button btnBack;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;

    public void btnGoDashBoardPageOnAction(ActionEvent actionEvent) throws IOException {
        ancEmployeePage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancEmployeePage.getChildren().add(load);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
    }
}
