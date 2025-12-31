<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理员 - 修改商品</title>
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
            <h2>修改商品</h2>
            
            <div class="admin-menu">
                <ul>
                    <li><a href="<%= request.getContextPath() %>/admin/productList">商品列表</a></li>
                    <li><a href="<%= request.getContextPath() %>/admin/addProduct">添加商品</a></li>
                </ul>
            </div>
            
            <% if (request.getAttribute("error") != null) { %>
                <div class="message message-error">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            
            <% 
                com.shopping.model.Product product = (com.shopping.model.Product) request.getAttribute("product");
                if (product == null) {
                    response.sendRedirect(request.getContextPath() + "/admin/productList");
                    return;
                }
            %>
            <form action="<%= request.getContextPath() %>/admin/editProduct" method="post" enctype="multipart/form-data">
                <input type="hidden" name="id" value="<%= product.getId() %>">
                <div class="form-group">
                    <label for="name">商品名称</label>
                    <input type="text" id="name" name="name" value="<%= product.getName() %>" required>
                </div>
                <div class="form-group">
                    <label for="description">商品描述</label>
                    <textarea id="description" name="description" required><%= product.getDescription() %></textarea>
                </div>
                <div class="form-group">
                    <label for="price">价格</label>
                    <input type="number" id="price" name="price" step="0.01" min="0" value="<%= product.getPrice() %>" required>
                </div>
                <div class="form-group">
                    <label for="stock">库存</label>
                    <input type="number" id="stock" name="stock" min="0" value="<%= product.getStock() %>" required>
                </div>
                <div class="form-group">
                    <label for="category">分类</label>
                    <input type="text" id="category" name="category" value="<%= product.getCategory() %>" required placeholder="例如：手机、电脑、耳机、平板等">
                </div>
                <div class="form-group">
                    <label for="imageFile">商品图片</label>
                    <input type="file" id="imageFile" name="imageFile" accept="image/*">
                    <input type="hidden" name="oldImage" value="<%= product.getImage() != null ? product.getImage() : "" %>">
                    <br>
                    <% if (product.getImage() != null && !product.getImage().isEmpty()) { %>
                        <div style="margin-top: 10px;">
                            <img src="<%= request.getContextPath() %>/images/<%= product.getImage() %>" alt="<%= product.getName() %>" style="max-width: 200px; max-height: 200px;">
                            <p>当前图片: <%= product.getImage() %></p>
                        </div>
                    <% } else { %>
                        <p>当前没有图片</p>
                    <% } %>
                </div>
                <div class="form-group">
                    <input type="submit" value="保存修改" class="btn btn-primary">
                    <a href="<%= request.getContextPath() %>/admin/productList" class="btn">返回</a>
                </div>
            </form>
        </main>
    </div>
    
    <footer>
        <div class="container">
            <p>&copy; 2025 在线购物网站 版权所有</p>
        </div>
    </footer>
</body>
</html>