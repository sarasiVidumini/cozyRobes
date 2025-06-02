package lk.ijse.cozyrobes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cozyrobes.db.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SigninController {

    @FXML
    public AnchorPane ancSigninPage;

    @FXML
    public TextField txtUserName;

    @FXML
    public PasswordField txtPassword;

    @FXML
    public void btnLoginOnAction(ActionEvent event) {
        String name = txtUserName.getText().trim();
        String password = txtPassword.getText().trim();

        if (name.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Empty Fields", "Please enter both username and password.");
            return;
        }

        if (validateLogin(name, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + name + "!");
            navigateTo("/view/DashBoardPage.fxml");
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect. Please check user name or password.");
        }
    }

    private boolean validateLogin(String name, String password) {
        String sql = "SELECT * FROM user WHERE name = ? AND password = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // login successful if a match is found

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred: " + e.getMessage());
            return false;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void goToLoginPage(MouseEvent mouseEvent) {
        navigateTo("/view/LoginPage.fxml");
    }

    @FXML
    public void goToForgotPasswordPage(MouseEvent mouseEvent) {
        navigateTo("/view/ForgotPassword.fxml");
    }

    private void navigateTo(String path) {
        try {
            ancSigninPage.getChildren().clear();
            AnchorPane pane = FXMLLoader.load(getClass().getResource(path));

            pane.prefWidthProperty().bind(ancSigninPage.widthProperty());
            pane.prefHeightProperty().bind(ancSigninPage.heightProperty());

            ancSigninPage.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Page not found!");
        }
    }

}
