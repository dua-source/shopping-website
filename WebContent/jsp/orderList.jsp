<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.shopping.model.Order" %>
<%@ page import="com.shopping.model.OrderItem" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的订单</title>
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
            <h2>我的订单</h2>
            
            <% List<Order> orders = (List<Order>) request.getAttribute("orders");
               if (orders == null || orders.isEmpty()) { %>
                <p>还没有订单，快去购物吧！</p>
                <a href="${pageContext.request.contextPath}/productList" class="btn btn-primary">去购物</a>
            <% } else { %>
                <div class="order-list">
                    <% for (Order order : orders) { %>
                        <div class="order-item">
                            <div class="order-header">
                                <h3>订单号: <%= order.getId() %></h3>
                                <p>状态: <%= order.getStatus() %></p>
                            </div>
                            <p>下单时间: <%= order.getOrderDate() %></p>
                            <p>支付方式: <%= order.getPaymentMethod() %></p>
                            <p>收货地址: <%= order.getShippingAddress() %></p>
                            
                            <div class="order-items">
                                <h4>订单项:</h4>
                                <% for (OrderItem item : order.getOrderItems()) { %>
                                    <div class="order-item-details">
                                        <span><%= item.getProductName() %> x <%= item.getQuantity() %></span>
                                        <span>¥<%= item.getUnitPrice() %> × <%= item.getQuantity() %> = ¥<%= item.getSubtotal() %></span>
                                    </div>
                                <% } %>
                            </div>
                            
                            <div class="order-summary">
                                <p>总价: ¥<%= order.getTotalAmount() %></p>
                            </div>
                            
                            <a href="${pageContext.request.contextPath}/orderDetail?orderId=<%= order.getId() %>" class="btn">查看详情</a>
                        </div>
                    <% } %>
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