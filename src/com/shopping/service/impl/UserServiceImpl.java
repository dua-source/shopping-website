package com.shopping.service.impl;

import com.shopping.dao.UserDAO;
import com.shopping.dao.impl.UserDAOImpl;
import com.shopping.model.User;
import com.shopping.service.UserService;

public class UserServiceImpl implements UserService {
    
    private UserDAO userDAO = new UserDAOImpl();
    
    @Override
    public boolean register(User user) {
        // 检查用户名是否已存在
        if (getUserByUsername(user.getUsername()) != null) {
            return false;
        }
        
        // 检查邮箱是否已存在
        if (getUserByEmail(user.getEmail()) != null) {
            return false;
        }
        
        // 设置默认角色为customer
        user.setRole("customer");
        
        // 添加用户
        return userDAO.addUser(user);
    }
    
    @Override
    public User login(String username, String password) {
        return userDAO.getUserByUsernameAndPassword(username, password);
    }
    
    @Override
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }
    
    @Override
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }
    
    @Override
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }
    
    @Override
    public boolean updateUser(User user) {
        // 检查是否存在该用户
        if (getUserById(user.getId()) == null) {
            return false;
        }
        
        // 更新用户信息
        return userDAO.updateUser(user);
    }
}

