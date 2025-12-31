package com.shopping.controller;

import com.shopping.model.User;
import com.shopping.service.UserService;
import com.shopping.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    
    private UserService userService = new UserServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 转发到登录页面
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取登录信息
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        // 调用service层进行登录验证
        User user = userService.login(username, password);
        
        if (user != null) {
            // 登录成功，将用户信息存储到session中
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            
            // 根据用户角色重定向到不同页面
            if ("admin".equals(user.getRole())) {
                // 管理员重定向到后台管理页面
                resp.sendRedirect(req.getContextPath() + "/admin/productList");
            } else {
                // 普通用户重定向到首页
                resp.sendRedirect(req.getContextPath() + "/productList");
            }
        } else {
            // 登录失败，返回登录页面并显示错误信息
            req.setAttribute("error", "用户名或密码错误");
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
        }
    }
}

