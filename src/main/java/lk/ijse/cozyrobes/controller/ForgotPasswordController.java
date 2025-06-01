package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ForgotPasswordController {
    public AnchorPane ancForgotPasswordPage;

    public void btnGoVerifyPasscodePageOnAction(ActionEvent actionEvent) throws IOException {
        ancForgotPasswordPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/VerifyPasscode.fxml"));
        ancForgotPasswordPage.getChildren().add(load);

    }

    public void goToSignupPage(MouseEvent mouseEvent) throws IOException {
       ancForgotPasswordPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/SignUp.fxml"));
        ancForgotPasswordPage.getChildren().add(load);
    }

    public void btnGoLoginPageOnAction(ActionEvent actionEvent) throws IOException {
        ancForgotPasswordPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
        ancForgotPasswordPage.getChildren().add(load);

    }

}
