package lk.ijse.cozyrobes.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    public AnchorPane ancLoginPage;
    public ComboBox<String> cmbLanguagePlatform;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbLanguagePlatform.setItems(FXCollections.observableArrayList(" " , "English(UK)" , "සිංහල"));
    }

    public void goToMainPage(MouseEvent mouseEvent) throws IOException {
        ancLoginPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/MainPage.fxml"));
        ancLoginPage.getChildren().add(load);
    }

    public void btnGoSignupPageOnAction(ActionEvent actionEvent) throws IOException {
        ancLoginPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/SignUp.fxml"));
        ancLoginPage.getChildren().add(load);
    }

    public void btnGoSigninPageOnAction(ActionEvent actionEvent) {
    }


}
