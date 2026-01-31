package com.agroconnect.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class BuyerRegisterController {

    @FXML private TextField txtName;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtPhone;
    @FXML private Label lblStatus;

    @FXML
    private void handleRegister() {
        lblStatus.setText("Register Buyer clicked (connect DAO later).");
    }

    @FXML
    private void goBackToLogin() {
        MainSceneController.getInstance().switchView("login.fxml");
    }

}