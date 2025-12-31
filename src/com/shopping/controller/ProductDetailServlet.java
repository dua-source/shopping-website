package com.shopping.controller;

import com.shopping.model.Product;
import com.shopping.service.ProductService;
import com.shopping.service.impl.ProductServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailServlet extends HttpServlet {
    
    private ProductService productService = new ProductServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取商品ID
        String idStr = req.getParameter("id");
        
        if (idStr == null || idStr.isEmpty()) {
            // 如果商品ID为空，重定向到商品列表页面
            resp.sendRedirect(req.getContextPath() + "/productList");
            return;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            // 根据ID获取商品信息
            Product product = productService.getProductById(id);
            
            if (product == null) {
                // 如果商品不存在，重定向到商品列表页面
                resp.sendRedirect(req.getContextPath() + "/productList");
                return;
            }
            
            // 将商品信息设置到request中
            req.setAttribute("product", product);
            
            // 转发到商品详情页面
            req.getRequestDispatcher("/jsp/productDetail.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            // 如果商品ID格式错误，重定向到商品列表页面
            resp.sendRedirect(req.getContextPath() + "/productList");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}


