package com.agroconnect.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> roleCombo;
    @FXML private Label lblStatus;

    @FXML
    public void initialize() {
        roleCombo.getItems().addAll("FARMER", "BUYER");
        roleCombo.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleLogin() {
        String email = txtEmail.getText().trim();
        String pass = txtPassword.getText();
        String role = roleCombo.getValue();

        if (email.isEmpty() || pass.isEmpty()) {
            lblStatus.setText("Email and password are required.");
            return;
        }

        // TEMP until DAO auth is connected
        if ("FARMER".equals(role)) {
            MainSceneController.getInstance().switchView("farmer_dashboard.fxml");
        } else {
            MainSceneController.getInstance().switchView("buyer_dashboard.fxml");
        }
    }

    @FXML
    private void handleClear() {
        txtEmail.clear();
        txtPassword.clear();
        lblStatus.setText("");
        roleCombo.getSelectionModel().selectFirst();
    }

    @FXML
    private void goToFarmerRegister() {
        MainSceneController.getInstance().switchView("farmer_register.fxml");
    }

    @FXML
    private void goToBuyerRegister() {
        MainSceneController.getInstance().switchView("buyer_register.fxml");
    }

}