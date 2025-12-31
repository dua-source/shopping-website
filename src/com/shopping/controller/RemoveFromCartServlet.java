package com.shopping.controller;

import com.shopping.service.ShoppingCartService;
import com.shopping.service.impl.ShoppingCartServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveFromCartServlet extends HttpServlet {
    
    private ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求参数
        String cartIdStr = req.getParameter("cartId");
        
        if (cartIdStr == null || cartIdStr.isEmpty()) {
            // 参数不完整，重定向到购物车列表
            resp.sendRedirect(req.getContextPath() + "/cartList");
            return;
        }
        
        try {
            int cartId = Integer.parseInt(cartIdStr);
            
            // 从购物车中移除商品
            shoppingCartService.removeFromCart(cartId);
            
            // 重定向到购物车列表
            resp.sendRedirect(req.getContextPath() + "/cartList");
        } catch (NumberFormatException e) {
            // 购物车ID格式错误，重定向到购物车列表
            resp.sendRedirect(req.getContextPath() + "/cartList");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}


