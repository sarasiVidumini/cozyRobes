package lk.ijse.cozyrobes.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ChangePasswordController {
    public AnchorPane ancChangePasswordPage;

    public void goToLoginPage(MouseEvent mouseEvent) throws IOException {
        ancChangePasswordPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
        ancChangePasswordPage.getChildren().add(load);
    }
}
