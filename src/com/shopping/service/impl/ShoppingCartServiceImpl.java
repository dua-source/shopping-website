package com.shopping.service.impl;

import com.shopping.dao.ShoppingCartDAO;
import com.shopping.dao.impl.ShoppingCartDAOImpl;
import com.shopping.model.ShoppingCart;
import com.shopping.service.ShoppingCartService;

import java.util.List;

public class ShoppingCartServiceImpl implements ShoppingCartService {
    
    private ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAOImpl();
    
    @Override
    public boolean addToCart(ShoppingCart cartItem) {
        return shoppingCartDAO.addToCart(cartItem);
    }
    
    @Override
    public boolean updateCartItemQuantity(int cartId, int quantity) {
        return shoppingCartDAO.updateCartItemQuantity(cartId, quantity);
    }
    
    @Override
    public boolean removeFromCart(int cartId) {
        return shoppingCartDAO.removeFromCart(cartId);
    }
    
    @Override
    public List<ShoppingCart> getCartByUserId(int userId) {
        return shoppingCartDAO.getCartByUserId(userId);
    }
    
    @Override
    public boolean clearCart(int userId) {
        return shoppingCartDAO.clearCart(userId);
    }
}

