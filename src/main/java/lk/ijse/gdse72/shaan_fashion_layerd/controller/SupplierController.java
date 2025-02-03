package lk.ijse.gdse72.shaan_fashion_layerd.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse72.shaan_fashion_layerd.bo.BOFactory;
import lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.SupplierBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.SupplierDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Supplier;
import lk.ijse.gdse72.shaan_fashion_layerd.view.tdm.SupplierTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnGenerateReport;

    @FXML
    private JFXButton btnResert;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private Label lblNotify;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierAddress;

    @FXML
    private TableColumn<SupplierTM , String> colSupplierContactNo;

    @FXML
    private TableColumn<SupplierTM , String> colSupplierId;

    @FXML
    private TableColumn<SupplierTM , String> colSupplierName;

    @FXML
    private TableColumn<SupplierTM , String> colSupplyItem;

    @FXML
    private Label lblSupplierId;

    @FXML
    private TableView<SupplierTM> tblSupplier;

    @FXML
    private JFXTextField txtSupplierAddress;

    @FXML
    private JFXTextField txtSupplierContactNo;

    @FXML
    private JFXTextField txtSupplierName;

    @FXML
    private JFXTextField txtSupplyItem;

    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colSupplyItem.setCellValueFactory(new PropertyValueFactory<>("supplyItem"));
        colSupplierAddress.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        colSupplierContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextCustomerID = supplierBO.generateNewSupplierId();
        lblSupplierId.setText(nextCustomerID);

        txtSupplierName.setText("");
        txtSupplyItem.setText("");
        txtSupplierAddress.setText("");
        txtSupplierContactNo.setText("");

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnGenerateReport.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        tblSupplier.getItems().clear();
        try {
            ArrayList<SupplierDTO> allSuppliers = supplierBO.getAllSuppliers();
            ObservableList<SupplierTM> suppliers = FXCollections.observableArrayList();

            for (SupplierDTO s : allSuppliers) {
                suppliers.add(new SupplierTM(
                        s.getSupplierId(),
                        s.getSupplierName(),
                        s.getSupplyItem(),
                        s.getSupplierAddress(),
                        s.getContactNo()
                ));
            }
            tblSupplier.setItems(suppliers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String customerId = lblSupplierId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = supplierBO.deleteSupplier(customerId);

            if (isDeleted) {
                lblNotify.setText("Customer deleted successfully!");
                refreshTable(); // Refresh the table to show updated data
                refreshPage();  // Refresh the entire page to clear fields
            } else {
                lblNotify.setText("Customer delete failed!");
            }
        }
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
        String supplierId = lblSupplierId.getText();
        String supplierName = txtSupplierName.getText();
        String supplierItem = txtSupplyItem.getText();
        String supplierAddress = txtSupplierAddress.getText();
        String supplierContactNo = txtSupplierContactNo.getText();

        // Define regex patterns for validation
        String namePattern = "^[A-Za-z ]+$";
        String addressPattern = "^^[a-zA-Z0-9\\s,.'-]{3,}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = supplierName.matches(namePattern);
        boolean isValidAddress = supplierAddress.matches(addressPattern);
        boolean isValidPhone = supplierContactNo.matches(phonePattern);

        // Reset input field styles
        txtSupplierName.setStyle(txtSupplierName.getStyle() + ";-fx-border-color: #7367F0;");
        txtSupplyItem.setStyle(txtSupplyItem.getStyle() + ";-fx-border-color: #7367F0;");
        txtSupplierAddress.setStyle(txtSupplierAddress.getStyle() + ";-fx-border-color: #7367F0;");
        txtSupplierContactNo.setStyle(txtSupplierContactNo.getStyle() + ";-fx-border-color: #7367F0;");

        // Highlight invalid fields in red
        if (!isValidName) {
            txtSupplierName.setStyle(txtSupplierName.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidAddress) {
            txtSupplierAddress.setStyle(txtSupplierAddress.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtSupplierContactNo.setStyle(txtSupplierContactNo.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidAddress && isValidPhone) {

            boolean isSaved = supplierBO.saveSupplier(
                    new SupplierDTO(
                            supplierId,
                            supplierName,
                            supplierItem,
                            supplierAddress,
                            supplierContactNo
                    ));

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "supplier save...!").show();
                lblNotify.setText("supplier successfully saved!");
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save supplier...!").show();
                lblNotify.setText("Failed to save supplier.");
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String supplierId = lblSupplierId.getText();
        String supplierName = txtSupplierName.getText();
        String supplierItem = txtSupplyItem.getText();
        String supplierAddress = txtSupplierAddress.getText();
        String supplierContactNo = txtSupplierContactNo.getText();

        // Define regex patterns for validation
        String namePattern = "^[A-Za-z ]+$";
        String addressPattern = "^^[a-zA-Z0-9\\s,.'-]{3,}$";
        //String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = supplierName.matches(namePattern);
        boolean isValidAddress = supplierAddress.matches(addressPattern);
        boolean isValidPhone = supplierContactNo.matches(phonePattern);

        // Reset input field styles
        txtSupplierName.setStyle(txtSupplierName.getStyle() + ";-fx-border-color: #7367F0;");
        txtSupplyItem.setStyle(txtSupplyItem.getStyle() + ";-fx-border-color: #7367F0;");
        txtSupplierAddress.setStyle(txtSupplierAddress.getStyle() + ";-fx-border-color: #7367F0;");
        txtSupplierContactNo.setStyle(txtSupplierContactNo.getStyle() + ";-fx-border-color: #7367F0;");

        // Highlight invalid fields in red
        if (!isValidName) {
            txtSupplierName.setStyle(txtSupplierName.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidAddress) {
            txtSupplierAddress.setStyle(txtSupplierAddress.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtSupplierContactNo.setStyle(txtSupplierContactNo.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidAddress && isValidPhone) {

            boolean isUpdated = supplierBO.updateSupplier(
                    new SupplierDTO(
                            supplierId,
                            supplierName,
                            supplierItem,
                            supplierAddress,
                            supplierContactNo
                    ));

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "supplier update...!").show();
                lblNotify.setText("supplier successfully update!");
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update supplier...!").show();
                lblNotify.setText("Failed to update supplier.");
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        SupplierTM selectedItem = tblSupplier.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblSupplierId.setText(selectedItem.getSupplierId());
            txtSupplierName.setText(selectedItem.getSupplierName());
            txtSupplyItem.setText(selectedItem.getSupplyItem());
            txtSupplierAddress.setText(selectedItem.getSupplierAddress());
            txtSupplierContactNo.setText(selectedItem.getContactNo());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnGenerateReport.setDisable(false);
        }
    }
}
