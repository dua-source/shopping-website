package com.shopping.dao.impl;

import com.shopping.dao.OrderDAO;
import com.shopping.model.Order;
import com.shopping.model.OrderItem;
import com.shopping.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    
    @Override
    public boolean createOrder(Order order) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO orders (user_id, order_date, total_amount, status, shipping_address, payment_method) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, order.getUserId());
            pstmt.setTimestamp(2, new java.sql.Timestamp(order.getOrderDate().getTime()));
            pstmt.setDouble(3, order.getTotalAmount());
            pstmt.setString(4, order.getStatus());
            pstmt.setString(5, order.getShippingAddress());
            pstmt.setString(6, order.getPaymentMethod());
            
            int rows = pstmt.executeUpdate();
            
            // 获取生成的订单ID
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                order.setId(rs.getInt(1));
            }
            
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }
    
    @Override
    public boolean addOrderItem(OrderItem orderItem) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderItem.getOrderId());
            pstmt.setInt(2, orderItem.getProductId());
            pstmt.setInt(3, orderItem.getQuantity());
            pstmt.setDouble(4, orderItem.getUnitPrice());
            
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
    public Order getOrderById(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM orders WHERE id = ?";
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setPaymentMethod(rs.getString("payment_method"));
                
                // 获取订单项
                List<OrderItem> orderItems = getOrderItemsByOrderId(id);
                order.setOrderItems(orderItems);
                
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }
    
    @Override
    public List<Order> getOrdersByUserId(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
        List<Order> orders = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setPaymentMethod(rs.getString("payment_method"));
                
                // 获取订单项
                List<OrderItem> orderItems = getOrderItemsByOrderId(order.getId());
                order.setOrderItems(orderItems);
                
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return orders;
    }
    
    @Override
    public List<Order> getAllOrders() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";
        List<Order> orders = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setPaymentMethod(rs.getString("payment_method"));
                
                // 获取订单项
                List<OrderItem> orderItems = getOrderItemsByOrderId(order.getId());
                order.setOrderItems(orderItems);
                
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return orders;
    }
    
    @Override
    public boolean updateOrderStatus(int orderId, String status) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setInt(2, orderId);
            
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
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT oi.*, p.name FROM order_items oi JOIN products p ON oi.product_id = p.id WHERE oi.order_id = ?";
        List<OrderItem> orderItems = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setId(rs.getInt("id"));
                orderItem.setOrderId(rs.getInt("order_id"));
                orderItem.setProductId(rs.getInt("product_id"));
                orderItem.setProductName(rs.getString("name"));
                orderItem.setQuantity(rs.getInt("quantity"));
                orderItem.setUnitPrice(rs.getDouble("unit_price"));
                
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return orderItems;
    }
}

