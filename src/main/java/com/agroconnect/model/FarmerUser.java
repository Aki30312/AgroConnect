package com.agroconnect.model;

public class FarmerUser extends User {

    private String phone;

    public FarmerUser(int id, String name, String password, String email ) {
        super(id, name, email, password);

    }

    public FarmerUser(String name, String username, String password) {
        this(0, name, username, password);
    }

    @Override
    public String getRole() {
        return "FARMER";
    }


    /*public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone(String phone) {
        return phone;
    }*/
}
