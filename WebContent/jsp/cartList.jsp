<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.shopping.model.ShoppingCart" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>购物车</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <h1>在线购物网站</h1>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/productList">首页</a></li>
                    <% if (session.getAttribute("user") == null) { %>
                        <li><a href="${pageContext.request.contextPath}/login">登录</a></li>
                        <li><a href="${pageContext.request.contextPath}/register">注册</a></li>
                    <% } else { %>
                        <li><a href="${pageContext.request.contextPath}/cartList">购物车</a></li>
                    <li><a href="${pageContext.request.contextPath}/orderList">我的订单</a></li>
                    <li><a href="${pageContext.request.contextPath}/updateProfile">个人信息</a></li>
                    <% if ("admin".equals(((com.shopping.model.User) session.getAttribute("user")).getRole())) { %>
                        <li><a href="${pageContext.request.contextPath}/admin/productList">管理后台</a></li>
                    <% } %>
                    <li><a href="${pageContext.request.contextPath}/logout">注销</a></li>
                    <% } %>
                </ul>
            </nav>
        </div>
    </header>
    
    <div class="container">
        <main>
            <h2>我的购物车</h2>
            
            <% List<ShoppingCart> cartItems = (List<ShoppingCart>) request.getAttribute("cartItems");
               Double totalPrice = (Double) request.getAttribute("totalPrice");
               if (cartItems == null || cartItems.isEmpty()) { %>
                <p>购物车为空，快去添加商品吧！</p>
                <a href="${pageContext.request.contextPath}/productList" class="btn btn-primary">去购物</a>
            <% } else { %>
                <div class="cart-items">
                    <% for (ShoppingCart item : cartItems) { %>
                        <div class="cart-item">
                            <% 
                                String imagePath = request.getContextPath() + "/images/";
                                String productImage = item.getImage();
                                String finalImagePath;
                                if (productImage != null && !productImage.isEmpty()) {
                                    finalImagePath = imagePath + productImage;
                                } else {
                                    finalImagePath = imagePath + "default.jpg";
                                }
                            %>
                            <img src="<%= finalImagePath %>" alt="<%= item.getProductName() %>" onerror="this.src='<%= request.getContextPath() %>/images/default.jpg'">
                            <div class="cart-item-info">
                                <h3><%= item.getProductName() %></h3>
                                <p>单价: ¥<%= item.getPrice() %></p>
                            </div>
                            <div class="cart-item-quantity">
                                <form action="${pageContext.request.contextPath}/updateCart" method="post" style="display: inline;">
                                    <input type="hidden" name="cartId" value="<%= item.getId() %>">
                                    <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1">
                                    <input type="submit" value="更新" class="btn">
                                </form>
                            </div>
                            <div class="cart-item-price">
                                ¥<%= item.getSubtotal() %>
                            </div>
                            <div class="cart-item-remove">
                                <a href="${pageContext.request.contextPath}/removeFromCart?cartId=<%= item.getId() %>" class="btn btn-danger">删除</a>
                            </div>
                        </div>
                    <% } %>
                </div>
                
                <div class="cart-summary">
                    <h3>总价: ¥<%= totalPrice %></h3>
                    <form action="${pageContext.request.contextPath}/createOrder" method="post">
                        <div class="form-group" style="margin: 10px 0;">
                            <label for="shippingAddress">收货地址:</label>
                            <textarea id="shippingAddress" name="shippingAddress" required></textarea>
                        </div>
                        <div class="form-group" style="margin: 10px 0;">
                            <label for="paymentMethod">支付方式:</label>
                            <select id="paymentMethod" name="paymentMethod" required>
                                <option value="微信支付">微信支付</option>
                                <option value="支付宝">支付宝</option>
                                <option value="银行卡支付">银行卡支付</option>
                            </select>
                        </div>
                        <input type="submit" value="结算" class="btn btn-success">
                    </form>
                </div>
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