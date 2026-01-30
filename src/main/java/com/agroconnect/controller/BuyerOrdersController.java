package com.agroconnect.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class BuyerOrdersController {

    @FXML private Label lblStatus;

    @FXML private TableView<?> tableOrders;
    @FXML private TableColumn<?, ?> colOrderId;
    @FXML private TableColumn<?, ?> colProductId;
    @FXML private TableColumn<?, ?> colQuantity;
    @FXML private TableColumn<?, ?> colDate;

    @FXML
    public void initialize() {
        if (lblStatus != null) lblStatus.setText("Orders screen (logic later).");
    }

    @FXML
    private void handleRefreshOrders() {
        if (lblStatus != null) lblStatus.setText("Refresh orders clicked (connect DAO later).");
    }

    @FXML
    private void goBackToDashboard() {
        MainSceneController.getInstance().switchView("buyer_dashboard.fxml");
    }
}