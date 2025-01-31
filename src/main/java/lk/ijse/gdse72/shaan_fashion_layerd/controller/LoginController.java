package lk.ijse.gdse72.shaan_fashion_layerd.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginController {

    @FXML
    private JFXButton btnForgot;

    @FXML
    private JFXButton btnSignin;

    @FXML
    private JFXButton btnSignup;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblUsername;

    @FXML
    private AnchorPane ownerLoginPage;

    @FXML
    private JFXPasswordField txtFxPassword;

    @FXML
    private JFXTextField txtFxUsername;

    @FXML
    void btnForgotOnAction(ActionEvent event) {

    }

    @FXML
    void btnSigninOnAction(ActionEvent event) throws IOException {
        ownerLoginPage.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBoardForm.fxml"));
        AnchorPane load = loader.load();
        ownerLoginPage.getChildren().add(load);
    }

    @FXML
    void btnSignupOnAction(ActionEvent event) throws IOException {
        ownerLoginPage.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/RegisterForm.fxml"));
        ownerLoginPage.getChildren().add(load);
    }

    @FXML
    void navigateMain(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/MainForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.ownerLoginPage.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

}
