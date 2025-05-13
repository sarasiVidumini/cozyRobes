package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {
    public AnchorPane ancLoginPage;
    public TextField txtUserName;
    public TextField txtPassword;
    public TextField txtEmail;


    public void btnGoDashBoardPageOnAction(ActionEvent actionEvent) throws IOException {
        String username = "1";
        String password = "1";
        String email= "1";

        String inputUserName = txtUserName.getText();
        String inputPassword = txtPassword.getText();
        String inputUserEmail = txtEmail.getText();

        boolean userNameMatched = username.equals(inputUserName);
        boolean passwordMatched = password.equals(inputPassword);
        boolean userEmailMatched = inputUserEmail.equals(inputUserEmail);

        if (userNameMatched && passwordMatched && userEmailMatched) {

            ancLoginPage.getChildren().clear();

            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/DashBoardPage.fxml"));

            ancLoginPage.getChildren().add(load);
        } else {
            System.out.println(" wrong password...!");
        }

    }
    }

