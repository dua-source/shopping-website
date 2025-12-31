package com.shopping.controller;

import com.shopping.model.User;
import com.shopping.service.ProductService;
import com.shopping.service.impl.ProductServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteProductServlet extends HttpServlet {
    
    private ProductService productService = new ProductServiceImpl();
    
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
        
        // 获取商品ID
        String idStr = req.getParameter("id");
        
        if (idStr == null || idStr.isEmpty()) {
            // 如果没有商品ID，重定向到管理员商品列表页面
            resp.sendRedirect(req.getContextPath() + "/admin/productList");
            return;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            
            // 删除商品
            productService.deleteProduct(id);
            
            // 重定向到管理员商品列表页面
            resp.sendRedirect(req.getContextPath() + "/admin/productList");
        } catch (NumberFormatException e) {
            // 如果ID格式错误，重定向到管理员商品列表页面
            resp.sendRedirect(req.getContextPath() + "/admin/productList");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}


