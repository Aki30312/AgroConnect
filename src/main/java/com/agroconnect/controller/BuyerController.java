package com.agroconnect.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BuyerController {

    @FXML private Label lblStatus;
    @FXML private TextField txtOrderQty;

    @FXML private TableView<?> tableProducts;
    @FXML private TableColumn<?, ?> colId;
    @FXML private TableColumn<?, ?> colName;
    @FXML private TableColumn<?, ?> colPrice;
    @FXML private TableColumn<?, ?> colQuantity;

    @FXML
    public void initialize() {
        if (lblStatus != null) lblStatus.setText("Buyer products loaded (logic later).");
    }

    @FXML
    private void handlePlaceOrder() {
        if (lblStatus != null) lblStatus.setText("Place Order clicked (connect DAO later).");
    }

    @FXML
    private void handleRefreshProducts() {
        if (lblStatus != null) lblStatus.setText("Refresh clicked (connect DAO later).");
    }

    @FXML
    private void goBackToDashboard() {
        MainSceneController.getInstance().switchView("buyer_dashboard.fxml");
    }
}