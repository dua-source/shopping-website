package com.shopping.dao.impl;

import com.shopping.dao.ProductDAO;
import com.shopping.model.Product;
import com.shopping.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    
    @Override
    public boolean addProduct(Product product) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO products (name, description, price, stock, category, image) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getStock());
            pstmt.setString(5, product.getCategory());
            pstmt.setString(6, product.getImage());
            
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }
    
    @Override
    public boolean updateProduct(Product product) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, stock = ?, category = ?, image = ? WHERE id = ?";
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getStock());
            pstmt.setString(5, product.getCategory());
            pstmt.setString(6, product.getImage());
            pstmt.setInt(7, product.getId());
            
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }
    
    @Override
    public boolean deleteProduct(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM products WHERE id = ?";
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }
    
    @Override
    public Product getProductById(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM products WHERE id = ?";
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setCategory(rs.getString("category"));
                product.setImage(rs.getString("image"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }
    
    @Override
    public List<Product> getAllProducts() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM products ORDER BY id DESC";
        List<Product> products = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setCategory(rs.getString("category"));
                product.setImage(rs.getString("image"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return products;
    }
    
    @Override
    public List<Product> getProductsByCategory(String category) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM products WHERE category = ? ORDER BY id DESC";
        List<Product> products = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, category);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setCategory(rs.getString("category"));
                product.setImage(rs.getString("image"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return products;
    }
    
    @Override
    public List<Product> searchProducts(String keyword) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM products WHERE name LIKE ? OR description LIKE ? ORDER BY id DESC";
        List<Product> products = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setCategory(rs.getString("category"));
                product.setImage(rs.getString("image"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return products;
    }
    
    @Override
    public boolean updateProductStock(int productId, int quantity) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, quantity);
            
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }
    
    @Override
    public List<String> getAllCategories() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT DISTINCT category FROM products ORDER BY category";
        List<String> categories = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return categories;
    }
}

