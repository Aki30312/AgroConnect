package com.agroconnect.controller;

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
    private final ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // This controller is used by multiple farmer fxml screens.
        // Only set up table if we are on the products screen.
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

    private void loadMyProducts() {
        try {
            int farmerId = currentFarmerId();
            if (farmerId == 0) {
                lblStatus.setText("No farmer logged in.");
                return;
            }
            products.setAll(productDAO.getProductsByFarmer(farmerId));
            lblStatus.setText("Loaded " + products.size() + " products.");
        } catch (Exception e) {
            lblStatus.setText("Load failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddProduct() {
        try {
            int farmerId = currentFarmerId();
            if (farmerId == 0) {
                lblStatus.setText("No farmer logged in.");
                return;
            }

            String name = txtName.getText().trim();
            if (name.isEmpty()) {
                lblStatus.setText("Product name is required.");
                return;
            }

            double price = Double.parseDouble(txtPrice.getText().trim());
            int qty = Integer.parseInt(txtQuantity.getText().trim());

            Product p = new Product(0, farmerId, name, price, qty);
            int newId = productDAO.addProduct(p);

            if (newId > 0) {
                lblStatus.setText("Product added! ID: " + newId);
                clearFields();
                loadMyProducts();
            } else {
                lblStatus.setText("Add failed.");
            }

        } catch (NumberFormatException nfe) {
            lblStatus.setText("Price and Quantity must be numbers.");
        } catch (Exception e) {
            lblStatus.setText("Add failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    private void handleUpdateProduct() {
        Product selected = tableProducts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("Select a product first.");
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
                lblStatus.setText("Product updated.");
                clearFields();
                loadMyProducts();
            } else {
                lblStatus.setText("Update failed (not your product?).");
            }

        } catch (NumberFormatException nfe) {
            lblStatus.setText("Price and Quantity must be numbers.");
        } catch (Exception e) {
            lblStatus.setText("Update failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteProduct() {
        Product selected = tableProducts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("Select a product first.");
            return;
        }

        try {
            int farmerId = currentFarmerId();
            boolean ok = productDAO.deleteProduct(selected.getId(), farmerId);

            if (ok) {
                lblStatus.setText("Product deleted.");
                clearFields();
                loadMyProducts();
            } else {
                lblStatus.setText("Delete failed (not your product?).");
            }

        } catch (Exception e) {
            lblStatus.setText("Delete failed: " + e.getMessage());
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
    private void handleUpdateProfile() {
        // For now: just show success. DAO connection later.
        // Works even if lblStatus belongs to a different screen.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Profile");
        alert.setHeaderText(null);

        String name = (txtProfileName != null) ? txtProfileName.getText().trim() : "";
        String phone = (txtProfilePhone != null) ? txtProfilePhone.getText().trim() : "";
        String location = (txtProfileLocation != null) ? txtProfileLocation.getText().trim() : "";

        alert.setContentText(
                "Update Profile clicked.\n" +
                        "Name: " + name + "\n" +
                        "Phone: " + phone + "\n" +
                        "Location: " + location + "\n\n" +
                        "(We will connect DAO later.)"
        );
        alert.showAndWait();
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