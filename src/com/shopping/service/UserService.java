package com.shopping.service;

import com.shopping.model.User;

public interface UserService {
    // 用户注册
    boolean register(User user);
    
    // 用户登录
    User login(String username, String password);
    
    // 根据用户名查询用户
    User getUserByUsername(String username);
    
    // 根据邮箱查询用户
    User getUserByEmail(String email);
    
    // 根据ID查询用户
    User getUserById(int id);
    
    // 更新用户信息
    boolean updateUser(User user);
}

