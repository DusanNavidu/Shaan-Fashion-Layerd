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
import lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.CategoryBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl.CategoryDAOImpl;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.CategoryDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Category;
import lk.ijse.gdse72.shaan_fashion_layerd.view.tdm.CategoryTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {

    @FXML
    private JFXButton btnCategoryReport;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnResert;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<CategoryTM, String> colCategoryId;

    @FXML
    private TableColumn<CategoryTM, String> colCategoryName;

    @FXML
    private TableColumn<CategoryTM, String> colDescprition;

    @FXML
    private Label lblCategoryId;

    @FXML
    private Label lblNotify;

    @FXML
    private TableView<CategoryTM> tbtCategory;

    @FXML
    private JFXTextField txtCategoryName;

    @FXML
    private JFXTextField txtDescription;

    CategoryBO categoryBO = (CategoryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CATEGORY);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colDescprition.setCellValueFactory(new PropertyValueFactory<>("description"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextCategoryId = categoryBO.generateNewCategoryId();
        lblCategoryId.setText(nextCategoryId);

        txtCategoryName.setText("");
        txtDescription.setText("");

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        tbtCategory.getItems().clear();
        try {
            ArrayList<CategoryDTO> allCategory = categoryBO.getAllCategoryS();
            ObservableList<CategoryTM> categoryTMS = FXCollections.observableArrayList();

            for (CategoryDTO ca : allCategory) {
                categoryTMS.add(new CategoryTM(
                        ca.getCategoryId(),
                        ca.getCategoryName(),
                        ca.getDescription()
                ));
            }
            tbtCategory.setItems(categoryTMS);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnCategoryReportOnAcrion(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String categoryId = lblCategoryId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this category?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = categoryBO.deleteCategory(categoryId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Category delete...!").show();
                lblNotify.setText("Category successfully delete!");
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Category...!").show();
                lblNotify.setText("Failed to delete category.");
            }
        }
    }

    @FXML
    void btnResertOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String categoryId = lblCategoryId.getText();
        String categoryName = txtCategoryName.getText();
        String description = txtDescription.getText();

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = categoryName.matches(namePattern);

        txtCategoryName.setStyle(txtCategoryName.getStyle() + ";-fx-border-color: #7367F0;");
        txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: #7367F0;");


        if (!isValidName) {
            txtCategoryName.setStyle(txtCategoryName.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName ) {

            boolean isSaved = categoryBO.saveCategory(
                    new CategoryDTO(
                            categoryId,
                            categoryName,
                            description
                    ));

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Category saved...!").show();
                lblNotify.setText("Category successfully saved!");
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Category...!").show();
                lblNotify.setText("Failed to save category.");
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String categoryId = lblCategoryId.getText();
        String categoryName = txtCategoryName.getText();
        String description = txtDescription.getText();


        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";


        boolean isValidName = categoryName.matches(namePattern);


        txtCategoryName.setStyle(txtCategoryName.getStyle() + ";-fx-border-color: #7367F0;");
        txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidName) {
            txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidName) {

            boolean isUpdate = categoryBO.updateCategory(
                    new CategoryDTO(
                            categoryId,
                            categoryName,
                            description
                    ));

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Category update...!").show();
                lblNotify.setText("Category successfully update!");
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update Category...!").show();
                lblNotify.setText("Failed to update category.");
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        CategoryTM selectedItem = tbtCategory.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblCategoryId.setText(selectedItem.getCategoryId());
            txtCategoryName.setText(selectedItem.getCategoryName());
            txtDescription.setText(selectedItem.getDescription());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
}
