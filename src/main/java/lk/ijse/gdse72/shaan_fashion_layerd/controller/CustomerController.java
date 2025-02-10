package lk.ijse.gdse72.shaan_fashion_layerd.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
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
import lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.CustomerBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.CustomerDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.view.tdm.CustomerTM;
import lk.ijse.gdse72.shaan_fashion_layerd.bo.BOFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class CustomerController implements Initializable {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEMailSendToCustomer;

    @FXML
    private JFXButton btnGenerateReport;

    @FXML
    private JFXButton btnOrderReport;

    @FXML
    private JFXButton btnResert;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private Label lblNotify;

    @FXML
    private TableColumn<CustomerTM, String> colCustomerAddress;

    @FXML
    private TableColumn<CustomerTM, String> colCustomerEmail;

    @FXML
    private TableColumn<CustomerTM, String> colCustomerId;

    @FXML
    private TableColumn<CustomerTM, String> colCustomerName;

    @FXML
    private TableColumn<CustomerTM, String> colUserId;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblUserId;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private JFXTextField txtCustomerAddress;

    @FXML
    private JFXTextField txtCustomerEmail;

    @FXML
    private JFXTextField txtCustomerName;

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextCustomerID = customerBO.generateNewCustomerId();
        lblCustomerId.setText(nextCustomerID);

        lblUserId.setText("U001");
        txtCustomerName.setText("");
        txtCustomerAddress.setText("");
        txtCustomerEmail.setText("");

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnGenerateReport.setDisable(true);
        btnEMailSendToCustomer.setDisable(true);
    }

    private void refreshTable() {
        tblCustomer.getItems().clear();
        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers();
            ObservableList<CustomerTM> customers = FXCollections.observableArrayList();

            for (CustomerDTO c : allCustomers) {
                customers.add(new CustomerTM(
                        c.getCustomerId(),
                        c.getUserId(),
                        c.getCustomerName(),
                        c.getCustomerAddress(),
                        c.getCustomerEmail()
                ));
            }

            tblCustomer.setItems(customers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnEMailSendToCustomerOnAction(ActionEvent event) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Please select customer..!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MailSendForm.fxml"));
            Parent load = loader.load();

            MailSendController sendMailController = loader.getController();

            String email = selectedItem.getCustomerEmail();
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
    void btnOrderReportOnAction(ActionEvent event) {
    }

    @FXML
    void btnGenerateReportOnAction(ActionEvent event) {

    }

    @FXML
    void btnResertOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String customerId = lblCustomerId.getText();
        String userId = lblUserId.getText();
        String customerName = txtCustomerName.getText();
        String customerAddress = txtCustomerAddress.getText();
        String customerEmail = txtCustomerEmail.getText();

        // Define regex patterns for validation
        String namePattern = "^[A-Za-z ]+$";
        String addressPattern = "^^[a-zA-Z0-9\\s,.'-]{3,}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        //Validate each field using regex patterns
        boolean isValidName = customerName.matches(namePattern);
        boolean isValidAddress = customerAddress.matches(addressPattern);
        boolean isValidEmail = customerEmail.matches(emailPattern);

        txtCustomerName.setStyle(txtCustomerName.getStyle() + ";-fx-border-color: #7367F0;");
        txtCustomerAddress.setStyle(txtCustomerAddress.getStyle() + ";-fx-border-color: #7367F0;");
        txtCustomerEmail.setStyle(txtCustomerEmail.getStyle() + ";-fx-border-color: #7367F0;");

        // Highlight invalid fields in red

        if (!isValidName) {
            txtCustomerName.setStyle(txtCustomerName.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidAddress) {
            txtCustomerAddress.setStyle(txtCustomerAddress.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidEmail) {
            txtCustomerEmail.setStyle(txtCustomerEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidAddress && isValidEmail) {

            boolean isSaved = customerBO.saveCustomer(
                    new CustomerDTO(
                            customerId,
                            userId,
                            customerName,
                            customerAddress,
                            customerEmail
                    ));

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "customer save...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save customer...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String customerId = lblCustomerId.getText();
        String userId = lblUserId.getText();
        String customerName = txtCustomerName.getText();
        String customerAddress = txtCustomerAddress.getText();
        String customerEmail = txtCustomerEmail.getText();

        // Define regex patterns for validation
        String namePattern = "^[A-Za-z ]+$";
        String addressPattern = "^^[a-zA-Z0-9\\s,.'-]{3,}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        //Validate each field using regex patterns
        boolean isValidName = customerName.matches(namePattern);
        boolean isValidAddress = customerAddress.matches(addressPattern);
        boolean isValidEmail = customerEmail.matches(emailPattern);

        txtCustomerName.setStyle(txtCustomerName.getStyle() + ";-fx-border-color: #7367F0;");
        txtCustomerAddress.setStyle(txtCustomerAddress.getStyle() + ";-fx-border-color: #7367F0;");
        txtCustomerEmail.setStyle(txtCustomerEmail.getStyle() + ";-fx-border-color: #7367F0;");

        // Highlight invalid fields in red

        if (!isValidName) {
            txtCustomerName.setStyle(txtCustomerName.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidAddress) {
            txtCustomerAddress.setStyle(txtCustomerAddress.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidEmail) {
            txtCustomerEmail.setStyle(txtCustomerEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidAddress && isValidEmail) {

            boolean isUpdated = customerBO.updateCustomer(
                    new CustomerDTO(
                            customerId,
                            userId,
                            customerName,
                            customerAddress,
                            customerEmail
                    )
            );

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully!").show();
                refreshTable(); // Refresh the table to show updated data
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer update failed!").show();}
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String customerId = lblCustomerId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = customerBO.deleteCustomer(customerId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully!").show();
                refreshPage();  // Refresh the entire page to clear fields
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer delete failed!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblCustomerId.setText(selectedItem.getCustomerId());
            lblUserId.setText(selectedItem.getUserId());
            txtCustomerName.setText(selectedItem.getCustomerName());
            txtCustomerAddress.setText(selectedItem.getCustomerAddress());
            txtCustomerEmail.setText(selectedItem.getCustomerEmail());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnGenerateReport.setDisable(false);
            btnEMailSendToCustomer.setDisable(false);
        }
    }
}