package com.shopping.service;

import com.shopping.model.Order;
import com.shopping.model.OrderItem;

import java.util.List;

public interface OrderService {
    // 创建订单
    boolean createOrder(Order order);
    
    // 根据ID查询订单
    Order getOrderById(int id);
    
    // 根据用户ID查询订单列表
    List<Order> getOrdersByUserId(int userId);
    
    // 获取所有订单
    List<Order> getAllOrders();
    
    // 更新订单状态
    boolean updateOrderStatus(int orderId, String status);
    
    // 根据订单ID查询订单项
    List<OrderItem> getOrderItemsByOrderId(int orderId);
}

