package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashBoardController {

    public AnchorPane ancDashBoardPage;

    public void btnGoCustomerPageOnAction(ActionEvent actionEvent) throws IOException {
        ancDashBoardPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/CustomerView.fxml"));
        ancDashBoardPage.getChildren().add(load);

    }
}
