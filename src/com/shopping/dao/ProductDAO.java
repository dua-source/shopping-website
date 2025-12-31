package com.shopping.dao;

import com.shopping.model.Product;

import java.util.List;

public interface ProductDAO {
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(int id);
    Product getProductById(int id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> searchProducts(String keyword);
    boolean updateProductStock(int productId, int quantity);
    List<String> getAllCategories();
}