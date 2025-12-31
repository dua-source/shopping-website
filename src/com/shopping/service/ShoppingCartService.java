package com.shopping.service;

import com.shopping.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    // 添加商品到购物车
    boolean addToCart(ShoppingCart cartItem);
    
    // 更新购物车商品数量
    boolean updateCartItemQuantity(int cartId, int quantity);
    
    // 删除购物车商品
    boolean removeFromCart(int cartId);
    
    // 根据用户ID查询购物车
    List<ShoppingCart> getCartByUserId(int userId);
    
    // 清空购物车
    boolean clearCart(int userId);
}

