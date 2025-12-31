<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.shopping.model.Product" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理员 - 商品管理</title>
    <link rel="stylesheet" type="text/css" href="../../css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <h1>在线购物网站 - 管理后台</h1>
            <nav>
                <ul>
                    <li><a href="<%= request.getContextPath() %>/admin/productList">商品管理</a></li>
                    <li><a href="<%= request.getContextPath() %>/admin/orderList">订单管理</a></li>
                    <li><a href="<%= request.getContextPath() %>/admin/salesReport">销售统计</a></li>
                    <li><a href="<%= request.getContextPath() %>/logout">注销</a></li>
                </ul>
            </nav>
        </div>
    </header>
    
    <div class="container">
        <main>
            <h2>商品管理</h2>
            
            <div class="admin-menu">
                <ul>
                    <li><a href="<%= request.getContextPath() %>/admin/productList">商品列表</a></li>
                    <li><a href="<%= request.getContextPath() %>/admin/addProduct">添加商品</a></li>
                </ul>
            </div>
            
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>名称</th>
                        <th>描述</th>
                        <th>价格</th>
                        <th>库存</th>
                        <th>分类</th>
                        <th>图片</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <% List<Product> products = (List<Product>) request.getAttribute("products");
                       if (products != null && !products.isEmpty()) {
                           for (Product product : products) { %>
                        <tr>
                            <td><%= product.getId() %></td>
                            <td><%= product.getName() %></td>
                            <td><%= product.getDescription() %></td>
                            <td>¥<%= product.getPrice() %></td>
                            <td><%= product.getStock() %></td>
                            <td><%= product.getCategory() %></td>
                            <td>
                                <% if (product.getImage() != null && !product.getImage().isEmpty()) { %>
                                    <img src="<%= request.getContextPath() %>/images/<%= product.getImage() %>" alt="<%= product.getName() %>" style="max-width: 100px; max-height: 100px;">
                                <% } else { %>
                                    <span>无图片</span>
                                <% } %>
                            </td>
                            <td>
                                <a href="<%= request.getContextPath() %>/admin/editProduct?id=<%= product.getId() %>" class="btn">编辑</a>
                                <a href="<%= request.getContextPath() %>/admin/deleteProduct?id=<%= product.getId() %>" class="btn btn-danger" onclick="return confirm('确定要删除这个商品吗？');">删除</a>
                            </td>
                        </tr>
                    <% } 
                       } %>
                </tbody>
            </table>
        </main>
    </div>
    
    <footer>
        <div class="container">
            <p>&copy; 2025 在线购物网站 版权所有</p>
        </div>
    </footer>
</body>
</html>