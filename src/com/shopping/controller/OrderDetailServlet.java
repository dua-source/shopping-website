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

public class OrderDetailServlet extends HttpServlet {
    
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
        
        // 获取订单ID
        String orderIdStr = req.getParameter("orderId");
        
        if (orderIdStr == null || orderIdStr.isEmpty()) {
            // 参数不完整，重定向到订单列表页面
            resp.sendRedirect(req.getContextPath() + "/orderList");
            return;
        }
        
        try {
            int orderId = Integer.parseInt(orderIdStr);
            
            // 获取订单详情
            Order order = orderService.getOrderById(orderId);
            
            if (order == null || order.getUserId() != user.getId() && !"admin".equals(user.getRole())) {
                // 订单不存在或无权限访问，重定向到订单列表页面
                resp.sendRedirect(req.getContextPath() + "/orderList");
                return;
            }
            
            // 将订单详情设置到request中
            req.setAttribute("order", order);
            
            // 转发到订单详情页面
            req.getRequestDispatcher("/jsp/orderDetail.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            // 订单ID格式错误，重定向到订单列表页面
            resp.sendRedirect(req.getContextPath() + "/orderList");
        }
    }
}


