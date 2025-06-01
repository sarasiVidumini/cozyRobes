package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SignupPageController {
    public AnchorPane ancSignupPage;
    public TextField txtUserName;
    public TextField txtContact;
    public PasswordField txtPassword;

    public void GoToLoginPage(MouseEvent mouseEvent) throws IOException {
        ancSignupPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
        ancSignupPage.getChildren().add(load);
    }

    public void btnGoDashBoardPageOnAction(ActionEvent actionEvent) throws IOException {
        ancSignupPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));
        ancSignupPage.getChildren().add(load);
    }
}
