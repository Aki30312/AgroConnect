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
        // TODO: Later connect FarmerDAO / BuyerDAO login here
        String email = txtEmail.getText().trim();
        String pass = txtPassword.getText();

        if (email.isEmpty() || pass.isEmpty()) {
            lblStatus.setText("Email and password are required.");
            return;
        }

        lblStatus.setText("Login clicked (" + roleCombo.getValue() + "). Connect DAO later.");
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
        lblStatus.setText("Open Farmer registration screen (to be connected).");
    }

    @FXML
    private void goToBuyerRegister() {
        lblStatus.setText("Open Buyer registration screen (to be connected).");
    }
}