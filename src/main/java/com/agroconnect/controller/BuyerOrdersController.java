package com.agroconnect.controller;

import com.agroconnect.dao.OrderDAO;
import com.agroconnect.model.BuyerUser;
import com.agroconnect.model.Order;
import com.agroconnect.util.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BuyerOrdersController {

    @FXML private Label lblStatus;

    @FXML private TableView<Order> tableOrders;
    @FXML private TableColumn<Order, Integer> colOrderId;
    @FXML private TableColumn<Order, Integer> colProductId;
    @FXML private TableColumn<Order, Integer> colQuantity;
    @FXML private TableColumn<Order, String> colDate;

    private final OrderDAO orderDAO = new OrderDAO();
    private final ObservableList<Order> orders = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        tableOrders.setItems(orders);
        loadOrders();
    }

    private int currentBuyerId() {
        if (Session.getCurrentUser() instanceof BuyerUser b) return b.getId();
        return 0;
    }
    private void loadOrders() {
        try {
            int buyerId = currentBuyerId();
            if (buyerId == 0) {
                lblStatus.setText("No buyer logged in.");
                return;
            }

            orders.setAll(orderDAO.getOrdersByBuyer(buyerId));
            lblStatus.setText("Loaded " + orders.size() + " orders.");

        } catch (Exception e) {
            lblStatus.setText("Failed to load orders: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefreshOrders() {
        loadOrders();
    }

    @FXML
    private void goBackToDashboard() {
        MainSceneController.getInstance().switchView("buyer_dashboard.fxml");
    }
}