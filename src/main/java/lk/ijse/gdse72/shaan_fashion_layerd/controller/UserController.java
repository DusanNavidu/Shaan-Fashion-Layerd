package lk.ijse.gdse72.shaan_fashion_layerd.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse72.shaan_fashion_layerd.bo.BOFactory;
import lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.UserBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.UserDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.view.tdm.UserTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private JFXButton btnDelete;

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
    private JFXTextField txtEnterPassword;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserFullName.setCellValueFactory(new PropertyValueFactory<>("userFullName"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

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
        txtUsername.setText("");
        txtEmail.setText("");
        txtPassword.setText("");

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
        String username = txtUsername.getText();
        String userEmail = txtEmail.getText();
        String password = txtPassword.getText();

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String usernamePattern = "^[A-Za-z0-9]{5,}$";
        String passwordPattern = "^[0-9]{4,8}$";

        boolean isValidName = userFullName.matches(namePattern);
        boolean isValidEmail = userEmail.matches(emailPattern);
        boolean isValidUsername = username.matches(usernamePattern);
        boolean isValidPassword = password.matches(passwordPattern);

        txtFullname.setStyle(txtFullname.getStyle()+"-fx-border-color: #7367F0;");
        txtUsername.setStyle(txtUsername.getStyle()+"-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle()+"-fx-border-color: #7367F0;");
        txtPassword.setStyle(txtPassword.getStyle()+"-fx-border-color: #7367F0;");

        if (!isValidName){
            txtFullname.setStyle(txtFullname.getStyle()+"-fx-border-color: red;");
        }
        if (!isValidUsername){
            txtUsername.setStyle(txtUsername.getStyle()+"-fx-border-color: red;");
        }
        if (!isValidEmail){
            txtEmail.setStyle(txtEmail.getStyle()+"-fx-border-color: red;");
        }
        if (!isValidPassword){
            txtPassword.setStyle(txtPassword.getStyle()+"-fx-border-color: red;");
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
    void btnUpdateOnAction(ActionEvent event) throws Exception {
        String userId = lblUserId.getText();
        String userFullName = txtFullname.getText();
        String username = txtUsername.getText();
        String userEmail = txtEmail.getText();
        String password = txtPassword.getText();

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String usernamePattern = "^[A-Za-z0-9]{5,}$";
        String passwordPattern = "^[0-9]{4,8}$";

        boolean isValidName = userFullName.matches(namePattern);
        boolean isValidEmail = userEmail.matches(emailPattern);
        boolean isValidUsername = username.matches(usernamePattern);
        boolean isValidPassword = password.matches(passwordPattern);

        txtFullname.setStyle(txtFullname.getStyle()+"-fx-border-color: #7367F0;");
        txtUsername.setStyle(txtUsername.getStyle()+"-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle()+"-fx-border-color: #7367F0;");
        txtPassword.setStyle(txtPassword.getStyle()+"-fx-border-color: #7367F0;");

        if (!isValidName){
            txtFullname.setStyle(txtFullname.getStyle()+"-fx-border-color: red;");
        }
        if (!isValidUsername){
            txtUsername.setStyle(txtUsername.getStyle()+"-fx-border-color: red;");
        }
        if (!isValidEmail){
            txtEmail.setStyle(txtEmail.getStyle()+"-fx-border-color: red;");
        }
        if (!isValidPassword){
            txtPassword.setStyle(txtPassword.getStyle()+"-fx-border-color: red;");
        }

        if (isValidName && isValidUsername && isValidEmail && isValidPassword) {
            boolean isUpdated = userBO.updateUser(
                    new UserDTO(
                            userId,
                            userFullName,
                            username,
                            userEmail,
                            password
                    ));
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "User Update Successfully").show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "User Update Failed").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        UserTM selectedItem = tblUser.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            lblUserId.setText(selectedItem.getUserId());
            txtFullname.setText(selectedItem.getUserFullName());
            txtUsername.setText(selectedItem.getUsername());
            txtEmail.setText(selectedItem.getUserEmail());
            txtPassword.setText(selectedItem.getPassword());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnGenerateReport.setDisable(false);
            btnEMailSendToUser.setDisable(false);
        }
    }
}
