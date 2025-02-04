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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse72.shaan_fashion_layerd.bo.BOFactory;
import lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.UserBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.UserDTO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private AnchorPane OwnerRegisterPage;

    @FXML
    private JFXButton btnSignup;

    @FXML
    private JFXButton fxBtnSignin;

    @FXML
    private Label lblUserId;

    @FXML
    private JFXPasswordField txtConfirmPassword;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXPasswordField txtEnterPassword;

    @FXML
    private JFXTextField txtFullName;

    @FXML
    private JFXTextField txtUserName;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws Exception {
        String newUserId = userBO.generateNewUserId();
        lblUserId.setText(newUserId);

        txtFullName.setText("");
        txtUserName.setText("");
        txtEmail.setText("");
        txtEnterPassword.setText("");

        txtFullName.requestFocus();

    }

    @FXML
    void btnSignupOnAction(ActionEvent event) throws Exception {
        String userId = lblUserId.getText();
        String userFullName = txtFullName.getText();
        String username = txtUserName.getText();
        String userEmail = txtEmail.getText();
        String password = txtEnterPassword.getText();

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String usernamePattern = "^[A-Za-z0-9]{5,}$";
        String passwordPattern = "^[0-9]{4,8}$";

        boolean isValidName = userFullName.matches(namePattern);
        boolean isValidEmail = userEmail.matches(emailPattern);
        boolean isValidUsername = username.matches(usernamePattern);
        boolean isValidPassword = password.matches(passwordPattern);

        txtFullName.setStyle(txtFullName.getStyle()+"-fx-border-color: #7367F0;");
        txtUserName.setStyle(txtUserName.getStyle()+"-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle()+"-fx-border-color: #7367F0;");
        txtEnterPassword.setStyle(txtEnterPassword.getStyle()+"-fx-border-color: #7367F0;");

        if (!isValidName){
            txtFullName.setStyle(txtFullName.getStyle()+"-fx-border-color: red;");
        }
        if (!isValidUsername){
            txtUserName.setStyle(txtUserName.getStyle()+"-fx-border-color: red;");
        }
        if (!isValidEmail){
            txtEmail.setStyle(txtEmail.getStyle()+"-fx-border-color: red;");
        }
        if (!isValidPassword){
            txtEnterPassword.setStyle(txtEnterPassword.getStyle()+"-fx-border-color: red;");
        }

        if (isValidName && isValidUsername && isValidEmail && isValidPassword) {
           boolean isSaved = userBO.saveUser(
                   new UserDTO(
                           userId,
                           userFullName,
                           username,
                           userEmail,
                           password
                   ));
              if (isSaved){
                  new Alert(Alert.AlertType.CONFIRMATION, "User Registered Successfully").show();
                  refreshPage();
              }else {
                  new Alert(Alert.AlertType.ERROR, "User Registration Failed").show();
              }
        }

    }

    @FXML
    void fxBtnSigninOnAction(ActionEvent event) throws IOException {
        OwnerRegisterPage.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        OwnerRegisterPage.getChildren().add(load);
    }

    @FXML
    void navigateLogin(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/LoginForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.OwnerRegisterPage.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }
}
