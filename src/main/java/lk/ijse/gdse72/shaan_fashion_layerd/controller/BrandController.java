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
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl.BrandDAOImpl;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Brand;
import lk.ijse.gdse72.shaan_fashion_layerd.view.tdm.BrandTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class BrandController implements Initializable {

    @FXML
    private JFXButton btnBrandReport;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnResert;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<BrandTM , String> colBrandName;

    @FXML
    private TableColumn<BrandTM , String> colBrandId;

    @FXML
    private TableColumn<BrandTM , String> colDescprition;

    @FXML
    private Label lblBrandId;

    @FXML
    private Label lblNotify;

    @FXML
    private TableView<BrandTM> tblBrand;

    @FXML
    private JFXTextField txtBrandName;

    @FXML
    private JFXTextField txtDescription;

    BrandDAOImpl brandDAO = new BrandDAOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colBrandId.setCellValueFactory(new PropertyValueFactory<>("brandId"));
        colBrandName.setCellValueFactory(new PropertyValueFactory<>("brandName"));
        colDescprition.setCellValueFactory(new PropertyValueFactory<>("description"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextCustomerID = brandDAO.generateNewID();
        lblBrandId.setText(nextCustomerID);

        txtBrandName.setText("");
        txtDescription.setText("");

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnBrandReport.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<Brand> brands = brandDAO.getAll();
        ObservableList<BrandTM> brandTMS = FXCollections.observableArrayList();


        for (Brand brand : brands) {
            BrandTM brandTM = new BrandTM(
                    brand.getBrandId(),
                    brand.getBrandName(),
                    brand.getDescription()
            );
            brandTMS.add(brandTM);
        }
        tblBrand.setItems(brandTMS);
    }

    @FXML
    void btnBrandReportOnAcrion(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String brandId = lblBrandId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this brand?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = brandDAO.delete(brandId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "brand delete...!").show();
                lblNotify.setText("brand successfully delete!");
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete brand...!").show();
                lblNotify.setText("Failed to delete brand.");
            }
        }
    }

    @FXML
    void btnResertOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String brandId = lblBrandId.getText();
        String brandName = txtBrandName.getText();
        String description = txtDescription.getText();


        // Define regex patterns for validation
        String namePattern = "^[A-Za-z ]+$";

        boolean isValidName = brandName.matches(namePattern);

        // Reset input field styles
        txtBrandName.setStyle(txtBrandName.getStyle() + ";-fx-border-color: #7367F0;");
        txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: #7367F0;");

        // Highlight invalid fields in red
        if (!isValidName) {
            txtBrandName.setStyle(txtBrandName.getStyle() + ";-fx-border-color: red;");
        }

        // Save customer if all fields are valid
        if (isValidName) {
            Brand brand = new Brand(brandId, brandName, description);

            boolean isSaved = brandDAO.save(brand);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Brand save...!").show();
                lblNotify.setText("Brand successfully saved!");
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save brand...!").show();
                lblNotify.setText("Failed to save brand.");
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String brandId = lblBrandId.getText();
        String brandName = txtBrandName.getText();
        String description = txtDescription.getText();


        // Define regex patterns for validation
        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = brandName.matches(namePattern);

        // Reset input field styles
        txtBrandName.setStyle(txtBrandName.getStyle() + ";-fx-border-color: #7367F0;");
        txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: #7367F0;");

        // Highlight invalid fields in red
        if (!isValidName) {
            txtBrandName.setStyle(txtBrandName.getStyle() + ";-fx-border-color: red;");
        }

        // update customer if all fields are valid
        if (isValidName) {
            Brand brand = new Brand(brandId, brandName, description);

            boolean isUpdate = brandDAO.update(brand);

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Brand update...!").show();
                lblNotify.setText("Brand successfully updated!");
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update brand...!").show();
                lblNotify.setText("Failed to update brand.");
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        BrandTM selectedItem = tblBrand.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblBrandId.setText(selectedItem.getBrandId());
            txtBrandName.setText(selectedItem.getBrandName());
            txtDescription.setText(selectedItem.getDescription());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnBrandReport.setDisable(false);
        }
    }
}
