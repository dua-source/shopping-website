package com.shopping.dao.impl;

import com.shopping.dao.ShoppingCartDAO;
import com.shopping.model.ShoppingCart;
import com.shopping.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDAOImpl implements ShoppingCartDAO {
    
    @Override
    public boolean addToCart(ShoppingCart cartItem) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        // 先检查购物车中是否已存在该商品
        ShoppingCart existingItem = getCartItemByUserIdAndProductId(cartItem.getUserId(), cartItem.getProductId());
        
        if (existingItem != null) {
            // 如果已存在，则更新数量
            String updateSql = "UPDATE shopping_cart SET quantity = quantity + ? WHERE id = ?";
            try {
                conn = DBUtil.getConnection();
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setInt(1, cartItem.getQuantity());
                pstmt.setInt(2, existingItem.getId());
                
                int rows = pstmt.executeUpdate();
                return rows > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                DBUtil.close(conn, pstmt);
            }
        } else {
            // 如果不存在，则添加新记录
            String insertSql = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
            try {
                conn = DBUtil.getConnection();
                pstmt = conn.prepareStatement(insertSql);
                pstmt.setInt(1, cartItem.getUserId());
                pstmt.setInt(2, cartItem.getProductId());
                pstmt.setInt(3, cartItem.getQuantity());
                
                int rows = pstmt.executeUpdate();
                return rows > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                DBUtil.close(conn, pstmt);
            }
        }
    }
    
    @Override
    public boolean updateCartItemQuantity(int cartId, int quantity) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE shopping_cart SET quantity = ? WHERE id = ?";
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, cartId);
            
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
    public boolean removeFromCart(int cartId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM shopping_cart WHERE id = ?";
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cartId);
            
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
    public ShoppingCart getCartItemByUserIdAndProductId(int userId, int productId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM shopping_cart WHERE user_id = ? AND product_id = ?";
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                ShoppingCart cartItem = new ShoppingCart();
                cartItem.setId(rs.getInt("id"));
                cartItem.setUserId(rs.getInt("user_id"));
                cartItem.setProductId(rs.getInt("product_id"));
                cartItem.setQuantity(rs.getInt("quantity"));
                return cartItem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }
    
    @Override
    public List<ShoppingCart> getCartByUserId(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT sc.*, p.name, p.price, p.image FROM shopping_cart sc JOIN products p ON sc.product_id = p.id WHERE sc.user_id = ?";
        List<ShoppingCart> cartItems = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ShoppingCart cartItem = new ShoppingCart();
                cartItem.setId(rs.getInt("id"));
                cartItem.setUserId(rs.getInt("user_id"));
                cartItem.setProductId(rs.getInt("product_id"));
                cartItem.setProductName(rs.getString("name"));
                cartItem.setPrice(rs.getDouble("price"));
                cartItem.setQuantity(rs.getInt("quantity"));
                cartItem.setImage(rs.getString("image"));
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return cartItems;
    }
    
    @Override
    public boolean clearCart(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM shopping_cart WHERE user_id = ?";
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }
}

