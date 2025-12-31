package com.shopping.controller;

import com.shopping.model.User;
import com.shopping.model.Product;
import com.shopping.service.ProductService;
import com.shopping.service.impl.ProductServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // 5MB max file size
public class AddProductServlet extends HttpServlet {
    
    private ProductService productService = new ProductServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取session中的用户信息
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || !"admin".equals(user.getRole())) {
            // 如果用户未登录或不是管理员，重定向到登录页面
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        // 转发到添加商品页面
        req.getRequestDispatcher("/jsp/admin/addProduct.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取session中的用户信息
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || !"admin".equals(user.getRole())) {
            // 如果用户未登录或不是管理员，重定向到登录页面
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        // 获取请求参数
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String priceStr = req.getParameter("price");
        String stockStr = req.getParameter("stock");
        String category = req.getParameter("category");
        
        if (name == null || name.isEmpty() || description == null || description.isEmpty() || 
            priceStr == null || priceStr.isEmpty() || stockStr == null || stockStr.isEmpty() ||
            category == null || category.isEmpty()) {
            // 如果参数不完整，返回添加商品页面并显示错误信息
            req.setAttribute("error", "请填写完整的商品信息");
            req.getRequestDispatcher("/jsp/admin/addProduct.jsp").forward(req, resp);
            return;
        }
        
        try {
            double price = Double.parseDouble(priceStr);
            int stock = Integer.parseInt(stockStr);
            
            // 处理文件上传
            String image = null;
            Part filePart = req.getPart("imageFile");
            if (filePart != null && filePart.getSize() > 0) {
                // 获取上传目录路径 - 仅使用部署目录
                String deployPath = getServletContext().getRealPath("/images");
                
                // 确保部署目录存在
                File deployDir = new File(deployPath);
                if (!deployDir.exists()) {
                    deployDir.mkdirs();
                }
                
                // 生成文件名：使用商品名称，处理特殊字符，确保唯一
                String extension = getFileExtension(filePart.getSubmittedFileName());
                String baseName = sanitizeFileName(name);
                String fileName = getUniqueFileName(baseName, extension, deployPath);
                
                // 保存到部署目录
                String deployFilePath = deployPath + File.separator + fileName;
                filePart.write(deployFilePath);
                
                // 设置图片路径
                image = fileName;
            }
            
            // 创建商品对象
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            product.setCategory(category);
            product.setImage(image);
            
            // 添加商品
            boolean success = productService.addProduct(product);
            
            if (success) {
                // 添加成功，重定向到管理员商品列表页面
                resp.sendRedirect(req.getContextPath() + "/admin/productList");
            } else {
                // 添加失败，返回添加商品页面并显示错误信息
                req.setAttribute("error", "添加商品失败");
                req.getRequestDispatcher("/jsp/admin/addProduct.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            // 价格或库存格式错误，返回添加商品页面并显示错误信息
            req.setAttribute("error", "价格或库存格式错误");
            req.getRequestDispatcher("/jsp/admin/addProduct.jsp").forward(req, resp);
        } catch (Exception e) {
            // 文件上传或其他错误，返回添加商品页面并显示错误信息
            req.setAttribute("error", "图片上传失败: " + e.getMessage());
            req.getRequestDispatcher("/jsp/admin/addProduct.jsp").forward(req, resp);
        }
    }
    
    /**
     * 处理文件名中的特殊字符，确保文件名安全
     */
    private String sanitizeFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "default";
        }
        // 替换特殊字符为下划线
        return fileName.replaceAll("[^a-zA-Z0-9_.-]", "_");
    }
    
    /**
     * 确保文件名唯一，如果已存在则添加数字后缀
     */
    private String getUniqueFileName(String baseName, String extension, String directoryPath) {
        String fileName = baseName + extension;
        File file = new File(directoryPath + File.separator + fileName);
        int counter = 1;
        
        while (file.exists()) {
            fileName = baseName + "_" + counter + extension;
            file = new File(directoryPath + File.separator + fileName);
            counter++;
        }
        
        return fileName;
    }
    
    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return ".jpg"; // 默认扩展名
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}