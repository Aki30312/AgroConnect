package com.agroconnect.model;

public class Product {
    private int id;
    private int farmerId;
    private String name;
    private double price;
    private int quantity;

    public Product(int id, int farmerId, String name, double price, int quantity) {
        this.id = id;
        this.farmerId = farmerId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(int farmerId, String name, double price, int quantity) {
        this(0, farmerId, name, price, quantity);
    }

    public int getId() { return id; }
    public int getFarmerId() { return farmerId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}