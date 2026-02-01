package com.agroconnect.controller;

import com.agroconnect.dao.FarmerDAO;
import com.agroconnect.dao.ProductDAO;
import com.agroconnect.model.FarmerUser;
import com.agroconnect.model.Product;
import com.agroconnect.util.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class FarmerController {

    // --- Products screen UI ---
    @FXML private Label lblStatus;
    @FXML private TextField txtName;
    @FXML private TextField txtPrice;
    @FXML private TextField txtQuantity;

    // --- Profile screen UI ---
    @FXML private TextField txtProfileName;
    @FXML private TextField txtProfilePhone;
    @FXML private TextField txtProfileLocation;

    @FXML private TableView<Product> tableProducts;
    @FXML private TableColumn<Product, Integer> colId;
    @FXML private TableColumn<Product, String> colName;
    @FXML private TableColumn<Product, Double> colPrice;
    @FXML private TableColumn<Product, Integer> colQuantity;

    private final ProductDAO productDAO = new ProductDAO();
    private final FarmerDAO farmerDAO = new FarmerDAO();

    private final ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        // --- Products screen setup ---
        if (tableProducts != null) {
            setupTable();
            loadMyProducts();

            tableProducts.getSelectionModel().selectedItemProperty().addListener((obs, oldV, selected) -> {
                if (selected != null) {
                    txtName.setText(selected.getName());
                    txtPrice.setText(String.valueOf(selected.getPrice()));
                    txtQuantity.setText(String.valueOf(selected.getQuantity()));
                }
            });
        }

        // --- Profile screen setup ---
        if (txtProfileName != null) {
            loadProfileFromSession();
        }
    }

    private void setupTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableProducts.setItems(products);
    }

    private int currentFarmerId() {
        if (Session.getCurrentUser() instanceof FarmerUser f) return f.getId();
        return 0;
    }

    private void setStatus(String msg) {
        // lblStatus exists only on some screens, so guard it
        if (lblStatus != null) lblStatus.setText(msg);
    }

    private void loadMyProducts() {
        try {
            int farmerId = currentFarmerId();
            if (farmerId == 0) {
                setStatus("No farmer logged in.");
                return;
            }
            products.setAll(productDAO.getProductsByFarmer(farmerId));
            setStatus("Loaded " + products.size() + " products.");
        } catch (Exception e) {
            setStatus("Load failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddProduct() {

        if (!(Session.getCurrentUser() instanceof FarmerUser)) {
            setStatus("No farmer logged in.");
            return;
        }

        String name = txtName.getText().trim();
        String priceText = txtPrice.getText().trim();
        String qtyText = txtQuantity.getText().trim();

        if (name.isEmpty()||  priceText.isEmpty() || qtyText.isEmpty()) {
            setStatus("All fields are required.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            int qty = Integer.parseInt(qtyText);

            if (price <= 0) {
                setStatus("Price must be greater than 0.");
                return;
            }
            if (qty <= 0) {
                setStatus("Quantity must be greater than 0.");
                return;
            }

            int farmerId = currentFarmerId();
            Product p = new Product(0, farmerId, name, price, qty);
            int newId = productDAO.addProduct(p);

            setStatus("Product added successfully!");
            clearFields();
            loadMyProducts();

        } catch (NumberFormatException e) {
            setStatus("Price and Quantity must be valid numbers.");
        } catch (Exception e) {
            setStatus("Error: " + e.getMessage());
        }
    }
    @FXML
    private void handleUpdateProduct() {
        Product selected = tableProducts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            setStatus("Select a product first.");
            return;
        }

        try {
            String name = txtName.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());
            int qty = Integer.parseInt(txtQuantity.getText().trim());

            selected.setName(name);
            selected.setPrice(price);
            selected.setQuantity(qty);

            boolean ok = productDAO.updateProduct(selected);
            if (ok) {
                setStatus("Product updated.");
                clearFields();
                loadMyProducts();
            } else {
                setStatus("Update failed (not your product?).");
            }

        } catch (NumberFormatException nfe) {
            setStatus("Price and Quantity must be numbers.");
        } catch (Exception e) {
            setStatus("Update failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteProduct() {
        Product selected = tableProducts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            setStatus("Select a product first.");
            return;
        }

        try {
            int farmerId = currentFarmerId();
            boolean ok = productDAO.deleteProduct(selected.getId(), farmerId);

            if (ok) {
                setStatus("Product deleted.");
                clearFields();
                loadMyProducts();
            } else {
                setStatus("Delete failed (not your product?).");
            }

        } catch (Exception e) {
            setStatus("Delete failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefreshProducts() {
        loadMyProducts();
    }

    private void clearFields() {
        txtName.clear();
        txtPrice.clear();
        txtQuantity.clear();
        tableProducts.getSelectionModel().clearSelection();
    }

    // ---------- PROFILE LOGIC (CONNECTED TO DAO) ----------

    private void loadProfileFromSession() {
        if (Session.getCurrentUser() instanceof FarmerUser f) {
            txtProfileName.setText(f.getName() == null ? "" : f.getName());
            txtProfilePhone.setText(f.getPhone() == null ? "" : f.getPhone());
            txtProfileLocation.setText(f.getLocation() == null ? "" : f.getLocation());
        }
    }
    /**
     * Handles updating the logged-in farmer's profile information.
     * - Validates that a farmer is currently logged in
     * - Reads and validates input fields from the profile form
     * - Updates the farmer object in the session
     * - Persists changes using the DAO layer
     * - Displays success or error feedback to the user
     */

    @FXML
    private void handleUpdateProfile() {
        if (!(Session.getCurrentUser() instanceof FarmerUser f)) {
            new Alert(Alert.AlertType.ERROR, "No farmer logged in.").showAndWait();
            return;
        }



        String name = txtProfileName.getText().trim();
        String phone = txtProfilePhone.getText().trim();
        String location = txtProfileLocation.getText().trim();

        if (name.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Name is required.").showAndWait();
            return;
        }
        if (name.length() < 3) {
            new Alert(Alert.AlertType.WARNING, "Name must be at least 3 characters.").showAndWait();
            return;
        }

        // Update object in session
        f.setName(name);
        f.setPhone(phone);
        f.setLocation(location);

        boolean ok = farmerDAO.updateProfile(f);

        if (ok) {
            new Alert(Alert.AlertType.INFORMATION, "Profile updated successfully!").showAndWait();
        } else {
            new Alert(Alert.AlertType.ERROR, "Update failed.").showAndWait();
        }
    }
    // --- Navigation methods your FXML calls ---
    @FXML
    private void goToProducts() {
        MainSceneController.getInstance().switchView("farmer_product.fxml");
    }

    @FXML
    private void goToProfile() {
        MainSceneController.getInstance().switchView("farmer_profile.fxml");
    }

    @FXML
    private void goBackToDashboard() {
        MainSceneController.getInstance().switchView("farmer_dashboard.fxml");
    }

    @FXML
    private void logout() {
        Session.clear();
        MainSceneController.getInstance().switchView("login.fxml");
    }
}