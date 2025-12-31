package com.shopping.service.impl;

import com.shopping.dao.ProductDAO;
import com.shopping.dao.impl.ProductDAOImpl;
import com.shopping.model.Product;
import com.shopping.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    
    private ProductDAO productDAO = new ProductDAOImpl();
    
    @Override
    public boolean addProduct(Product product) {
        return productDAO.addProduct(product);
    }
    
    @Override
    public boolean updateProduct(Product product) {
        return productDAO.updateProduct(product);
    }
    
    @Override
    public boolean deleteProduct(int id) {
        return productDAO.deleteProduct(id);
    }
    
    @Override
    public Product getProductById(int id) {
        return productDAO.getProductById(id);
    }
    
    @Override
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }
    
    @Override
    public List<Product> getProductsByCategory(String category) {
        return productDAO.getProductsByCategory(category);
    }
    
    @Override
    public List<Product> searchProducts(String keyword) {
        return productDAO.searchProducts(keyword);
    }
    
    @Override
    public boolean updateProductStock(int productId, int quantity) {
        return productDAO.updateProductStock(productId, quantity);
    }
    
    @Override
    public List<String> getAllCategories() {
        return productDAO.getAllCategories();
    }
}

