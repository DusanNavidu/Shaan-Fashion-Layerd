package lk.ijse.gdse72.shaan_fashion_layerd.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse72.shaan_fashion_layerd.bo.BOFactory;
import lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.UserBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.UserDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.view.tdm.UserTM;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class UserController implements Initializable {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnOTP;

    @FXML
    private JFXButton btnEMailSendToUser;

    @FXML
    private JFXButton btnGenerateReport;

    @FXML
    private JFXButton btnResert;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private Label c;

    @FXML
    private TableColumn<UserTM,String> colEmail;

    @FXML
    private TableColumn<UserTM,String> colPassword;

    @FXML
    private TableColumn<UserTM,String> colUserFullName;

    @FXML
    private TableColumn<UserTM,String> colUserId;

    @FXML
    private TableColumn<UserTM,String > colUsername;

    @FXML
    private Label lblNotify;

    @FXML
    private Label lblUserId;

    @FXML
    private TableView<UserTM> tblUser;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtFullname;

    @FXML
    private JFXPasswordField pwdCinfirmPW;

    @FXML
    private JFXPasswordField pwdEnterPW;

    @FXML
    private JFXPasswordField pwdUsername;

    @Setter
    private String Email;

    @Setter
    private UserController userController;


    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserFullName.setCellValueFactory(new PropertyValueFactory<>("userFullName"));

        colUsername.setCellValueFactory(cellData -> {
            String maskedUsername = "*".repeat(cellData.getValue().getUsername().length());
            return new SimpleStringProperty(maskedUsername);
        });

        colEmail.setCellValueFactory(new PropertyValueFactory<>("userEmail"));

        colPassword.setCellValueFactory(cellData -> {
            String maskedPassword = "*".repeat(cellData.getValue().getPassword().length());
            return new SimpleStringProperty(maskedPassword);
        });

        try {
            refreshPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws Exception {
        refreshTable();

        String newUserId = userBO.generateNewUserId();
        lblUserId.setText(newUserId);

        txtFullname.setText("");
        pwdUsername.setText("");
        txtEmail.setText("");
        pwdEnterPW.setText("");
        pwdCinfirmPW.setText("");

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnGenerateReport.setDisable(true);
        btnEMailSendToUser.setDisable(true);
    }

    private void refreshTable() {
        tblUser.getItems().clear();
        try {
            ArrayList<UserDTO> allUsers = userBO.getAllUsers();
            ObservableList<UserTM> users = tblUser.getItems();

            for (UserDTO user : allUsers) {
                users.add(new UserTM(
                        user.getUserId(),
                        user.getUserFullName(),
                        user.getUsername(),
                        user.getUserEmail(),
                        user.getPassword()
                ));
            }
            tblUser.setItems(users);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws Exception {
        String userId = lblUserId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this user?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = userBO.deleteUser(userId);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "User Deleted Successfully").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "User Delete Failed").show();
            }
        }
    }

    @FXML
    void btnEMailSendToUserOnAction(ActionEvent event) {
        UserTM selectedItem = tblUser.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a user to send an email").show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MailSendForm.fxml"));
            Parent load = loader.load();

            MailSendController sendMailController = loader.getController();

            String email = selectedItem.getUserEmail();
            sendMailController.setEmail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send email");

            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnUpdate.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
            e.printStackTrace();
        }
    }

    @FXML
    void btnGenerateReportOnAction(ActionEvent event) {

    }

    @FXML
    void btnResertOnAction(ActionEvent event) throws Exception {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
        String userId = lblUserId.getText();
        String userFullName = txtFullname.getText();
        String username = pwdUsername.getText();
        String userEmail = txtEmail.getText();
        String enterPw = pwdEnterPW.getText();
        String password = pwdCinfirmPW.getText();

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String usernamePattern = "^[A-Za-z0-9]{5,}$";
        String passwordPattern = "^[0-9]{4,8}$";

        boolean isValidName = userFullName.matches(namePattern);
        boolean isValidEmail = userEmail.matches(emailPattern);
        boolean isValidUsername = username.matches(usernamePattern);
        boolean isValidPassword2 = enterPw.matches(passwordPattern);
        boolean isValidPassword1 = password.matches(passwordPattern);

        txtFullname.setStyle(txtFullname.getStyle()+"-fx-border-color: #7367F0;");
        pwdUsername.setStyle(pwdUsername.getStyle()+"-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle()+"-fx-border-color: #7367F0;");
        pwdEnterPW.setStyle(pwdEnterPW.getStyle()+"-fx-border-color: #7367F0;");
        pwdCinfirmPW.setStyle(pwdCinfirmPW.getStyle()+"-fx-border-color: #7367F0;");


        if (!isValidName){
            txtFullname.setStyle(txtFullname.getStyle()+"-fx-border-color: red;");
        }
        if (!isValidUsername){
            pwdUsername.setStyle(pwdUsername.getStyle()+"-fx-border-color: red;");
        }
        if (!isValidEmail){
            txtEmail.setStyle(txtEmail.getStyle()+"-fx-border-color: red;");
        }
        if (!isValidPassword1){
            pwdEnterPW.setStyle(pwdEnterPW.getStyle()+"-fx-border-color: red;");
        }
        if (!isValidPassword2){
            pwdCinfirmPW.setStyle(pwdCinfirmPW.getStyle()+"-fx-border-color: red;");
        }

        if (!password.equals(enterPw)){
            pwdEnterPW.setStyle(pwdEnterPW.getStyle()+"-fx-border-color: red;");
            pwdCinfirmPW.setStyle(pwdCinfirmPW.getStyle()+"-fx-border-color: red;");
        }

        if (isValidName && isValidUsername && isValidEmail && isValidPassword2 && isValidPassword1 && password.equals(enterPw)) {
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
    void btnUpdateOnAction(ActionEvent event) throws Exception {
        String userId = lblUserId.getText();
        String userFullName = txtFullname.getText();
        String username = pwdUsername.getText();
        String userEmail = txtEmail.getText();
        String enterPw = pwdEnterPW.getText();
        String password = pwdCinfirmPW.getText();

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String usernamePattern = "^[A-Za-z0-9]{5,}$";
        String passwordPattern = "^[0-9]{4,8}$";

        boolean isValidName = userFullName.matches(namePattern);
        boolean isValidEmail = userEmail.matches(emailPattern);
        boolean isValidUsername = username.matches(usernamePattern);
        boolean isValidPassword2 = enterPw.matches(passwordPattern);
        boolean isValidPassword1 = password.matches(passwordPattern);

        txtFullname.setStyle(txtFullname.getStyle()+"-fx-border-color: #7367F0;");
        pwdUsername.setStyle(pwdUsername.getStyle()+"-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle()+"-fx-border-color: #7367F0;");
        pwdEnterPW.setStyle(pwdEnterPW.getStyle()+"-fx-border-color: #7367F0;");
        pwdCinfirmPW.setStyle(pwdCinfirmPW.getStyle()+"-fx-border-color: #7367F0;");


        if (!isValidName){
            txtFullname.setStyle(txtFullname.getStyle()+"-fx-border-color: red;");
        }
        if (!isValidUsername){
            pwdUsername.setStyle(pwdUsername.getStyle()+"-fx-border-color: red;");
        }
        if (!isValidEmail){
            txtEmail.setStyle(txtEmail.getStyle()+"-fx-border-color: red;");
        }
        if (!isValidPassword1){
            pwdEnterPW.setStyle(pwdEnterPW.getStyle()+"-fx-border-color: red;");
        }
        if (!isValidPassword2){
            pwdCinfirmPW.setStyle(pwdCinfirmPW.getStyle()+"-fx-border-color: red;");
        }

        if (!password.equals(enterPw)){
            pwdEnterPW.setStyle(pwdEnterPW.getStyle()+"-fx-border-color: red;");
            pwdCinfirmPW.setStyle(pwdCinfirmPW.getStyle()+"-fx-border-color: red;");
        }

        if (isValidName && isValidUsername && isValidEmail && isValidPassword2 && isValidPassword1 && password.equals(enterPw)) {
            boolean isUpdated = userBO.updateUser(
                    new UserDTO(
                            userId,
                            userFullName,
                            username,
                            userEmail,
                            password
                    ));
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "User Updated Successfully").show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "User update Failed").show();
            }
        }
    }


    @FXML
    void onClickTable(MouseEvent event) {
        UserTM selectedItem = tblUser.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            lblUserId.setText(selectedItem.getUserId());
            txtFullname.setText(selectedItem.getUserFullName());
            pwdUsername.setText(selectedItem.getUsername());
            txtEmail.setText(selectedItem.getUserEmail());
            pwdCinfirmPW.setText(selectedItem.getPassword());
            pwdEnterPW.setText(selectedItem.getPassword());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnGenerateReport.setDisable(false);
            btnEMailSendToUser.setDisable(false);
        }
    }
}
