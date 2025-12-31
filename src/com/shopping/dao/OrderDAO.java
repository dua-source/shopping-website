package com.shopping.dao;

import com.shopping.model.Order;
import com.shopping.model.OrderItem;

import java.util.List;

public interface OrderDAO {
    boolean createOrder(Order order);
    boolean addOrderItem(OrderItem orderItem);
    Order getOrderById(int id);
    List<Order> getOrdersByUserId(int userId);
    List<Order> getAllOrders();
    boolean updateOrderStatus(int orderId, String status);
    List<OrderItem> getOrderItemsByOrderId(int orderId);
}