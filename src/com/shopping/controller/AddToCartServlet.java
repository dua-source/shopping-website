package com.shopping.controller;

import com.shopping.model.User;
import com.shopping.model.ShoppingCart;
import com.shopping.service.ShoppingCartService;
import com.shopping.service.impl.ShoppingCartServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AddToCartServlet extends HttpServlet {
    
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
        String productIdStr = req.getParameter("productId");
        String quantityStr = req.getParameter("quantity");
        
        if (productIdStr == null || productIdStr.isEmpty() || quantityStr == null || quantityStr.isEmpty()) {
            // 如果参数不完整，重定向到产品列表页面
            resp.sendRedirect(req.getContextPath() + "/productList");
            return;
        }
        
        try {
            int productId = Integer.parseInt(productIdStr);
            int quantity = Integer.parseInt(quantityStr);
            
            if (quantity <= 0) {
                quantity = 1;
            }
            
            // 创建购物车项
            ShoppingCart cartItem = new ShoppingCart();
            cartItem.setUserId(user.getId());
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);
            
            // 添加到购物车
            boolean success = shoppingCartService.addToCart(cartItem);
            
            // 重定向到购物车页面
            resp.sendRedirect(req.getContextPath() + "/cartList");
        } catch (NumberFormatException e) {
            // 如果参数格式错误，重定向到产品列表页面
            resp.sendRedirect(req.getContextPath() + "/productList");
        }
    }
}

