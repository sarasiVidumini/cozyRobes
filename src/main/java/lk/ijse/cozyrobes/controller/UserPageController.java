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
import lk.ijse.cozyrobes.dto.UserDto;
import lk.ijse.cozyrobes.dto.tm.UserTM;
import lk.ijse.cozyrobes.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserPageController implements Initializable {
    public AnchorPane ancUserPage;
    public Label lblUserId;
    public TextField txtRole;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtPassword;

    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Button btnReset;
    public TextField txtSearch;

private  final UserModel userModel = new UserModel();

    public TableView<UserTM> tblUser;
    public TableColumn<UserTM , String> colUserId;
    public TableColumn<UserTM , String> colUserRole;
    public TableColumn<UserTM , String> colUserName;
    public TableColumn<UserTM , String> colUserContact;
    public TableColumn<UserTM , String> colUserPassword;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUserContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colUserPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

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
                              userDto.getUserId(),
                              userDto.getRole(),
                              userDto.getName(),
                              userDto.getContact(),
                              userDto.getPassword()
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
            String user_id = lblUserId.getText();
            String role = txtRole.getText();
            String name = txtName.getText();
            String contact = txtContact.getText();
            String password = txtPassword.getText();


        if (user_id.isEmpty() || role.isEmpty() ||  name.isEmpty() || contact.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Empty fields, please fill all the fields").show();
            return;
        }


        UserDto userDto = new UserDto(user_id , role ,name , contact,password);

        try {
            boolean isSaved = userModel.saveUser(userDto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "User saved successfully").show();
                resetPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save User").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save User").show();
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
        String user_id = lblUserId.getText();
        String role = txtRole.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String password = txtPassword.getText();

        if (user_id.isEmpty() ||role.isEmpty() || name.isEmpty() || contact.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Empty fields , please fill all the fields").show();
            return;
        }
        UserDto userDto = new UserDto(
                user_id,
                role,
                name,
                contact,
                password
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

    public void onClickedTable(MouseEvent mouseEvent) {
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

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void goToDashboard(MouseEvent mouseEvent) throws IOException {
        ancUserPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancUserPage.getChildren().add(load);
    }

    public void search(KeyEvent keyEvent) {
        String search = txtSearch.getText();
        if (search.isEmpty()) {
            try {
                loadTableData();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
            }
        }else {
            try {
                ArrayList<UserDto> userList = userModel.searchUser(search);
                tblUser.setItems(FXCollections.observableArrayList(
                        userList.stream()
                                .map(userDto -> new UserTM(
                                        userDto.getUserId(),
                                        userDto.getRole(),
                                        userDto.getName(),
                                        userDto.getContact(),
                                        userDto.getPassword()
                                )).toList()
                ));
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
            }
        }
    }
}
