package com.agroconnect.controller;

import com.agroconnect.dao.BuyerDAO;
import com.agroconnect.dao.FarmerDAO;
import com.agroconnect.model.BuyerUser;
import com.agroconnect.model.FarmerUser;
import com.agroconnect.util.Session;
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

    private final FarmerDAO farmerDAO = new FarmerDAO();
    private final BuyerDAO buyerDAO = new BuyerDAO();

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
        if (!email.contains("@")) {
            lblStatus.setText("Enter a valid email.");
            return;
        }

        if (pass.length() < 4) {
            lblStatus.setText("Password too short.");
            return;
        }

        try {
            if ("FARMER".equals(role)) {
                FarmerUser farmer = farmerDAO.login(email, pass);
                if (farmer == null) {
                    lblStatus.setText("Invalid farmer email or password.");
                    return;
                }
                Session.setCurrentUser(farmer);
                MainSceneController.getInstance().switchView("farmer_dashboard.fxml");
            } else {
                BuyerUser buyer = buyerDAO.login(email, pass); // throws SQLException in your DAO
                if (buyer == null) {
                   lblStatus.setText("Invalid buyer email or password.");
                    return;
                }
                Session.setCurrentUser(buyer);
                MainSceneController.getInstance().switchView("buyer_dashboard.fxml");
            }
        } catch (Exception e) {
            lblStatus.setText("Login error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClear() {
        txtEmail.clear();
        txtPassword.clear();
        lblStatus.setText("");
        roleCombo.getSelectionModel().selectFirst();
        Session.clear();
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