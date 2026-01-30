package com.agroconnect.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FarmerRegisterController {

    @FXML private TextField txtName;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtPhone;
    @FXML private TextField txtLocation;
    @FXML private Label lblStatus;

    @FXML
    private void handleRegister() {
        lblStatus.setText("Register Farmer clicked (connect DAO later).");
    }

    @FXML
    private void goBackToLogin() {
        lblStatus.setText("Back to Login (scene switch later).");
    }
}