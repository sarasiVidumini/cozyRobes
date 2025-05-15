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
import lk.ijse.cozyrobes.dto.SupplierDto;
import lk.ijse.cozyrobes.dto.tm.SupplierTM;
import lk.ijse.cozyrobes.dto.tm.UserTM;
import lk.ijse.cozyrobes.model.SupplierModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierPageController implements Initializable {

    public AnchorPane ancSupplierPage;

    public Label lblSupplierId;
    public TextField txtSPName;
    public TextField txtSPAddress;
    public TextField txtSPContact;

    public TableView<SupplierTM> tblSupplier;
    public TableColumn<SupplierTM , String>colSupplierId;
    public TableColumn<SupplierTM , String> colSupplierName;
    public TableColumn<SupplierTM , String> colSupplierAdd;
    public TableColumn<SupplierTM , String> colSupplierCnt;

    private final SupplierModel supplierModel = new SupplierModel();

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public Button btnBack;


    public void btnGoDashBoardPageOnAction(ActionEvent actionEvent) throws IOException {
        ancSupplierPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancSupplierPage.getChildren().add(load);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSupplierAdd.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSupplierCnt.setCellValueFactory(new PropertyValueFactory<>("contact"));

        try {
                loadTableData();
                loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR , "Something went wrong").show();
        }
    }

    private void loadTableData() throws SQLException {
//        ArrayList<SupplierDto> supplierDtoArrayList = supplierModel.getAllSuppliers();
//        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();
//
//         for(SupplierDto supplierDto : supplierDtoArrayList) {
//             SupplierTM supplierTM = new SupplierTM(
//                     supplierDto.getSupplierId(),
//                     supplierDto.getName(),
//                     supplierDto.getAddress(),
//                     supplierDto.getContact()
//             );
//             supplierTMS.add(supplierTM);
//        }
//         tblSupplier.setItems(supplierTMS);
        tblSupplier.setItems(FXCollections.observableArrayList(
                supplierModel.getAllSuppliers().stream()
                        .map(supplierDto -> new SupplierTM(
                                supplierDto.getSupplier_id(),
                                supplierDto.getName(),
                                supplierDto.getAddress(),
                                supplierDto.getContact()
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

            txtSPName.setText("");
            txtSPAddress.setText("");
            txtSPContact.setText("");

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR , "Something went wrong").show();
        }
    }
    public void btnSaveOnAction(ActionEvent actionEvent) {
            String supplier_id = lblSupplierId.getText();
            String name = txtSPName.getText();
            String address = txtSPAddress.getText();
            String contact = txtSPContact.getText();

            SupplierDto supplierDto = new SupplierDto(
                    supplier_id,
                    name,
                    address,
                    contact
            );

            try {
                boolean isSaved = supplierModel.saveSupplier(supplierDto);

                if (isSaved) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION , "Supplier Saved successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR , "Fail to save supplier").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR , "Fail to save supplier").show();
            }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
            String supplier_id = lblSupplierId.getText();
            String name = txtSPName.getText();
            String address = txtSPAddress.getText();
            String contact = txtSPContact.getText();

            SupplierDto supplierDto = new SupplierDto(
                    supplier_id,
                    name,
                    address,
                    contact
            );

            try {
                boolean isUpdated = supplierModel.updateSupplier(supplierDto);

                if (isUpdated) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION , "Supplier Updated successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR , "Fail to update supplier").show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR , "Fail to update Supplier").show();
            }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Are you sure ?" ,
                    ButtonType.YES,
                    ButtonType.NO
            );


        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String supplierId = lblSupplierId.getText();

            try {
                boolean isDeleted = supplierModel.deleteSupplier(supplierId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Supplier Deleted successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete supplier").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to delete supplier").show();
            }
        }
    }

    private void loadNextId() throws Exception {
        try {
            String nextId = "S001"; // Dummy logic; replace with DB fetch if needed
            lblSupplierId.setText(nextId);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to generate next Supplier ID").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        SupplierTM selectedItem =tblSupplier.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblSupplierId.setText(selectedItem.getSupplier_id());
            txtSPName.setText(selectedItem.getName());
            txtSPAddress.setText(selectedItem.getAddress());
            txtSPContact.setText(selectedItem.getContact());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }
}
