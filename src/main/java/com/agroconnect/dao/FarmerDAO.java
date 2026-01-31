package com.agroconnect.dao;

import com.agroconnect.model.FarmerUser;
import com.agroconnect.util.DBConnection;

import java.sql.*;

public class FarmerDAO {

    // Register farmer (INSERT)
    public boolean registerFarmer(FarmerUser farmer) {
        String sql = "INSERT INTO farmers(name, email, password, phone, location) VALUES(?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, farmer.getName());
            ps.setString(2, farmer.getEmail());
            ps.setString(3, farmer.getPassword());
            ps.setString(4, farmer.getPhone());
            ps.setString(5, farmer.getLocation());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Register farmer failed: " + e.getMessage());
            return false;
        }
    }

    // Login farmer (SELECT)
    public FarmerUser login(String email, String password) {
        String sql = "SELECT * FROM farmers WHERE email=? AND password=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new FarmerUser(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("location")
                );
            }

        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }

        return null;
    }

    // Update profile (UPDATE)
    public boolean updateProfile(FarmerUser farmer) {
        String sql = "UPDATE farmers SET name=?, phone=?, location=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, farmer.getName());
            ps.setString(2, farmer.getPhone());
            ps.setString(3, farmer.getLocation());
            ps.setInt(4, farmer.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Update profile failed: " + e.getMessage());
            return false;
        }
    }
}