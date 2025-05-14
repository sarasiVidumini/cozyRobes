package lk.ijse.cozyrobes.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cozyrobes.dto.CustomerDto;
import lk.ijse.cozyrobes.dto.UserDto;
import lk.ijse.cozyrobes.dto.tm.CustomerTM;
import lk.ijse.cozyrobes.dto.tm.UserTM;
import lk.ijse.cozyrobes.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserPageController implements Initializable {
    public AnchorPane ancUserPage;
    public Label lblUserId;
    public TextField txtRole;
    public TextField txtName;
    public TextField txtContact;

    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;

private  final UserModel userModel = new UserModel();

    public TableView<UserTM> tblUser;
    public TableColumn<UserTM , String> colUserId;
    public TableColumn<UserTM , String> colUserRole;
    public TableColumn<UserTM , String> colUserName;
    public TableColumn<UserTM , String> colUserContact;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUserContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        try {
            loadTableData();
            loadNextId();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    public void loadTableData() throws Exception {
      tblUser.setItems(FXCollections.observableArrayList(
              userModel.getAllUser().stream()
                      .map(userDto -> new UserTM(
                              userDto.getUser_id(),
                              userDto.getRole(),
                              userDto.getName(),
                              userDto.getContact()
                      )).toList()
      ));
    }

    private void resetPage() {
        try {
          loadTableData();
           loadNextId();

            btnSave.setDisable(false);

            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            txtRole.setText("");
            txtName.setText("");
            txtContact.setText("");

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }
    public void btnSaveOnAction(ActionEvent actionEvent) {
            String userId = lblUserId.getText();
            String role = txtRole.getText();
            String name = txtName.getText();
            String contact = txtContact.getText();

            UserDto userDto = new UserDto(
                    userId,
                    role,
                    name,
                    contact
            );
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

            String userId = lblUserId.getText();
            try {
                boolean isDeleted = userModel.deleteUser(userId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "User deleted successfully").show();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Fail to delete user.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete user").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String userId = lblUserId.getText();
        String role = txtRole.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();

        if (userId.isEmpty() ||role.isEmpty() || name.isEmpty() || contact.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Empty fields , please fill all the fields").show();
            return;
        }
        UserDto userDto = new UserDto(
                userId,
                role,
                name,
                contact
        );

        try {
            boolean isUpdated = userModel.updateUser( userDto);

            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Updated successfully!").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Fail to update user").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to update user.").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = userModel.getNextUserId();
        lblUserId.setText(nextId);
    }

    public void onClickTable(MouseEvent mouseEvent) {
        UserTM selectedItem = (UserTM) tblUser.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblUserId.setText(selectedItem.getUserId());
            txtRole.setText(selectedItem.getRole());
            txtName.setText(selectedItem.getName());
            txtContact.setText(selectedItem.getContact());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
    public void btnGoDashBoardPageOnAction(ActionEvent actionEvent) throws IOException {
        ancUserPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancUserPage.getChildren().add(load);
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
    }
}
