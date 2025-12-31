<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.shopping.model.Product" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>产品列表</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <h1>在线购物网站</h1>
            <nav>
                <ul>
                    <li><a href="<%= request.getContextPath() %>/productList">首页</a></li>
                    <% if (session.getAttribute("user") == null) { %>
                        <li><a href="<%= request.getContextPath() %>/login">登录</a></li>
                        <li><a href="<%= request.getContextPath() %>/register">注册</a></li>
                    <% } else { %>
                        <li><a href="<%= request.getContextPath() %>/cartList">购物车</a></li>
                    <li><a href="<%= request.getContextPath() %>/orderList">我的订单</a></li>
                    <li><a href="<%= request.getContextPath() %>/updateProfile">个人信息</a></li>
                    <% if ("admin".equals(((com.shopping.model.User) session.getAttribute("user")).getRole())) { %>
                        <li><a href="<%= request.getContextPath() %>/admin/productList">管理后台</a></li>
                    <% } %>
                    <li><a href="<%= request.getContextPath() %>/logout">注销</a></li>
                    <% } %>
                </ul>
            </nav>
        </div>
    </header>
    
    <div class="container">
        <main>
            <h2>产品列表</h2>
            
            <!-- 搜索和分类筛选 -->
            <div style="margin-bottom: 20px;">
                <form action="<%= request.getContextPath() %>/productList" method="get" style="display: inline;">
                    <input type="text" name="keyword" placeholder="搜索商品" value="<%= request.getAttribute("keyword") != null ? request.getAttribute("keyword") : "" %>">
                    <input type="submit" value="搜索" class="btn">
                </form>
                
                <form action="<%= request.getContextPath() %>/productList" method="get" style="display: inline; margin-left: 20px;">
                    <select name="category">
                    <option value="">全部分类</option>
                    <% List<String> categories = (List<String>) request.getAttribute("categories");
                       if (categories != null && !categories.isEmpty()) {
                           for (String cat : categories) { %>
                               <option value="<%= cat %>" <%= cat.equals(request.getAttribute("category")) ? "selected" : "" %>>
                                   <%= cat %>
                               </option>
                           <% } 
                       } %>
                </select>
                    <input type="submit" value="筛选" class="btn">
                </form>
            </div>
            
            <!-- 产品列表 -->
            <div class="product-grid">
                <% List<Product> products = (List<Product>) request.getAttribute("products");
                   if (products != null && !products.isEmpty()) {
                       for (Product product : products) { %>
                    <div class="product-card">
                        <% 
                            String imagePath = request.getContextPath() + "/images/";
                            String productImage = product.getImage();
                            String finalImagePath;
                            if (productImage != null && !productImage.isEmpty()) {
                                finalImagePath = imagePath + productImage;
                            } else {
                                finalImagePath = imagePath + "default.jpg";
                            }
                        %>
                        <img src="<%= finalImagePath %>" alt="<%= product.getName() %>" onerror="this.src='<%= request.getContextPath() %>/images/default.jpg'" style="max-width: 100%; height: auto; border-radius: 3px; margin-bottom: 10px;">
                        <h3><%= product.getName() %></h3>
                        <p><%= product.getDescription() %></p>
                        <p class="price">¥<%= product.getPrice() %></p>
                        <p>库存: <%= product.getStock() %></p>
                        <a href="<%= request.getContextPath() %>/productDetail?id=<%= product.getId() %>" class="btn">查看详情</a>
                        <form action="<%= request.getContextPath() %>/addToCart" method="post" style="display: inline; margin-left: 10px;">
                            <input type="hidden" name="productId" value="<%= product.getId() %>">
                            <input type="hidden" name="quantity" value="1">
                            <input type="submit" value="加入购物车" class="btn btn-success">
                        </form>
                    </div>
                <% } 
                   } %>
            </div>
            
            <!-- 如果没有产品 -->
            <% List<Product> productsEmptyCheck = (List<Product>) request.getAttribute("products");
               if (productsEmptyCheck == null || productsEmptyCheck.isEmpty()) { %>
                <p>没有找到相关产品</p>
            <% } %>
        </main>
    </div>
    
    <footer>
        <div class="container">
            <p>&copy; 2025 在线购物网站 版权所有</p>
        </div>
    </footer>
</body>
</html>