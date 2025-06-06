package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cozyrobes.dto.UserDto;
import lk.ijse.cozyrobes.model.UserModel;
import lk.ijse.cozyrobes.util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;

public class SignupPageController {
    private final UserModel userModel = new UserModel();
    public AnchorPane ancSignupPage;
    public TextField txtUserName;
    public TextField txtContact;
    public PasswordField txtPassword;

    private final String userNamePattern = "^[a-zA-Z0-9_-]{3,16}$";

    public PasswordField txtConfirmPassword;
    public Button btnGotIt;
    public TextField txtRole;
    private String passwordPattern = "^[A-Za-z0-9+_.-]+$";
    private String contactPattern = "^[A-Za-z0-9+_.-]+$";

    public void initialize() {
        txtRole.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        txtUserName.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        txtContact.textProperty().addListener((observableValue, oldValue, newValue) -> validateFields());
        txtConfirmPassword.textProperty().addListener((observableValue, oldValue, newValue) ->validateFields() );

    }

    private void validateFields() {
        boolean IsValidUserName = txtUserName.getText().matches(userNamePattern);
        boolean IsValidContact = txtContact.getText().matches(contactPattern);
        boolean IsValidPassword = txtPassword.getText().matches(passwordPattern);
        boolean IsValidConfirmPassword = txtConfirmPassword.getText().equals(txtPassword.getText());


        txtRole.setStyle("-fx-border-color: #7367F0; -fx-border-radius: 20px; -fx-background-radius: 20px;");
        txtUserName.setStyle("-fx-border-color: #7367F0; -fx-border-radius: 20px; -fx-background-radius: 20px;");
        txtContact.setStyle("-fx-border-color: #7367F0; -fx-border-radius: 20px; -fx-background-radius: 20px;");
        txtPassword.setStyle("-fx-border-color: #7367F0; -fx-border-radius: 20px; -fx-background-radius: 20px;");
        txtConfirmPassword.setStyle("-fx-border-color: #7367F0; -fx-border-radius: 20px; -fx-background-radius: 20px;");

        if (!IsValidUserName) txtUserName.setStyle("-fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 20px;");
        if (!IsValidContact) txtContact.setStyle("-fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 20px;");
        if (!IsValidPassword) txtPassword.setStyle("-fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 20px;");
        if (!IsValidConfirmPassword) txtConfirmPassword.setStyle("-fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 20px;");

        btnGotIt.setDisable(!(IsValidUserName  && IsValidContact &&  IsValidPassword && IsValidConfirmPassword));

    }
    
    public void GoToLoginPage(MouseEvent mouseEvent) throws IOException {
        ancSignupPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
        ancSignupPage.getChildren().add(load);
    }

    public void btnGoDashBoardPageOnAction(ActionEvent actionEvent) throws IOException {
        String inputRole = txtRole.getText();
        String inputUsername = txtUserName.getText();
        String inputContact = txtContact.getText();
        String inputPassword = txtPassword.getText();
        String inputConfirmPassword = txtConfirmPassword.getText();

        if (inputRole.isEmpty()|| inputUsername.isEmpty() ||  inputContact.isEmpty()  || inputPassword.isEmpty() || inputConfirmPassword.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").show();
            return;
        }

        if (!inputContact.matches(contactPattern)) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid contact number").show();
            return;
        }

        if (!inputPassword.matches(passwordPattern)) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid password").show();
            return;
        }

        if (!inputConfirmPassword.matches(inputConfirmPassword)) {
            new Alert(Alert.AlertType.ERROR, "Please reenter a valid password").show();
            return;
        }

        if (!inputPassword.equals(inputConfirmPassword)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
            return;
        }

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM user WHERE  name = ?  OR password = ?" , inputUsername , inputPassword);

            if (resultSet.next()){
                new Alert(Alert.AlertType.ERROR, "User already exists").show();
                return;
            }
            String userId = userModel.getNextUserId();

            boolean isSaved = userModel.saveUser(new UserDto(
                    userId,
                    inputRole,
                    inputUsername,
                    inputContact,
                    inputPassword

            ));

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "User has been created").show();

            }else {
                new Alert(Alert.AlertType.ERROR, "User has not been saved").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Sign Up Faild").show();
        }

        ancSignupPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancSignupPage.getChildren().add(load);
    }
}
