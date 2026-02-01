package com.agroconnect.dao;

import com.agroconnect.model.BuyerUser;
import com.agroconnect.util.DBConnection;

import java.sql.*;
/**
 * Data Access Object (DAO) responsible for handling
 * database operations related to Buyer users.
 */

public class BuyerDAO {
/**
 * Registers a new buyer in the database.
 */
    public int registerBuyer(BuyerUser buyer) throws SQLException {
        String sql = "INSERT INTO buyers(name, email, password, phone) VALUES(?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, buyer.getName());
            ps.setString(2, buyer.getEmail());
            ps.setString(3, buyer.getPassword());
            ps.setString(4, buyer.getPhone());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
        }
        return 0;
    }

    public BuyerUser login(String email, String password) throws SQLException {
        String sql = "SELECT * FROM buyers WHERE email=? AND password=? LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;

            return new BuyerUser(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("phone")
            );
        }
    }
}