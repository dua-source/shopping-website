package com.shopping.controller;

import com.shopping.service.ShoppingCartService;
import com.shopping.service.impl.ShoppingCartServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateCartServlet extends HttpServlet {
    
    private ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求参数
        String cartIdStr = req.getParameter("cartId");
        String quantityStr = req.getParameter("quantity");
        
        if (cartIdStr == null || cartIdStr.isEmpty() || quantityStr == null || quantityStr.isEmpty()) {
            // 参数不完整，重定向到购物车列表
            resp.sendRedirect(req.getContextPath() + "/cartList");
            return;
        }
        
        try {
            int cartId = Integer.parseInt(cartIdStr);
            int quantity = Integer.parseInt(quantityStr);
            
            if (quantity <= 0) {
                quantity = 1;
            }
            
            // 更新购物车项数量
            shoppingCartService.updateCartItemQuantity(cartId, quantity);
            
            // 重定向到购物车列表
            resp.sendRedirect(req.getContextPath() + "/cartList");
        } catch (NumberFormatException e) {
            // 数量格式错误，重定向到购物车列表
            resp.sendRedirect(req.getContextPath() + "/cartList");
        }
    }
}


