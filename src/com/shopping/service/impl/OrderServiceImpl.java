package com.shopping.service.impl;

import com.shopping.dao.OrderDAO;
import com.shopping.dao.ProductDAO;
import com.shopping.dao.ShoppingCartDAO;
import com.shopping.dao.UserDAO;
import com.shopping.dao.impl.OrderDAOImpl;
import com.shopping.dao.impl.ProductDAOImpl;
import com.shopping.dao.impl.ShoppingCartDAOImpl;
import com.shopping.dao.impl.UserDAOImpl;
import com.shopping.model.Order;
import com.shopping.model.OrderItem;
import com.shopping.model.ShoppingCart;
import com.shopping.model.User;
import com.shopping.service.OrderService;
import com.shopping.service.ProductService;
import com.shopping.util.EmailUtil;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    
    private OrderDAO orderDAO = new OrderDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();
    private ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAOImpl();
    private UserDAO userDAO = new UserDAOImpl();
    
    @Override
    public boolean createOrder(Order order) {
        // 1. 创建订单
        boolean success = orderDAO.createOrder(order);
        if (!success) {
            return false;
        }
        
        // 2. 添加订单项
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(order.getId());
            success = orderDAO.addOrderItem(orderItem);
            if (!success) {
                return false;
            }
            
            // 3. 更新产品库存
            success = productDAO.updateProductStock(orderItem.getProductId(), orderItem.getQuantity());
            if (!success) {
                return false;
            }
        }
        
        // 4. 清空购物车
        success = shoppingCartDAO.clearCart(order.getUserId());
        if (!success) {
            return false;
        }
        
        // 5. 发送订单确认邮件
        User user = userDAO.getUserById(order.getUserId());
        if (user != null && user.getEmail() != null) {
            String subject = "订单确认 - 订单号：" + order.getId();
            StringBuilder body = new StringBuilder();
            body.append("尊敬的用户 ").append(user.getUsername()).append("，您好！\n\n");
            body.append("您的订单已成功创建，订单详情如下：\n\n");
            body.append("订单号：").append(order.getId()).append("\n");
            body.append("订单日期：").append(order.getOrderDate()).append("\n");
            body.append("总金额：").append(order.getTotalAmount()).append("元\n");
            body.append("订单状态：").append(order.getStatus()).append("\n");
            body.append("收货地址：").append(order.getShippingAddress()).append("\n");
            body.append("支付方式：").append(order.getPaymentMethod()).append("\n\n");
            body.append("订单项：\n");
            for (OrderItem item : orderItems) {
                body.append("- ").append(item.getProductName()).append("，数量：").append(item.getQuantity()).append("，单价：").append(item.getUnitPrice()).append("元\n");
            }
            body.append("\n感谢您的购买！我们将尽快处理您的订单。\n");
            body.append("\n如有疑问，请联系客服。\n");
            EmailUtil.sendEmail(user.getEmail(), subject, body.toString());
        }
        
        return true;
    }
    
    @Override
    public Order getOrderById(int id) {
        return orderDAO.getOrderById(id);
    }
    
    @Override
    public List<Order> getOrdersByUserId(int userId) {
        return orderDAO.getOrdersByUserId(userId);
    }
    
    @Override
    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }
    
    @Override
    public boolean updateOrderStatus(int orderId, String status) {
        boolean success = orderDAO.updateOrderStatus(orderId, status);
        
        if (success) {
            // 获取订单信息
            Order order = orderDAO.getOrderById(orderId);
            if (order != null) {
                // 获取用户信息
                User user = userDAO.getUserById(order.getUserId());
                if (user != null && user.getEmail() != null) {
                    // 获取订单项
                    List<OrderItem> orderItems = orderDAO.getOrderItemsByOrderId(orderId);
                    
                    // 发送状态更新邮件
                    String subject = "订单状态更新- 订单号：" + orderId;
                    StringBuilder body = new StringBuilder();
                    body.append("尊敬的用户 ").append(user.getUsername()).append("，您好！\n\n");
                    body.append("您的订单状态已更新，订单详情如下：\n\n");
                    body.append("订单号：").append(order.getId()).append("\n");
                    body.append("订单日期：").append(order.getOrderDate()).append("\n");
                    body.append("总金额：").append(order.getTotalAmount()).append("元\n");
                    body.append("当前状态：").append(status).append("\n");
                    body.append("收货地址：").append(order.getShippingAddress()).append("\n");
                    body.append("支付方式：").append(order.getPaymentMethod()).append("\n\n");
                    body.append("订单项：\n");
                    for (OrderItem item : orderItems) {
                        body.append("- ").append(item.getProductName()).append("，数量：").append(item.getQuantity()).append("，单价：").append(item.getUnitPrice()).append("元\n");
                    }
                    body.append("\n感谢您的购买！\n");
                    body.append("\n如有疑问，请联系客服。\n");
                    EmailUtil.sendEmail(user.getEmail(), subject, body.toString());
                }
            }
        }
        
        return success;
    }
    
    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        return orderDAO.getOrderItemsByOrderId(orderId);
    }
}

