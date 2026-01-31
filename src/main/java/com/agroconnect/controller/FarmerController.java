package com.agroconnect.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FarmerController {

    // products screen fields
    @FXML private Label lblStatus;
    @FXML private TextField txtName;
    @FXML private TextField txtPrice;
    @FXML private TextField txtQuantity;

    @FXML private TableView<?> tableProducts;
    @FXML private TableColumn<?, ?> colId;
    @FXML private TableColumn<?, ?> colName;
    @FXML private TableColumn<?, ?> colPrice;
    @FXML private TableColumn<?, ?> colQuantity;

    // profile screen fields
    @FXML private TextField txtProfileName;
    @FXML private TextField txtProfilePhone;
    @FXML private TextField txtProfileLocation;

    @FXML
    public void initialize() {
        // This controller is shared; initialize will run for whichever FXML is loaded.
    }

    // dashboard navigation
    @FXML
    private void goToProducts() {
        MainSceneController.getInstance().switchView("farmer_product.fxml");
    }

    @FXML
    private void goToProfile() {
        MainSceneController.getInstance().switchView("farmer_profile.fxml");
    }

    @FXML
    private void logout() {
        MainSceneController.getInstance().switchView("login.fxml");
    }

    // products actions
    @FXML
    private void handleAddProduct() {
        if (lblStatus != null) lblStatus.setText("Add product clicked (connect DAO later).");
    }

    @FXML
    private void handleUpdateProduct() {
        if (lblStatus != null) lblStatus.setText("Update product clicked (connect DAO later).");
    }

    @FXML
    private void handleDeleteProduct() {
        if (lblStatus != null) lblStatus.setText("Delete product clicked (connect DAO later).");
    }

    @FXML
    private void handleRefreshProducts() {
        if (lblStatus != null) lblStatus.setText("Refresh products clicked (connect DAO later).");
    }

    @FXML
    private void goBackToDashboard() {
        MainSceneController.getInstance().switchView("farmer_dashboard.fxml");
    }

    // profile actions
    @FXML
    private void handleUpdateProfile() {
        // later: FarmerDAO.updateProfile(...)
        if (lblStatus != null) lblStatus.setText("Update profile clicked (connect DAO later).");
    }
}