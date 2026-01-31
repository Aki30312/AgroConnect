package com.agroconnect.model;

public class BuyerUser extends User {

    private String phone;

    public BuyerUser(int id, String name, String email, String password, String phone) {
        super(id, name, email, password);
        this.phone = phone;
    }

    public BuyerUser(String name, String email, String password, String phone) {
        this(0, name, email, password, phone);
    }

    @Override
    public String getRole() {
        return "BUYER";
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}