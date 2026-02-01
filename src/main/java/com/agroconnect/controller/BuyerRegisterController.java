package com.agroconnect.controller;

import com.agroconnect.dao.BuyerDAO;
import com.agroconnect.model.BuyerUser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.agroconnect.util.ValidationUtil;

public class BuyerRegisterController {

    @FXML private TextField txtName;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtPhone;
    @FXML private Label lblStatus;

    private final BuyerDAO buyerDAO = new BuyerDAO();

    @FXML
    private void handleRegister() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText();
        String phone = txtPhone.getText().trim();

        // Required fields
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            lblStatus.setText("Name, Email, and Password are required.");
            return;
        }

        // Email validation
        if (!ValidationUtil.isValidEmail(email)) {
            lblStatus.setText("Enter a valid email (example@mail.com).");
            return;
        }

        // Password validation
        if (!ValidationUtil.isValidPassword(password)) {
            lblStatus.setText("Password must be at least 5 characters long");
            return;
        }

        BuyerUser buyer = new BuyerUser(name, email, password, phone);

        try {
            int newId = buyerDAO.registerBuyer(buyer);

            if (newId > 0) {
                lblStatus.setText("Buyer registered successfully! ID: " + newId);
                MainSceneController.getInstance().switchView("login.fxml");
            } else {
                lblStatus.setText("Registration failed.");
            }

        } catch (Exception e) {
            lblStatus.setText("Registration error: Email may already exist.");
        }
    }

    @FXML
    private void goBackToLogin() {
        MainSceneController.getInstance().switchView("login.fxml");
    }
}