package com.shopping.controller;

import com.shopping.model.User;
import com.shopping.model.Order;
import com.shopping.service.OrderService;
import com.shopping.service.impl.OrderServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class OrderListServlet extends HttpServlet {
    
    private OrderService orderService = new OrderServiceImpl();
    
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
        
        // 获取当前用户的订单列表
        List<Order> orders = orderService.getOrdersByUserId(user.getId());
        
        // 将订单列表设置到request中
        req.setAttribute("orders", orders);
        
        // 转发到订单列表页面
        req.getRequestDispatcher("/jsp/orderList.jsp").forward(req, resp);
    }
}


