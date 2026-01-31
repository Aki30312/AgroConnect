package com.agroconnect.dao;

import com.agroconnect.model.Product;
import com.agroconnect.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public int addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products(farmer_id, name, price, quantity) VALUES(?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, product.getFarmerId());
            ps.setString(2, product.getName());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return 0;
    }

    public List<Product> getProductsByFarmer(int farmerId) throws SQLException {
        String sql = "SELECT * FROM products WHERE farmer_id = ? ORDER BY id DESC";
        List<Product> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, farmerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                            rs.getInt("id"),
                            rs.getInt("farmer_id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getInt("quantity")
                    ));
                }
            }
        }
        return list;
    }

    // ✅ NEW: buyers need all products
    public List<Product> getAllProducts() throws SQLException {
        String sql = "SELECT * FROM products ORDER BY id DESC";
        List<Product> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getInt("farmer_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                ));
            }
        }
        return list;
    }

    // ✅ NEW: get a single product (useful for order validation)
    public Product getById(int productId) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ? LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                return new Product(
                        rs.getInt("id"),
                        rs.getInt("farmer_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
            }
        }
    }

    // ✅ NEW: reduce/update stock (optional but recommended)
    public boolean updateQuantity(int productId, int newQuantity) throws SQLException {
        String sql = "UPDATE products SET quantity = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newQuantity);
            ps.setInt(2, productId);

            return ps.executeUpdate() == 1;
        }
    }

    public boolean updateProduct(Product product) throws SQLException {
        String sql = """
            UPDATE products
            SET name = ?, price = ?, quantity = ?
            WHERE id = ? AND farmer_id = ?
        """;try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getQuantity());
            ps.setInt(4, product.getId());
            ps.setInt(5, product.getFarmerId());

            return ps.executeUpdate() == 1;
        }
    }

    public boolean deleteProduct(int productId, int farmerId) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ? AND farmer_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ps.setInt(2, farmerId);

            return ps.executeUpdate() == 1;
        }
    }
}