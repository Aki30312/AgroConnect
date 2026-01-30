package com.agroconnect.dao;

import com.agroconnect.model.FarmerUser;
import com.agroconnect.util.DBConnection;

import java.sql.*;

public class FarmerDAO {

    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT 1 FROM farmers WHERE email = ? LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    // returns generated farmer id
    public int registerFarmer(FarmerUser farmer) throws SQLException {
        String sql = "INSERT INTO farmers(name, email, password, phone, location) VALUES(?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, farmer.getName());
            ps.setString(2, farmer.getEmail());
            ps.setString(3, farmer.getPassword());
            ps.setString(4, farmer.getPhone());
            ps.setString(5, farmer.getLocation());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return 0;
    }

    public FarmerUser login(String email, String password) throws SQLException {
        String sql = "SELECT * FROM farmers WHERE email = ? AND password = ? LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                return new FarmerUser(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("location")
                );
            }
        }
    }

    public FarmerUser findById(int id) throws SQLException {
        String sql = "SELECT * FROM farmers WHERE id = ? LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                return new FarmerUser(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("location")
                );
            }
        }
    }

    public boolean updateProfile(int farmerId, String name, String phone, String location) throws SQLException {
        String sql = "UPDATE farmers SET name = ?, phone = ?, location = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, location);
            ps.setInt(4, farmerId);

            return ps.executeUpdate() == 1;
        }
    }
}