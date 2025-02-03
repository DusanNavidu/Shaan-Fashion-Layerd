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
import lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.ItemBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.ItemDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl.BrandDAOImpl;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl.CategoryDAOImpl;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.ItemDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Item;
import lk.ijse.gdse72.shaan_fashion_layerd.view.tdm.ItemTM;


import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnItemReport;

    @FXML
    private JFXButton btnResert;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<ItemTM, String> colBatchNumber;

    @FXML
    private TableColumn<ItemTM , String> colBrandId;

    @FXML
    private TableColumn<ItemTM , String> colCategoryId;

    @FXML
    private TableColumn<ItemTM , String> colDescription;

    @FXML
    private TableColumn<ItemTM , String> colItemId;

    @FXML
    private TableColumn<ItemTM , String> colItemName;

    @FXML
    private TableColumn<ItemTM , String> colItemQuantity;

    @FXML
    private TableColumn<ItemTM , BigDecimal> colPrice;

    @FXML
    private TableColumn<ItemTM , BigDecimal> colProfit;

    @FXML
    private ComboBox<String> comBrandId;

    @FXML
    private ComboBox<String> comCategoryId;

    @FXML
    private Label lblBatchNumber;

    @FXML
    private Label lblBrandId;

    @FXML
    private Label lblCategoryId;

    @FXML
    private Label lblItemId;

    @FXML
    private TableView<ItemTM> tblItem;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private JFXTextField txtItemQuantity;

    @FXML
    private JFXTextField txtPeice;

    @FXML
    private Label lblNotify;

    @FXML
    private JFXTextField txtProfit;

    private final CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
    private final BrandDAOImpl brandDAO = new BrandDAOImpl();

    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colBrandId.setCellValueFactory(new PropertyValueFactory<>("brandId"));
        colItemQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantityOnHand"));
        colBatchNumber.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colProfit.setCellValueFactory(new PropertyValueFactory<>("profit"));
        try {
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        comCategoryId.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            try {
                if (newValue != null) {
                    lblCategoryId.setText(categoryDAO.getCategoryNameById(newValue));
                } else {
                    lblCategoryId.setText("");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        comBrandId.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            try {
                if (newValue != null) {
                    lblBrandId.setText(brandDAO.getBrandNameById(newValue));
                } else {
                    lblBrandId.setText("");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextItemID = itemBO.generateNewItemId();
        lblItemId.setText(nextItemID);

        txtItemName.setText("");

        loadCategoryIds();
        loadBrandIds();

        comCategoryId.getSelectionModel().clearSelection();
        comBrandId.getSelectionModel().clearSelection();

        txtItemQuantity.setText("");

        String nextBatchNumber = itemBO.generateNewBatchNumber();
        lblBatchNumber.setText(nextBatchNumber);

        txtDescription.setText("");
        txtPeice.setText("");
        txtProfit.setText("");

        btnResert.setDisable(false);
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void loadCategoryIds() throws SQLException {
        ArrayList<String> categoryIds = categoryDAO.getAllCategoryIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(categoryIds);
        comCategoryId.setItems(observableList);
    }

    private void loadBrandIds() throws SQLException {
        ArrayList<String> categoryIds = brandDAO.getAllBrandIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(categoryIds);
        comBrandId.setItems(observableList);
    }


    private void refreshTable() {
        tblItem.getItems().clear();
        try {
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            for (ItemDTO i : allItems) {
                tblItem.getItems().add(new ItemTM(
                        i.getItemId(),
                        i.getItemName(),
                        i.getCategoryId(),
                        i.getBrandId(),
                        i.getItemQuantityOnHand(),
                        i.getBatchNumber(),
                        i.getDescription(),
                        i.getPrice(),
                        i.getProfit()
                ));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String customerId = lblItemId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Item?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = itemBO.deleteItem(customerId);

            if (isDeleted) {
                lblNotify.setText("Item deleted successfully!");
                refreshTable(); // Refresh the table to show updated data
                refreshPage();  // Refresh the entire page to clear fields
            } else {
                lblNotify.setText("Item delete failed!");
            }
        }
    }

    @FXML
    void btnItemReportOnAction(ActionEvent event) {

    }

    @FXML
    void btnResertOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    private void resetStyles() {
        txtItemName.setStyle(txtItemName.getStyle() + "; -fx-border-color: #7367F0;");
        txtItemQuantity.setStyle(txtItemQuantity.getStyle() + "; -fx-border-color: #7367F0;");
        txtPeice.setStyle(txtPeice.getStyle() + "; -fx-border-color: #7367F0;");
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws IOException {
        String itemId = lblItemId.getText();
        String itemName = txtItemName.getText();
        String categoryId = comCategoryId.getSelectionModel().getSelectedItem();
        String brandId = comBrandId.getSelectionModel().getSelectedItem();
        String itemQuantityOnHand = txtItemQuantity.getText();
        String batchNumber = lblBatchNumber.getText();
        String description = txtDescription.getText();
        String priceStr = txtPeice.getText();
        String profitStr = txtProfit.getText();

        String quantityPattern = "^[0-9]+$";
        String pricePattern = "^\\d+(\\.\\d{1,2})?$";
        String namePattern = "^[A-Za-z ]+$";

        boolean isValidName = itemName != null && itemName.matches(namePattern);
        boolean isValidQuantity = itemQuantityOnHand.matches(quantityPattern);
        boolean isValidPrice = priceStr.matches(pricePattern);

        System.out.println(isValidQuantity + " / " + itemQuantityOnHand);

        resetStyles();

        if (!isValidName) {
            txtItemName.setStyle(txtItemName.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidQuantity) {
            txtItemQuantity.setStyle(txtItemQuantity.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidPrice) {
            txtPeice.setStyle(txtPeice.getStyle() + "; -fx-border-color: red;");
        }

        if (isValidName && isValidQuantity && isValidPrice) {
            resetStyles();

            int quantity = Integer.parseInt(itemQuantityOnHand);
            double price = Double.parseDouble(priceStr);
            BigDecimal profit = new BigDecimal(profitStr);

            Item item = new Item(itemId, itemName, categoryId, brandId, itemQuantityOnHand, batchNumber, description, price, profit);

            try {
                boolean isSaved = itemBO.saveItem(
                        new ItemDTO(
                                itemId,
                                itemName,
                                categoryId,
                                brandId,
                                itemQuantityOnHand,
                                batchNumber,
                                description,
                                price,
                                profit
                        ));

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Item saved successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save item!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to save item due to an SQL error!").show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String itemId = lblItemId.getText();
        String itemName = txtItemName.getText();
        String categoryId = comCategoryId.getSelectionModel().getSelectedItem();
        String brandId = comBrandId.getSelectionModel().getSelectedItem();
        String itemQuantityOnHand = txtItemQuantity.getText();
        String batchNumber = lblBatchNumber.getText();
        String description = txtDescription.getText();
        String priceStr = txtPeice.getText();
        String profitStr = txtProfit.getText();

        String quantityPattern = "^[0-9]+$";
        String pricePattern = "^\\d+(\\.\\d{1,2})?$";
        String namePattern = "^[A-Za-z ]+$";

        boolean isValidName = itemName != null && itemName.matches(namePattern);
        boolean isValidQuantity = itemQuantityOnHand.matches(quantityPattern);
        boolean isValidPrice = priceStr.matches(pricePattern);

        resetStyles();

        if (!isValidName) {
            txtItemName.setStyle(txtItemName.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidQuantity) {
            txtItemQuantity.setStyle(txtItemQuantity.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidPrice) {
            txtPeice.setStyle(txtPeice.getStyle() + "; -fx-border-color: red;");
        }

        if (isValidName && isValidQuantity && isValidPrice) {
            resetStyles();

            int quantity = Integer.parseInt(itemQuantityOnHand);
            double price = Double.parseDouble(priceStr);
            BigDecimal profit = new BigDecimal(profitStr);

            try {
                boolean isUpdated = itemBO.updateItem(
                        new ItemDTO(
                                itemId,
                                itemName,
                                categoryId,
                                brandId,
                                itemQuantityOnHand,
                                batchNumber,
                                description,
                                price,
                                profit
                        ));

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Item updated successfully!").show();
                    refreshPage();

                    btnUpdate.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update item!").show();
                    btnUpdate.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                }
            } catch (SQLException e) {
                e.printStackTrace();

                new Alert(Alert.AlertType.ERROR, "Failed to update item due to an SQL error!").show();
                btnUpdate.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        ItemTM selectedItem = tblItem.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblItemId.setText(selectedItem.getItemId());
            txtItemName.setText(selectedItem.getItemName());
            comCategoryId.getSelectionModel().select(selectedItem.getCategoryId());
            comBrandId.getSelectionModel().select(selectedItem.getBrandId());
            txtItemQuantity.setText(String.valueOf(selectedItem.getItemQuantityOnHand())); // if it's an integer
            lblBatchNumber.setText(selectedItem.getBatchNumber());
            txtDescription.setText(selectedItem.getDescription());
            txtPeice.setText(String.valueOf(selectedItem.getPrice())); // if it's a number
            txtProfit.setText(String.valueOf(selectedItem.getProfit())); // if it's a number

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnItemReport.setDisable(false);
        }
    }
}
