package com.shopping.controller;

import com.shopping.model.User;
import com.shopping.service.UserService;
import com.shopping.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    
    private UserService userService = new UserServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 转发到注册页面
        req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取注册信息
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        
        // 验证确认密码
        if (!password.equals(confirmPassword)) {
            req.setAttribute("error", "两次输入的密码不一致");
            req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
            return;
        }
        
        // 验证密码强度
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (!password.matches(passwordRegex)) {
            req.setAttribute("error", "密码强度不符合要求：至少8个字符，包含大小写字母、数字和特殊字符");
            req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
            return;
        }
        
        // 创建用户对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        
        // 调用service层进行注册
        boolean success = userService.register(user);
        
        if (success) {
            // 注册成功，重定向到登录页面
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            // 注册失败，返回注册页面并显示错误信息
            req.setAttribute("error", "用户名或邮箱已存在");
            req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
        }
    }
}

