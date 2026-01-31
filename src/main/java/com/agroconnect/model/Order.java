package com.agroconnect.model;

public class Order {
    private int id;
    private int buyerId;
    private int productId;
    private int quantity;

    public Order(int id, int buyerId, int productId, int quantity) {
        this.id = id;
        this.buyerId = buyerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Order(int buyerId, int productId, int quantity) {
        this(0, buyerId, productId, quantity);
    }

    public int getId() { return id; }
    public int getBuyerId() { return buyerId; }
    public int getProductId() { return productId; }
    public int getQuantity() { return quantity; }
}