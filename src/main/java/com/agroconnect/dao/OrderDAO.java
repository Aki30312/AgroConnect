package com.agroconnect.dao;

import com.agroconnect.util.DBConnection;
import java.sql.*;

public class OrderDAO {

    public void placeOrder(int buyerId, int productId, int quantity) throws SQLException {
        String sql = "INSERT INTO orders(buyer_id, product_id, quantity) VALUES(?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, buyerId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.executeUpdate();
        }
    }
}