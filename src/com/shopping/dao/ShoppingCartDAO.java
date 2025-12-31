package com.shopping.dao;

import com.shopping.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartDAO {
    boolean addToCart(ShoppingCart cartItem);
    boolean updateCartItemQuantity(int cartId, int quantity);
    boolean removeFromCart(int cartId);
    ShoppingCart getCartItemByUserIdAndProductId(int userId, int productId);
    List<ShoppingCart> getCartByUserId(int userId);
    boolean clearCart(int userId);
}