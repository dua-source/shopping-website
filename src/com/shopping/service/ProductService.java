package com.shopping.service;

import com.shopping.model.Product;

import java.util.List;

public interface ProductService {
    // 添加产品
    boolean addProduct(Product product);
    
    // 修改产品
    boolean updateProduct(Product product);
    
    // 删除产品
    boolean deleteProduct(int id);
    
    // 根据ID查询产品
    Product getProductById(int id);
    
    // 获取所有产品
    List<Product> getAllProducts();
    
    // 根据分类查询产品
    List<Product> getProductsByCategory(String category);
    
    // 搜索产品
    List<Product> searchProducts(String keyword);
    
    // 更新产品库存
    boolean updateProductStock(int productId, int quantity);
    
    // 获取所有分类
    List<String> getAllCategories();
}

