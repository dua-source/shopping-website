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
import java.util.List;

public class CartListServlet extends HttpServlet {
    
    private ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取session中的用户信息
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            // 如果用户未登录，重定向到登录页面
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        // 获取用户的购物车项
        List<ShoppingCart> cartItems = shoppingCartService.getCartByUserId(user.getId());
        
        // 计算购物车总价格
        double totalPrice = 0;
        for (ShoppingCart cartItem : cartItems) {
            totalPrice += cartItem.getSubtotal();
        }
        
        // 将购物车项和总价格设置到request中
        req.setAttribute("cartItems", cartItems);
        req.setAttribute("totalPrice", totalPrice);
        
        // 转发到购物车列表页面
        req.getRequestDispatcher("/jsp/cartList.jsp").forward(req, resp);
    }
}


