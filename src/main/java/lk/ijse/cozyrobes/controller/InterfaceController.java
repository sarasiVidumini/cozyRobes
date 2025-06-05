package lk.ijse.cozyrobes.controller;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class InterfaceController {

    public Label lblMain;

    public void initialize() {
        animateLabelBlink();
    }

    private void animateLabelBlink() {
        String loginText =lblMain.getText();lblMain.setText(loginText);

        FadeTransition blink = new FadeTransition(Duration.millis(300),lblMain);
        blink.setFromValue(1.0);    // Fully visible
        blink.setToValue(0.0);      // Fully invisible
        blink.setCycleCount(6);     // Number of blinks (3 visible/invisible pairs)
        blink.setAutoReverse(true); // Return to visible after each blink

        blink.play();
    }

}
