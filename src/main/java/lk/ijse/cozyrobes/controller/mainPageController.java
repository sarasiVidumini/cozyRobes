package lk.ijse.cozyrobes.controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class mainPageController {

    public AnchorPane ancMainPage;
    public Label headLblCozyRobes;


    public void btnGoLoginPageOnAction(ActionEvent actionEvent) throws IOException {
        ancMainPage.getChildren().clear();

        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));

        ancMainPage.getChildren().add(load);
    }

    private void animateLabelBlink() {
        String loginText = headLblCozyRobes.getText();
        headLblCozyRobes.setText(loginText);

        FadeTransition blink = new FadeTransition(Duration.millis(1000), headLblCozyRobes);
        blink.setFromValue(1.0);
        blink.setToValue(0.0);
        blink.setCycleCount(6);
        blink.setAutoReverse(true);
        blink.play();
    }

    public  void initialize(){
        animateLabelBlink();
    }
}
