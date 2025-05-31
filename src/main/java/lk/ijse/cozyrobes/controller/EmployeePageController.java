package lk.ijse.cozyrobes.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import lk.ijse.cozyrobes.dto.EmployeeDto;
import lk.ijse.cozyrobes.dto.tm.EmployeeTM;
import lk.ijse.cozyrobes.model.EmployeeModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeePageController implements Initializable {
    public AnchorPane ancEmployeePage;
    private final EmployeeModel employeeModel = new EmployeeModel();

    public TableView<EmployeeTM> tblEmployee;
    public TableColumn<EmployeeTM, String> colEmployeeId;
    public TableColumn<EmployeeTM, String> colUserId;
    public TableColumn<EmployeeTM, String> colEmployeeName;
    public TableColumn<EmployeeTM, String> colEmployeeRole;
    public TableColumn<EmployeeTM, Double> colEmployeeSalary;
    public Label lblEmployeeId;
    public ComboBox<String> cmbUserPlatForm;
    public TextField txtEmployeeName;
    public TextField txtEmployeeRole;
    public TextField txtEmployeeSalary;

    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;
    public TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmployeeRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colEmployeeSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        cmbUserPlatForm.setItems(FXCollections.observableArrayList("U001" , "U002"));
        try {
            loadTableData();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load employee data").show();
        }
    }

    private void loadNextId() throws SQLException {
        String nextId = employeeModel.getNextEmployeeId();
        lblEmployeeId.setText(nextId);
    }
    
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this employee?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setTitle("Confirmation");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            String employeeId = lblEmployeeId.getText();
            try {
                boolean isDeleted = employeeModel.deleteEmployee(employeeId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee deleted successfully").show();
                    resetPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete Employee").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to delete employee").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String employeeId = lblEmployeeId.getText();
        String userId = (String) cmbUserPlatForm.getValue();
        String name = txtEmployeeName.getText();
        String role = txtEmployeeRole.getText();
        double salary = Double.parseDouble(txtEmployeeSalary.getText());

        if (employeeId.isEmpty() || userId == null || name.isEmpty() || role.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Empty fields, please fill all the fields").show();
            return;
        }

        EmployeeDto employeeDto = new EmployeeDto(employeeId, userId, name, role, salary);

        try {
            boolean isUpdated = employeeModel.updateEmployee(employeeDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Employee updated successfully").show();
                resetPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Employee").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to update employee").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String employeeId = lblEmployeeId.getText();
        String userId = (String) cmbUserPlatForm.getValue();
        String name = txtEmployeeName.getText();
        String role = txtEmployeeRole.getText();
        double salary = Double.parseDouble(txtEmployeeSalary.getText());

        if (employeeId.isEmpty() || userId == null || name.isEmpty() || role.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Empty fields, please fill all the fields").show();
            return;
        }

        EmployeeDto employeeDto = new EmployeeDto(employeeId, userId, name, role, salary);

        try {
            boolean isSaved = employeeModel.saveEmployee(employeeDto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Employee saved successfully").show();
                resetPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save Employee").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save Employee").show();
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblEmployee.setItems(FXCollections.observableArrayList(
                employeeModel.getAllEmployee()
                        .stream()
                        .map(employeeDto -> new EmployeeTM(
                                employeeDto.getEmployeeId(),
                                employeeDto.getUserId(),
                                employeeDto.getName(),
                                employeeDto.getRole(),
                                employeeDto.getSalary()
                        ))
                        .toList()
        ));
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();
            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
            cmbUserPlatForm.setValue(null);
            txtEmployeeName.clear();
            txtEmployeeRole.clear();
            txtEmployeeSalary.clear();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to reset page").show();
        }
    }

    public void goToDashboard(MouseEvent mouseEvent) throws IOException {
        ancEmployeePage.getChildren().clear();
        Parent load  = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancEmployeePage.getChildren().add(load);
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
                ArrayList<EmployeeDto> employeeList = employeeModel.searchEmployee(search);
                tblEmployee.setItems(FXCollections.observableArrayList(
                        employeeList.stream()
                                .map(employeeDto -> new EmployeeTM(
                                        employeeDto.getEmployeeId(),
                                        employeeDto.getUserId(),
                                        employeeDto.getName(),
                                        employeeDto.getRole(),
                                        employeeDto.getSalary()
                                )).toList()
                ));
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search").show();
            }
        }
    }

    public void OnClickedTable(MouseEvent mouseEvent) {
        EmployeeTM selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblEmployeeId.setText(selectedItem.getEmployeeId());
            cmbUserPlatForm.setValue(selectedItem.getUserId());
            txtEmployeeName.setText(selectedItem.getName());
            txtEmployeeRole.setText(selectedItem.getRole());
            txtEmployeeSalary.setText(String.valueOf(selectedItem.getSalary()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
