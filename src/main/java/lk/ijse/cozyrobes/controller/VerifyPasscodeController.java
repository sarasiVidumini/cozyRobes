package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class VerifyPasscodeController {
    public AnchorPane ancVerifyPasscodePage;

    public void btnGoChangePasswordPageOnAction(ActionEvent actionEvent) throws IOException {
        ancVerifyPasscodePage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/ChangePassword.fxml"));
        ancVerifyPasscodePage.getChildren().add(load);
    }
}
