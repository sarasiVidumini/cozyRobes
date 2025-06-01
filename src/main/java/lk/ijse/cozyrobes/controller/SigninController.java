package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SigninController {
    public AnchorPane ancSigninPage;

    public void GoToLoginPage(MouseEvent mouseEvent) throws IOException {
      navigateTo("/view/LoginPage.fxml");
    }

    public void btnGoDashBoardPageOnAction(ActionEvent actionEvent) throws IOException {
       navigateTo("/view/DashBoardPage.fxml");
    }

    public void goToForgotPasswordPage(MouseEvent mouseEvent) {
      navigateTo("/view/ForgotPassword.fxml");

    }

    private void navigateTo(String path){
        try {
            ancSigninPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancSigninPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancSigninPage.heightProperty());

            ancSigninPage.getChildren().add(anchorPane);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Page not found !").show();
        }
    }
}
