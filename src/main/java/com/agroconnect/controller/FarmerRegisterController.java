package com.agroconnect.controller;

import com.agroconnect.dao.FarmerDAO;
import com.agroconnect.model.FarmerUser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.agroconnect.util.ValidationUtil;

public class FarmerRegisterController {

    @FXML private TextField txtName;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtPhone;
    @FXML private TextField txtLocation;
    @FXML private Label lblStatus;

    private final FarmerDAO farmerDAO = new FarmerDAO();

    @FXML
    private void handleRegister() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText();
        String phone = txtPhone.getText().trim();
        String location = txtLocation.getText().trim();

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
            lblStatus.setText("Password must be 8+ chars with Upper, Lower, Number, Special.");
            return;
        }

        FarmerUser farmer = new FarmerUser(name, email, password, phone, location);

        boolean ok = farmerDAO.registerFarmer(farmer);

        if (ok) {
            lblStatus.setText("Farmer registered successfully!");
            MainSceneController.getInstance().switchView("login.fxml");
        } else {
            lblStatus.setText("Registration failed (email may already exist).");
        }
    }

    @FXML
    private void goBackToLogin() {
        MainSceneController.getInstance().switchView("login.fxml");
    }
}