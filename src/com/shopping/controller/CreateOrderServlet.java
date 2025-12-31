package com.shopping.controller;

import com.shopping.model.User;
import com.shopping.model.Order;
import com.shopping.model.OrderItem;
import com.shopping.model.ShoppingCart;
import com.shopping.service.OrderService;
import com.shopping.service.ShoppingCartService;
import com.shopping.service.impl.OrderServiceImpl;
import com.shopping.service.impl.ShoppingCartServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateOrderServlet extends HttpServlet {
    
    private OrderService orderService = new OrderServiceImpl();
    private ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取session中的用户信息
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            // 如果用户未登录，重定向到登录页面
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        // 获取请求参数
        String shippingAddress = req.getParameter("shippingAddress");
        String paymentMethod = req.getParameter("paymentMethod");
        
        if (shippingAddress == null || shippingAddress.isEmpty() || paymentMethod == null || paymentMethod.isEmpty()) {
            // 参数不完整，重定向到购物车列表页面
            resp.sendRedirect(req.getContextPath() + "/cartList");
            return;
        }
        
        // 获取用户的购物车项
        List<ShoppingCart> cartItems = shoppingCartService.getCartByUserId(user.getId());
        
        if (cartItems == null || cartItems.isEmpty()) {
            // 购物车为空，重定向到商品列表页面
            resp.sendRedirect(req.getContextPath() + "/productList");
            return;
        }
        
        // 计算订单总金额
        double totalAmount = 0;
        for (ShoppingCart cartItem : cartItems) {
            totalAmount += cartItem.getSubtotal();
        }
        
        // 创建订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setOrderDate(new Date());
        order.setTotalAmount(totalAmount);
        order.setStatus("pending");
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(paymentMethod);
        
        // 创建订单项
        List<OrderItem> orderItems = new ArrayList<>();
        for (ShoppingCart cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setProductName(cartItem.getProductName());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getPrice());
            orderItems.add(orderItem);
        }
        
        order.setOrderItems(orderItems);
        
        // 保存订单
        boolean success = orderService.createOrder(order);
        
        if (success) {
            // 订单创建成功，重定向到订单列表页面
            resp.sendRedirect(req.getContextPath() + "/orderList");
        } else {
            // 订单创建失败，重定向到购物车列表页面
            resp.sendRedirect(req.getContextPath() + "/cartList");
        }
    }
}


