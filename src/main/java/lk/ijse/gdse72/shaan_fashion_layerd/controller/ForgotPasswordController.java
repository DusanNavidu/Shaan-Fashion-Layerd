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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse72.shaan_fashion_layerd.bo.BOFactory;
import lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.UserBO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class ForgotPasswordController {

    @FXML
    private JFXButton btnNext;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblUsername;

    @FXML
    private JFXPasswordField txtNewConfirmPassword;

    @FXML
    private JFXPasswordField txtNewPassword;

    @FXML
    private JFXTextField txtNewUsername;

    @FXML
    private AnchorPane forgotPage;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnNextOnAction(ActionEvent event) {
        String username = txtNewUsername.getText();
        String password = txtNewPassword.getText();
        String confirmPassword = txtNewConfirmPassword.getText();

        if (password.equals(confirmPassword)) {
            try {
                boolean isUpdated = userBO.isUpdatePassword(username, password);
                if (isUpdated) {
                    new Alert(Alert.AlertType.WARNING, "Password Updated Successfully!");
                    forgotPage.getChildren().clear();
                    AnchorPane load = FXMLLoader.load(getClass().getResource("/view/ForgotPassword.fxml"));
                    forgotPage.getChildren().add(load);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Password Update Failed!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Password Mismatch!");
        }
    }

    @FXML
    void navigateLogin(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/MainForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.forgotPage.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }
}
