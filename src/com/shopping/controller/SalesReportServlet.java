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

public class SalesReportServlet extends HttpServlet {
    
    private OrderService orderService = new OrderServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取session中的用户信息
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || !"admin".equals(user.getRole())) {
            // 如果用户未登录或不是管理员，重定向到登录页面
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        // 获取所有订单
        List<Order> orders = orderService.getAllOrders();
        
        // 计算销售统计数据
        double totalSales = 0;
        int totalOrders = orders.size();
        int shippedOrders = 0;
        int deliveredOrders = 0;
        
        for (Order order : orders) {
            totalSales += order.getTotalAmount();
            if ("shipped".equals(order.getStatus())) {
                shippedOrders++;
            } else if ("delivered".equals(order.getStatus())) {
                deliveredOrders++;
            }
        }
        
        // 将统计数据设置到request中
        req.setAttribute("totalSales", totalSales);
        req.setAttribute("totalOrders", totalOrders);
        req.setAttribute("shippedOrders", shippedOrders);
        req.setAttribute("deliveredOrders", deliveredOrders);
        req.setAttribute("orders", orders);
        
        // 转发到销售报表页面
        req.getRequestDispatcher("/jsp/admin/salesReport.jsp").forward(req, resp);
    }
}


