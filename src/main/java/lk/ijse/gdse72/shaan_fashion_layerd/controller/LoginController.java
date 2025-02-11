package lk.ijse.gdse72.shaan_fashion_layerd.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse72.shaan_fashion_layerd.bo.BOFactory;
import lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.UserBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.UserDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl.UserDAOImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

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
    private TextField txtFullname;


    @FXML
    private TextField txtNewEnterPW;

    @FXML
    private TextField txtNewusername;

    @FXML
    private TextField txtConfirmPW;

    @FXML
    private Button btnOk;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnForgotOnAction(ActionEvent event) {
        txtFullname.setVisible(true);
        btnOk.setVisible(true);
    }

    @FXML
    void btnOkOnAction(ActionEvent event) throws Exception {

        String fullName = txtFullname.getText();

        boolean isUser = userBO.isValidUserFullName(fullName);
        if (isUser) {
            ownerLoginPage.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/UserForm.fxml"));
            ownerLoginPage.getChildren().add(load);
        } else {
            new Alert(Alert.AlertType.WARNING, "Invalid User!").show();
        }
    }

    @FXML
    void btnSigninOnAction(ActionEvent event) throws IOException {
        String username = txtFxUsername.getText();
        String password = txtFxPassword.getText();

        try {
            boolean isValid = userBO.isValidUser(username, password);

            if (isValid) {
                new Alert(Alert.AlertType.WARNING, "Login Success!");
                ownerLoginPage.getChildren().clear();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBoardForm.fxml"));
                AnchorPane load = loader.load();
                ownerLoginPage.getChildren().add(load);
            } else {
                new Alert(Alert.AlertType.WARNING, "Invalid Username or Password!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtFullname.setVisible(false);
        btnOk.setVisible(false);
    }
}
