package com.agroconnect;

import com.agroconnect.dao.FarmerDAO;
import com.agroconnect.model.FarmerUser;
import com.agroconnect.util.DBInit;

public class TestFarmerDAO {
    public static void main(String[] args) {
        DBInit.createTables();

        FarmerDAO dao = new FarmerDAO();

        FarmerUser f = new FarmerUser("Test Farmer", "test@mail.com", "1234", "0912345678", "Addis");
        System.out.println("Register: " + dao.registerFarmer(f));

        FarmerUser logged = dao.login("test@mail.com", "1234");
        System.out.println("Login result: " + (logged != null ? logged.getName() : "FAILED"));
    }
}