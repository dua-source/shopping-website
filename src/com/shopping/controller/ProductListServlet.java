package com.shopping.controller;

import com.shopping.model.Product;
import com.shopping.service.ProductService;
import com.shopping.service.impl.ProductServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductListServlet extends HttpServlet {
    
    private ProductService productService = new ProductServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求参数
        String keyword = req.getParameter("keyword");
        String category = req.getParameter("category");
        
        List<Product> products;
        
        if (keyword != null && !keyword.isEmpty()) {
            // 根据关键词搜索
            products = productService.searchProducts(keyword);
            req.setAttribute("keyword", keyword);
        } else if (category != null && !category.isEmpty()) {
            // 根据分类获取商品
            products = productService.getProductsByCategory(category);
            req.setAttribute("category", category);
        } else {
            // 获取所有商品
            products = productService.getAllProducts();
        }
        
        // 获取所有分类
        List<String> categories = productService.getAllCategories();
        
        // 将商品列表和分类列表设置到request中
        req.setAttribute("products", products);
        req.setAttribute("categories", categories);
        
        // 转发到商品列表页面
        req.getRequestDispatcher("/jsp/productList.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}


