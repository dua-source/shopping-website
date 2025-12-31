package com.shopping.controller;

import com.shopping.model.User;
import com.shopping.service.UserService;
import com.shopping.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateProfileServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否登录
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        if (currentUser == null) {
            // 用户未登录，重定向到登录页面
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // 将当前用户信息传递给JSP页面
        request.setAttribute("user", currentUser);
        request.getRequestDispatcher("/jsp/user/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求编码为UTF-8，解决中文乱码问题
        request.setCharacterEncoding("UTF-8");
        
        // 获取当前登录用户
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // 获取表单提交的用户信息
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        
        // 更新用户信息
        currentUser.setUsername(username);
        currentUser.setEmail(email);
        currentUser.setPhone(phone);
        currentUser.setAddress(address);
        
        // 调用Service层更新用户信息
        boolean success = userService.updateUser(currentUser);
        
        if (success) {
            // 更新成功，更新session中的用户信息
            session.setAttribute("user", currentUser);
            request.setAttribute("successMessage", "个人信息更新成功！");
        } else {
            // 更新失败
            request.setAttribute("errorMessage", "个人信息更新失败，请重试！");
        }
        
        // 重新显示个人信息页面
        request.setAttribute("user", currentUser);
        request.getRequestDispatcher("/jsp/user/profile.jsp").forward(request, response);
    }
}