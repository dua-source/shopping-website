package com.shopping.controller;

import com.shopping.model.User;
import com.shopping.service.OrderService;
import com.shopping.service.impl.OrderServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateOrderStatusServlet extends HttpServlet {
    
    private OrderService orderService = new OrderServiceImpl();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取session中的用户信息
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || !"admin".equals(user.getRole())) {
            // 如果用户未登录或不是管理员，重定向到登录页面
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        // 获取请求参数
        String orderIdStr = req.getParameter("orderId");
        String status = req.getParameter("status");
        
        if (orderIdStr == null || orderIdStr.isEmpty() || status == null || status.isEmpty()) {
            // 参数不完整，重定向到管理员订单列表页面
            resp.sendRedirect(req.getContextPath() + "/admin/orderList");
            return;
        }
        
        try {
            int orderId = Integer.parseInt(orderIdStr);
            
            // 更新订单状态
            orderService.updateOrderStatus(orderId, status);
            
            // 重定向到管理员订单列表页面
            resp.sendRedirect(req.getContextPath() + "/admin/orderList");
        } catch (NumberFormatException e) {
            // 订单ID格式错误，重定向到管理员订单列表页面
            resp.sendRedirect(req.getContextPath() + "/admin/orderList");
        }
    }
}


