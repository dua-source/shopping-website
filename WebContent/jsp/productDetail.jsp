<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>产品详情 - 在线购物网站</title>
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
                        <% if ("admin".equals(((com.shopping.model.User) session.getAttribute("user")).getRole())) { %>
                            <li><a href="<%= request.getContextPath() %>/admin/productList">管理后台</a></li>
                        <% } %>
                        <li><a href="<%= request.getContextPath() %>/logout">注销</a></li>
                    <% } %>
                </ul>
            </nav>
        </div>
    </header>
    
    <% 
        com.shopping.model.Product product = (com.shopping.model.Product) request.getAttribute("product");
        if (product == null) {
            response.sendRedirect(request.getContextPath() + "/productList");
            return;
        }
    %>
    
    <div class="container">
        <main>
            <h2><%= product.getName() %></h2>
            
            <div style="display: flex; margin: 20px 0;">
                <div style="flex: 1; margin-right: 30px;">
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
                    <img src="<%= finalImagePath %>" alt="<%= product.getName() %>" style="width: 100%; max-width: 400px; height: auto; border-radius: 5px;" onerror="this.src='<%= request.getContextPath() %>/images/default.jpg'">
                </div>
                <div style="flex: 1;">
                    <p><strong>价格:</strong> ¥<%= product.getPrice() %></p>
                    <p><strong>库存:</strong> <%= product.getStock() %></p>
                    <p><strong>分类:</strong> <%= product.getCategory() %></p>
                    <p><strong>描述:</strong></p>
                    <p><%= product.getDescription() %></p>
                    
                    <form action="<%= request.getContextPath() %>/addToCart" method="post" style="margin-top: 20px;">
                        <div class="form-group" style="display: flex; align-items: center;">
                            <label for="quantity" style="margin-right: 10px;">数量:</label>
                            <input type="number" id="quantity" name="quantity" value="1" min="1" max="<%= product.getStock() %>" style="width: 60px; margin-right: 10px;">
                            <input type="hidden" name="productId" value="<%= product.getId() %>">
                            <input type="submit" value="加入购物车" class="btn btn-success">
                        </div>
                    </form>
                    
                    <a href="<%= request.getContextPath() %>/productList" class="btn" style="margin-top: 15px; display: inline-block;">返回产品列表</a>
                </div>
            </div>
        </main>
    </div>
    
    <footer>
        <div class="container">
            <p>&copy; 2025 在线购物网站 版权所有</p>
        </div>
    </footer>
</body>
</html>