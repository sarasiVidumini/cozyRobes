package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class mainPageController {

    public AnchorPane ancMainPage;


    public void btnGoLoginPageOnAction(ActionEvent actionEvent) throws IOException {
        ancMainPage.getChildren().clear();

        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));

        ancMainPage.getChildren().add(load);
    }
}
