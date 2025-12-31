package com.shopping.dao;

import com.shopping.model.User;

public interface UserDAO {
    boolean addUser(User user);
    User getUserByUsernameAndPassword(String username, String password);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User getUserById(int id);
    boolean updateUser(User user);
}