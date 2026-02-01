package com.agroconnect.dao;

import com.agroconnect.model.Order;
import com.agroconnect.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    // 🔥 ADD THIS METHOD
    public List<Order> getOrdersByBuyer(int buyerId) throws SQLException {
        String sql = "SELECT id, buyer_id, product_id, quantity, order_date FROM orders WHERE buyer_id = ? ORDER BY id DESC";
        List<Order> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, buyerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Order(
                            rs.getInt("id"),
                            rs.getInt("buyer_id"),
                            rs.getInt("product_id"),
                            rs.getInt("quantity"),
                            rs.getString("order_date")
                    ));
                }
            }
        }
        return list;
    }
}