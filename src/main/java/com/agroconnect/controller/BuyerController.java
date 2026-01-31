package com.agroconnect.controller;

import com.agroconnect.dao.OrderDAO;
import com.agroconnect.dao.ProductDAO;
import com.agroconnect.model.BuyerUser;
import com.agroconnect.model.Product;
import com.agroconnect.util.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class BuyerController {

    @FXML private Label lblStatus;
    @FXML private TextField txtOrderQty;

    @FXML private TableView<Product> tableProducts;
    @FXML private TableColumn<Product, Integer> colId;
    @FXML private TableColumn<Product, String> colName;
    @FXML private TableColumn<Product, Double> colPrice;
    @FXML private TableColumn<Product, Integer> colQuantity;

    private final ProductDAO productDAO = new ProductDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // If this controller is ever reused on another screen, avoid NPE
        if (tableProducts == null) return;

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tableProducts.setItems(products);
        loadProducts();
    }

    private int currentBuyerId() {
        if (Session.getCurrentUser() instanceof BuyerUser b) return b.getId();
        return 0;
    }

    private void loadProducts() {
        try {
            products.setAll(productDAO.getAllProducts());
            lblStatus.setText("Loaded " + products.size() + " products.");
        } catch (Exception e) {
            lblStatus.setText("Failed to load products: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefreshProducts() {
        loadProducts();
    }

    @FXML
    private void handlePlaceOrder() {
        Product selected = tableProducts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("Select a product first.");
            return;
        }

        int buyerId = currentBuyerId();
        if (buyerId == 0) {
            lblStatus.setText("No buyer logged in.");
            return;
        }

        try {
            int qty = Integer.parseInt(txtOrderQty.getText().trim());
            if (qty <= 0) {
                lblStatus.setText("Quantity must be greater than 0.");
                return;
            }

            // ✅ Stock validation
            if (qty > selected.getQuantity()) {
                lblStatus.setText("Not enough stock. Available: " + selected.getQuantity());
                return;
            }

            // 1) Place order
            orderDAO.placeOrder(buyerId, selected.getId(), qty);

            // 2) Reduce stock (recommended)
            int newQty = selected.getQuantity() - qty;
            productDAO.updateQuantity(selected.getId(), newQty);

            lblStatus.setText("Order placed successfully!");
            txtOrderQty.clear();
            loadProducts(); // refresh quantities on screen

        } catch (NumberFormatException nfe) {
            lblStatus.setText("Enter a valid number for quantity.");
        } catch (Exception e) {
            lblStatus.setText("Order failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void goBackToDashboard() {
        MainSceneController.getInstance().switchView("buyer_dashboard.fxml");
    }
}