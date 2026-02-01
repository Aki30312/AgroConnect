package com.agroconnect.controller;

import javafx.fxml.FXML;

public class BuyerDashboardController {

    @FXML
    private void goToProducts() {
        MainSceneController.getInstance().switchView("buyer_products.fxml");
    }

    @FXML
    private void goToOrders() {
        MainSceneController.getInstance().switchView("buyer_orders.fxml");
    }

    @FXML
    private void logout() {
        com.agroconnect.util.Session.clear();
        MainSceneController.getInstance().switchView("login.fxml");
    }
}